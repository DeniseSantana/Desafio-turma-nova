package br.com.santander.core.uftmobile.api.rest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.santander.core.uftmobile.api.exception.DeviceUnvailableException;
import br.com.santander.core.uftmobile.api.exception.InvalidCredentialsException;
import br.com.santander.core.uftmobile.api.exception.InvalidExpressionException;
import br.com.santander.core.uftmobile.api.exception.RequestCouldNotBeExecutedException;
import br.com.santander.core.uftmobile.api.model.Datum;
import br.com.santander.core.uftmobile.api.model.DeviceCapabilities;
import br.com.santander.core.uftmobile.api.model.DeviceContent;
import br.com.santander.core.uftmobile.api.model.InstallationDetails;
import br.com.santander.core.uftmobile.api.model.InstallationDetailsResponse;
import br.com.santander.core.uftmobile.api.model.UFTMobileApps;
import br.com.santander.core.uftmobile.api.model.Reservation;
import br.com.santander.core.uftmobile.api.model.ReservationDetails;
import br.com.santander.core.uftmobile.api.model.User;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UFTMobileApiClient {
	
	private static final Logger LOG = LogManager.getLogger(UFTMobileApiClient.class);

	// *************************************
	// Initiate the constants with your data
	// *************************************

	private String BASE_URL;

	// ************************************
	// Mobile Center APIs end-points
	// ************************************

	private static final String ENDPOINT_CLIENT_LOGIN = "client/login";
	private static final String ENDPOINT_CLIENT_LOGOUT = "client/logout";
	private static final String ENDPOINT_CLIENT_DEVICES = "deviceContent";
	private static final String ENDPOINT_CLIENT_APPS = "apps";
	private static final String ENDPOINT_CLIENT_INSTALL_APPS = "apps/install";
	private static final String ENDPOINT_CLIENT_UPLOAD_APPS = "apps/upload?enforceUpload=true";
	private static final String ENDPOINT_CLIENT_USERS = "v2/users";
	private static final String ENDPOINT_CLIENT_RESERVATION = "v2/public/reservation";

	private static final Integer DEFAULT_RESERVATION_IN_MINUTES = 10;
	//  ************************************
	//  Initiate proxy configuration
	//  ************************************
	//	modified to work with dinamic address proxy's
//		private static final boolean USE_PROXY = false;
//		private static final String PROXY = "<PROXY>";

	// ************************************
	// Path to app (IPA or APK) for upload
	// ************************************

	@SuppressWarnings("unused")
	private static String APP = "/PATH/TO/APP/FILE.ipa|apk";

	private OkHttpClient client;
	private String hp4msecret;
	private String jsessionid;
	private String responseBodyStr;
	private String userName;

	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final MediaType APK = MediaType.parse("application/vnd.android.package-archive");

	/**
	 * APIClient class constructor to store all info and call API methods
	 * 
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @param proxyEnable
	 * @param proxyUrl
	 * @param proxyPort
	 * @throws IOException
	 */
	public UFTMobileApiClient(String host, String port, String userName, String password, Boolean proxyEnable, String proxyUrl, int proxyPort) {
		this.BASE_URL = "http://" + host + ":" + port + "/rest/";
		this.userName = userName;
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().readTimeout(60, TimeUnit.MINUTES)
				.writeTimeout(60, TimeUnit.MINUTES).connectTimeout(60, TimeUnit.SECONDS).cookieJar(new CookieJar() {
					private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

					@Override
					public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
						List<Cookie> storedCookies = cookieStore.get(url.host());
						if (storedCookies == null) {
							storedCookies = new ArrayList<>();
							cookieStore.put(url.host(), storedCookies);
						}
						storedCookies.addAll(cookies);
						for (Cookie cookie : cookies) {
							if (cookie.name().equals("hp4msecret"))
								hp4msecret = cookie.value();
							if (cookie.name().equals("JSESSIONID"))
								jsessionid = cookie.value();
						}
					}

					@Override
					public List<Cookie> loadForRequest(HttpUrl url) {
						List<Cookie> cookies = cookieStore.get(url.host());
						return cookies != null ? cookies : new ArrayList<>();
					}
				});

		if (proxyEnable) {
			clientBuilder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort)));
		}

		client = clientBuilder.build();
		login(userName, password);
	}

	/**
	 * Login to Mobile Center for getting cookies to work with API
	 * 
	 * @param username
	 * @param password
	 */
	private void login(String username, String password) {
		String strCredentials = "{\"name\":\"" + username + "\",\"password\":\"" + password + "\"}";
		RequestBody body = RequestBody.create(JSON, strCredentials);
		// build the request URL and headers
		Request.Builder builder = new Request.Builder().url(BASE_URL + ENDPOINT_CLIENT_LOGIN)
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		// add CRSF header
		if (hp4msecret != null) {
			builder.addHeader("x-hp4msecret", hp4msecret);
		}
		builder.post(body);
		Request request = builder.build();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				return;
			} else if (response.code() == 401) {
				throw new InvalidCredentialsException("Invalid credentials. Verify username and password");
			}
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
	}

	/**
	 * List all apps from Mobile Center
	 * 
	 * @return
	 * @throws IOException
	 */
	public UFTMobileApps getAllApps() {
		// build the request URL and headers
		Request.Builder builder = new Request.Builder().url(BASE_URL + ENDPOINT_CLIENT_APPS)
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		// add CRSF header
		if (hp4msecret != null) {
			builder.addHeader("x-hp4msecret", hp4msecret);
		}
		builder.get();
		Request request = builder.build();
		Gson gson = new Gson();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				final ResponseBody responseBody = response.body();
				if (responseBody != null) {
					responseBodyStr = responseBody.string();
					UFTMobileApps deviceContentCollection = gson.fromJson(responseBodyStr, UFTMobileApps.class);
					return deviceContentCollection;
				}
			} else if (response.code() == 401) {
				throw new InvalidCredentialsException("Invalid credentials. Verify username and password");
			} else {
				throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
		return null;
	}

	/**
	 * List all users from Mobile Center
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<User> getAllUsers() {
		// build the request URL and headers
		Request.Builder builder = new Request.Builder().url(BASE_URL + ENDPOINT_CLIENT_USERS)
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		builder.get();
		Request request = builder.build();
		Gson gson = new Gson();
		List<User> usersList = new ArrayList<>();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				final ResponseBody responseBody = response.body();
				if (responseBody != null) {
					responseBodyStr = responseBody.string();
					usersList = Arrays.asList(gson.fromJson(responseBodyStr, User[].class));
				}
			} else if (response.code() == 401) {
				throw new InvalidCredentialsException("Invalid credentials. Verify username and password");
			}
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
		return usersList;
	}

	/**
	 * List all devices from Mobile Center
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<DeviceContent> getAllDevicesInformation() {
		// build the request URL and headers
		Request.Builder builder = new Request.Builder().url(BASE_URL + ENDPOINT_CLIENT_DEVICES)
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		builder.get();
		Request request = builder.build();
		Gson gson = new Gson();
		List<DeviceContent> devices = new ArrayList<>();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				final ResponseBody responseBody = response.body();
				if (responseBody != null) {
					responseBodyStr = responseBody.string();
					Type listType = new TypeToken<ArrayList<DeviceContent>>() {}.getType();
					List<DeviceContent> deviceList = gson.fromJson(responseBodyStr, listType);
					deviceList.removeIf( deviceItem -> deviceItem.getCurrentReservation() == null);
					return deviceList;
				}
			} else if (response.code() == 401) {
				throw new InvalidCredentialsException("Invalid credentials. Verify username and password");
			}
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
		return devices;
	}

	/**
	 * Verify if there are devices available
	 * 
	 * @param devicePlatform Plataforma do dispositivo
	 * @return <b>false</b> if there aren't devices available to be reserved
	 */
	public boolean areThereDevicesAvailable(String devicePlatform) {
		List<DeviceContent> devices = getAllDevicesInformation();
		for (DeviceContent device : devices)
			if (device.isAvailable() && device.getPlatformName().equalsIgnoreCase(devicePlatform))
				return true;
		return false;
	}

	/**
	 * Verify if there are devices available
	 * 
	 * @param devicePlatform Plataforma do dispositivo
	 * @return <b>false</b> if there aren't devices available to be reserved
	 * @throws DeviceUnvailableException
	 */
	public DeviceContent getFirstDeviceAvailable(String devicePlatform) throws DeviceUnvailableException {
		List<DeviceContent> devices = getAllDevicesInformation();
		for (DeviceContent device : devices)
			if (device.isAvailable() && device.getPlatformName().equalsIgnoreCase(devicePlatform))
				return device;
		String message = String.format("No devices from %s platform were available", devicePlatform);
		throw new DeviceUnvailableException(message);
	}

	/**
	 * Verify if there are devices available
	 * 
	 * @param devicePlatform Plataforma do dispositivo
	 * @param devicePlatform Versão da plataforma do dispositivo
	 * @return <b>false</b> if there aren't devices available to be reserved
	 * @throws DeviceUnvailableException
	 * @throws InvalidExpressionException
	 */
	public DeviceContent getFirstDeviceAvailable(String devicePlatform, String platformVersion)
			throws DeviceUnvailableException, InvalidExpressionException {
		Pattern pattern = Pattern.compile("^([<>=]+)(\\d.*$)");
		Matcher matcher = pattern.matcher(platformVersion);
		List<DeviceContent> devices = getAllDevicesInformation();
		if (!matcher.matches()) {
			for (DeviceContent device : devices)
				if (device.isAvailable() && device.getPlatformName().equalsIgnoreCase(devicePlatform)
						&& device.getPlatformVersion().equals(platformVersion))
					return device;
			String message = String.format("No devices from %s platform with version %s were available", devicePlatform,
					platformVersion);
			throw new DeviceUnvailableException(message);
		} else {
			String comparador = matcher.group(1);
			String targetVersion = matcher.group(2);
			if (comparador.equals(Comparador.BIGGER_THAN.getSymbol())) {
				for (DeviceContent device : devices)
					if (device.isAvailable() && device.getPlatformName().equalsIgnoreCase(devicePlatform)
							&& (compareVersion(device.getPlatformVersion(), targetVersion) > 0))
						return device;
				String message = String.format("No devices from %s platform with version bigger than %s were available",
						devicePlatform, targetVersion);
				throw new DeviceUnvailableException(message);
			} else if (comparador.equals(Comparador.LOWER_THAN.getSymbol())) {
				for (DeviceContent device : devices)
					if (device.isAvailable() && device.getPlatformName().equalsIgnoreCase(devicePlatform)
							&& (compareVersion(device.getPlatformVersion(), targetVersion) < 0))
						return device;
				String message = String.format("No devices from %s platform with version lower than %s were available",
						devicePlatform, targetVersion);
				throw new DeviceUnvailableException(message);
			} else if (comparador.equals(Comparador.BIGGER_OR_EQUAL_THAN.getSymbol())) {
				for (DeviceContent device : devices)
					if (device.isAvailable() && device.getPlatformName().equalsIgnoreCase(devicePlatform)
							&& (compareVersion(device.getPlatformVersion(), targetVersion) >= 0))
						return device;
				String message = String.format(
						"No devices from %s platform with version bigger or equal than %s were available",
						devicePlatform, targetVersion);
				throw new DeviceUnvailableException(message);
			} else if (comparador.equals(Comparador.LOWER_OR_EQUAL_THAN.getSymbol())) {
				for (DeviceContent device : devices)
					if (device.isAvailable() && device.getPlatformName().equalsIgnoreCase(devicePlatform)
							&& (compareVersion(device.getPlatformVersion(), targetVersion) <= 0))
						return device;
				String message = String.format("No devices from %s platform with version lower than %s were available",
						devicePlatform, targetVersion);
				throw new DeviceUnvailableException(message);
			} else if (comparador.equals(Comparador.EQUAL_TO.getSymbol())) {
				for (DeviceContent device : devices)
					if (device.isAvailable() && device.getPlatformName().equalsIgnoreCase(devicePlatform)
							&& (compareVersion(device.getPlatformVersion(), targetVersion) == 0))
						return device;
				String message = String.format("No devices from %s platform with version equal to %s were available",
						devicePlatform, targetVersion);
				throw new DeviceUnvailableException(message);
			} else {
				String message = String.format("Expression '%s' is not valid. Valid expressions: >, < , >=, <= and =",
						comparador);
				throw new InvalidExpressionException(message);
			}
		}
	}

	/**
	 * Return all available devices with its <b>udid</b>
	 * 
	 * @param udids All Devices that must be available
	 * @return List of DeviceContent if all devices are available
	 *
	 * @throws DeviceUnvailableException
	 */
	public List<DeviceContent> getDevicesAvailable(String... udids) throws DeviceUnvailableException {
		List<String> udidList = Arrays.asList(udids);
		List<DeviceContent> devicesAvailable = new ArrayList<>();
		List<DeviceContent> devices = getAllDevicesInformation();
		for (String udid : udidList) {
			boolean found = false;
			for (DeviceContent device : devices) {
				if (device.isAvailable() && udid.equals(device.getUdid())) {
					devicesAvailable.add(device);
					found = true;
				}
			}
			if (!found) {
				String message = String.format("Device %s is unvailable", udid);
				throw new DeviceUnvailableException(message);
			}
		}
		return devicesAvailable;
	}
	
	/**
	 * List all apps from Mobile Center
	 * 
	 * @return
	 * @throws IOException
	 */
	private InstallationDetailsResponse installApp(Datum app, String deviceUdid) {
		// build the request URL and headers
		Request.Builder builder = new Request.Builder().url(BASE_URL + ENDPOINT_CLIENT_APPS + "/install")
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		// add CRSF header
		if (hp4msecret != null) {
			builder.addHeader("x-hp4msecret", hp4msecret);
		}
		String json = new Gson().toJson(new InstallationDetails(app, new DeviceContent(deviceUdid)));
		RequestBody body = RequestBody.create(JSON, json);
		builder.post(body);
		Request request = builder.build();
		Gson gson = new Gson();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				final ResponseBody responseBody = response.body();
				if (responseBody != null) {
					responseBodyStr = responseBody.string();
					InstallationDetailsResponse installationDetailsResponse = gson.fromJson(responseBodyStr,
							InstallationDetailsResponse.class);
					LOG.info("Installed!");
					return installationDetailsResponse;
				}
			} else if (response.code() == 401) {
				throw new InvalidCredentialsException("Invalid credentials. Verify username and password");
			} else if (response.code() == 403) {
				throw new RuntimeException("Forbid to get device");
			} else {
				throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
		return null;
	}

	/**
	 * Install application
	 * 
	 * @param package_name
	 * @param version
	 * @param device_id
	 * @param is_intrumented
	 * @throws IOException
	 */
	private void installApp(String package_name, String version, String device_id, Boolean is_intrumented)
			throws IOException {
		String counter = version;
		String str = "{\n" + "  \"app\": {\n" + "    \"counter\": " + counter + ",\n" + "    \"id\": \"" + package_name
				+ "\",\n" + "    \"instrumented\": " + (is_intrumented ? "true" : "false") + "\n" + "  },\n"
				+ "  \"deviceCapabilities\": {\n" + "    \"udid\": \"" + device_id + "\"\n" + "  }\n" + "}";
		RequestBody body = RequestBody.create(JSON, str);
		executeRestAPI(ENDPOINT_CLIENT_INSTALL_APPS, HttpMethod.POST, body);
	}

	/**
	 * Install application by file name, when there are multiple matches for file
	 * name in database, will select first application.
	 * 
	 * @param filename
	 * @param device_id
	 * @param is_intrumented
	 * @throws IOException
	 */
	public void installAppByFileAndDeviceID(String filename, String device_id, Boolean is_intrumented)
			throws IOException {
		getAllApps();
		String[] res = responseBodyStr.split(filename);
		if (res == null || res.length < 2) {
			return;
		} else {
			String counter = parseProperty(res[0], "\"counter\":", ",");
			String package_name = parseProperty(res[1], "\"identifier\":\"", "\",");
			installApp(package_name, counter, device_id, is_intrumented);
		}
	}

	/**
	 * Install an app by file name
	 * @param fileName
	 * @param deviceUdid
	 */
	public boolean installApplicationByFileName(String fileName, String deviceUdid) {
		Objects.requireNonNull(fileName, "File name is required");
		Objects.requireNonNull(deviceUdid, "Device serial is required");
		DeviceContent device = this.getDevicesAvailable(deviceUdid).get(0);
		
		for (Datum app : this.getAllApps().getData()) {
			if (device.getPlatformName().equalsIgnoreCase(app.getType()) && app.getFileName().equals(fileName)) {
				app.setId(app.getIdentifier());
				return installApp(app, deviceUdid) != null;
			}
		}
		String message = String.format("Application with file name: '%s', was not installed", fileName);
		LOG.info(message);
		return false;
		//throw new ApplicationCouldNotBeInstalledException(message);
	}
	
	/**
	 * Logout from Mobile Center
	 * 
	 * @throws IOException
	 */
	public void logout() throws IOException {
		RequestBody body = RequestBody.create(JSON, "");
		executeRestAPI(ENDPOINT_CLIENT_LOGOUT, HttpMethod.POST, body);
	}

	@SuppressWarnings("unused")
	private void executeRestAPI(String endpoint) throws IOException {
		executeRestAPI(endpoint, HttpMethod.GET);
	}

	private void executeRestAPI(String endpoint, HttpMethod httpMethod) throws IOException {
		executeRestAPI(endpoint, httpMethod, null);
	}

	private void executeRestAPI(String endpoint, HttpMethod httpMethod, RequestBody body) {
		// build the request URL and headers
		Request.Builder builder = new Request.Builder().url(BASE_URL + endpoint)
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		// add CRSF header
		if (hp4msecret != null) {
			builder.addHeader("x-hp4msecret", hp4msecret);
		}
		// build the http method
		if (HttpMethod.GET.equals(httpMethod)) {
			builder.get();
		} else if (HttpMethod.POST.equals(httpMethod)) {
			builder.post(body);
		}
		Request request = builder.build();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				return;
			} else {
				throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
	}
	
	public boolean verifyAlreadyAppUploaded(String name) {
		LOG.info("Checking uploadeds apps... ");
		boolean uploadedApp = false;
		for(Datum a : getAllApps().getData()) {
			if(a.getFileName() != null && a.getFileName().equalsIgnoreCase(name)) {
				uploadedApp = true;
				break;
			}
		}
		return uploadedApp;
	}

	/**
	 * Upload Application to Mobile Center
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public boolean uploadApp(String filename) throws IOException {	

		String[] parts = filename.split("\\\\");
		LOG.info("Uploading and preparing the app... ");
		RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("file", parts[parts.length - 1], RequestBody.create(APK, new File(filename))).build();
		Request request = new Request.Builder().addHeader("content-type", "multipart/form-data")
				.addHeader("x-hp4msecret", hp4msecret).addHeader("JSESSIONID", jsessionid)
				.url(BASE_URL + ENDPOINT_CLIENT_UPLOAD_APPS).post(requestBody).build();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				LOG.info("Done!");
				LOG.info(response.toString());
				ResponseBody body = response.body();
				if (body != null) {
					LOG.info(body.string());
				}
				return true;
			} else {
				throw new IOException("Unexpected code " + response);
			}
		}
		
	}

	/**
	 * Get all reservations from Mobile Center
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<Reservation> getAllReservationInformation() {
		// build the request URL and headers
		Request.Builder builder = new Request.Builder().url(BASE_URL + ENDPOINT_CLIENT_RESERVATION)
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		builder.get();
		Request request = builder.build();
		Gson gson = new Gson();
		List<Reservation> devices = new ArrayList<>();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				final ResponseBody responseBody = response.body();
				if (responseBody != null) {
					responseBodyStr = responseBody.string();
					Reservation[] deviceContentCollection = gson.fromJson(responseBodyStr, Reservation[].class);
					return Arrays.asList(deviceContentCollection);
				}
			} else if (response.code() == 401) {
				throw new InvalidCredentialsException("Invalid credentials. Verify username and password");
			}
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
		return devices;
	}

	/**
	 * Get reservations by user name from Mobile Center
	 * 
	 * @param userName
	 * @return
	 * @throws IOException
	 */
	public List<Reservation> getAllReservationInformation(String userName) {
		// build the request URL and headers
		Request.Builder builder = new Request.Builder().url(BASE_URL + ENDPOINT_CLIENT_RESERVATION)
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		builder.get();
		Request request = builder.build();
		Gson gson = new Gson();
		List<Reservation> reservationsByUser = new ArrayList<>();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				final ResponseBody responseBody = response.body();
				if (responseBody != null) {
					responseBodyStr = responseBody.string();
					Reservation[] reservations = gson.fromJson(responseBodyStr, Reservation[].class);
					for (Reservation reservation : reservations) {
						if (reservation.getReservedForUser().getName().equals(userName))
							reservationsByUser.add(reservation);
					}
					return reservationsByUser;
				}
			} else if (response.code() == 401) {
				throw new InvalidCredentialsException("Invalid credentials. Verify username and password");
			}
			response.close();
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
		return reservationsByUser;
	}

	/**
	 * Create a reservation
	 * 
	 * @param deviceId
	 * @throws IOException
	 */
	public void createReservation( String deviceId )
	{
		createReservation( deviceId, DEFAULT_RESERVATION_IN_MINUTES );
	}
	
	/**
	 * Create a reservation
	 * 
	 * @param deviceId
	 * @param durationInMinutes
	 * @throws IOException
	 */
	public void createReservation(String deviceId, int durationInMinutes) {
		// build the request URL and headers
		ReservationDetails reservationDetails = new ReservationDetails();
		DeviceCapabilities deviceCapabilities = new DeviceCapabilities();
		deviceCapabilities.setUdid(deviceId);
		String startTime = getCurrentTimeUsingCalendar();
		String endTime = getCurrentTimeUsingCalendar(durationInMinutes);
		reservationDetails.setStartTime(startTime);
		reservationDetails.setEndTime(endTime);
		reservationDetails.setReleaseOnJobCompletion(false);
		reservationDetails.setDeviceCapabilities(deviceCapabilities);
		String jsonRequestBodyNew = new Gson().toJson(reservationDetails);
		RequestBody body = RequestBody.create(JSON, jsonRequestBodyNew);
		// build the request URL and headers
		Request.Builder builder = new Request.Builder().url(BASE_URL + ENDPOINT_CLIENT_RESERVATION)
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		// add CRSF header
		if (hp4msecret != null) {
			builder.addHeader("x-hp4msecret", hp4msecret);
		}
		builder.post(body);
		Request request = builder.build();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				return;
			} else if (response.code() == 409) {
				throw new DeviceUnvailableException(String.format("Device '%s' couldn't be reserved", deviceId));
			}
			response.close();
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
	}

	/**
	 * Removes a reservation by its uid
	 * 
	 * @param reservationUID
	 * @throws IOException
	 */
	public void removeReservation(String reservationUID) {
		Request.Builder builder = new Request.Builder()
				.url(BASE_URL + ENDPOINT_CLIENT_RESERVATION + "/" + reservationUID)
				.addHeader("Content-type", JSON.toString()).addHeader("Accept", JSON.toString());
		// add CRSF header
		if (hp4msecret != null) {
			builder.addHeader("x-hp4msecret", hp4msecret);
		}
		builder.delete();
		Request request = builder.build();
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				return;
			} else if (response.code() == 409) {
				throw new DeviceUnvailableException(
						String.format("Reservation '%s' couldn't be removed", reservationUID));
			}
		} catch (IOException e) {
			throw new RequestCouldNotBeExecutedException(e.getMessage().toString());
		}
	}

	private enum HttpMethod {
		GET, POST
	}

	private String parseProperty(String source, String prefix, String suffix) {
		try {
			String[] array = source.split(prefix);
			String str = array[array.length - 1];
			return str.split(suffix)[0];
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getUserName() {
		return this.userName;
	}

	public String getCurrentTimeUsingCalendar() {
		Calendar cal = Calendar.getInstance();
//		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		return dateFormat.format(cal.getTime());
	}

	public String getCurrentTimeUsingCalendar(int plusMinutes) {
		Calendar cal = Calendar.getInstance();
//		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
//		cal.setTime(date);
		cal.add(Calendar.MINUTE, plusMinutes);
		return dateFormat.format(cal.getTime());
	}

	private enum Comparador {
		BIGGER_THAN(">"), LOWER_THAN("<"), BIGGER_OR_EQUAL_THAN(">="), LOWER_OR_EQUAL_THAN("<="), EQUAL_TO("=");

		private String symbol;

		private Comparador(String symbol) {
			this.symbol = symbol;
		}

		public String getSymbol() {
			return this.symbol;
		}

		@Override
		public String toString() {
			return getSymbol();
		}
	}

	private static int compareVersion(String targetVersion, String platformVersion) {
		String[] splitedTargetVersion = targetVersion.split("\\.");
		String[] splitedPlatformVersion = platformVersion.split("\\.");
		int result = 0;
		for (int i = 0; i < splitedTargetVersion.length; i++) {
			int tvFragment = Integer.valueOf(splitedTargetVersion[i]);
			if (i < splitedPlatformVersion.length) {
				int pvFragment = Integer.valueOf(splitedPlatformVersion[i]);
				if (tvFragment < pvFragment)
					return -1;
				else if (tvFragment > pvFragment)
					return 1;
			} else {
				return 1;
			}
		}
		return result;
	}
}
