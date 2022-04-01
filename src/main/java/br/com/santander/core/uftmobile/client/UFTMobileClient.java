package br.com.santander.core.uftmobile.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.santander.core.exception.CoreException;
import br.com.santander.core.uftmobile.api.model.DeviceContent;
import br.com.santander.core.uftmobile.api.model.Reservation;
import br.com.santander.core.uftmobile.api.rest.UFTMobileApiClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UFTMobileClient {
	
	private final UFTMobileClientProperties uftMobileProperties; 
	
	private final String SERVERADDRESS;
	private final String SERVERPORT;
	private final String USERNAME;
	private final String PASSWORD;
	private final Boolean PROXY_ENABLE;
	private final String PROXY_URL;
	private final int PROXY_PORT;
	
	private final int TIME_OF_RESERVE = 20; // minutos
	
	private final UFTMobileApiClient api;
	
	public UFTMobileClient() {
		uftMobileProperties = new UFTMobileClientProperties();
		SERVERADDRESS = uftMobileProperties.getServerAddress();
		SERVERPORT = uftMobileProperties.getServerPort();
		USERNAME = uftMobileProperties.getUserName();
		PASSWORD = uftMobileProperties.getUserPassword();
		PROXY_ENABLE = uftMobileProperties.getServerProxyEnable();
		PROXY_URL = uftMobileProperties.getServerProxyURL();
		PROXY_PORT = uftMobileProperties.getServerProxyPort();
		api = new UFTMobileApiClient( SERVERADDRESS, SERVERPORT, USERNAME, PASSWORD, PROXY_ENABLE, PROXY_URL, PROXY_PORT ); 
	}
	
	public List<DeviceContent> reserveAllDevicesFree(String platform) throws CoreException
	{
		List<DeviceContent> deviceList = new ArrayList<DeviceContent>();
		try
		{
			//***
			
			if ( api.areThereDevicesAvailable( platform ) )
			{
				for ( DeviceContent device : api.getAllDevicesInformation() )
				{
					if ( device.isAvailable() && device.getPlatformName().equalsIgnoreCase(platform) )
					{
						api.createReservation(device.getUdid(), TIME_OF_RESERVE);
						deviceList.add(device);
						log.info( String.format( "The device was reserved '%s', "
								+ "platform '%s', "
								+ "version '%s', "
								+ "udid '%s'", 
								device.getDeviceName(), 
								device.getPlatformName(), 
								device.getPlatformVersion(), 
								device.getUdid() ) );
					}
				}
			} else {
				throw new CoreException( String.format("No free devices found of platform '%s' ...", platform ));
			}
		} catch ( Exception e )
		{
			log.error( String.format( "Error to connect in UFTMobile: %s:%s ...",
					SERVERADDRESS, SERVERPORT), e );
			throw new CoreException(e);
		}
		
		return deviceList;
	}
	
	public List<DeviceContent> listAllDevicesFree() throws CoreException
	{
		//***
		
		return api.getAllDevicesInformation()
				.stream()
				.filter( DeviceContent::isAvailable )
				.collect( Collectors.toList() );
	}
	
	public List<DeviceContent> listAllDevicesFree(String platform) throws CoreException
	{
		//***
		List<DeviceContent> deviceList = new ArrayList<DeviceContent>();
		
		if ( api.areThereDevicesAvailable( platform ) )
		{
			for ( DeviceContent device : api.getAllDevicesInformation() )
			{
				if ( device.isAvailable() && device.getPlatformName().equalsIgnoreCase(platform) )
				{
					deviceList.add(device);
					log.info( String.format( "Device found '%s', "
							+ "platform '%s', "
							+ "version '%s', "
							+ "udid '%s'", 
							device.getDeviceName(), 
							device.getPlatformName(), 
							device.getPlatformVersion(), 
							device.getUdid() ) );
				}
			}
		} else {
			throw new CoreException( String.format("No free devices found of platform '%s' ...", platform ));
		}
		
		return deviceList;
	}
	
	public DeviceContent verifyDeviceIsFree(String platform, String udid) throws CoreException
	{
		//***
		DeviceContent device = null;
		
		if ( api.areThereDevicesAvailable( platform ) )
		{
			for ( DeviceContent d : api.getAllDevicesInformation() )
			{
				if ( d.isAvailable() && d.getPlatformName().equalsIgnoreCase(platform) && d.getUdid().equalsIgnoreCase(udid))
				{
					device = d;
					log.info( String.format( "Device found '%s', "
							+ "platform '%s', "
							+ "version '%s', "
							+ "udid '%s'", 
							d.getDeviceName(), 
							d.getPlatformName(), 
							d.getPlatformVersion(), 
							d.getUdid() ) );
					break;
				}
			}
		} else {
			throw new CoreException( String.format("No free devices found of platform '%s' ...", platform ));
		}
		return device;
	}
	
	public boolean verifyDeviceIsFree( String udid )
	{
		//***
		
		return api.getAllDevicesInformation()
			.stream()
			.filter( DeviceContent::isAvailable )
			.map( DeviceContent::getUdid )
			.collect( Collectors.toList() )
			.contains( udid );
	}
		
	public DeviceContent reserveDevice(String udid) {
		//***
		DeviceContent deviceUnico = null;
		for (DeviceContent device : api.getAllDevicesInformation()) {
			if (device.isAvailable() && device.isConnected() && device.getUdid().equalsIgnoreCase(udid)) {
				api.createReservation(udid, TIME_OF_RESERVE);
				deviceUnico = device;
				log.info(String.format("Reserved device: '%s', platform '%s', version '%s', udid '%s'", device.getDeviceName(), device.getPlatformName(), device.getPlatformVersion(), device.getUdid()));
				break;
			}
		}
		return deviceUnico;
	}
	
	public DeviceContent reserveAnyDevice() {
		//***
		DeviceContent deviceContent = null;
		for (DeviceContent device : api.getAllDevicesInformation()) {
			if (device.isAvailable() && device.isConnected()) {
				api.createReservation(device.getUdid());
				deviceContent = device;
				log.info(String.format("Reserved device: '%s', platform '%s', version '%s', udid '%s'", device.getDeviceName(), device.getPlatformName(), device.getPlatformVersion(), device.getUdid()));
				break;
			}
		}
		return deviceContent;
	}
	
	public DeviceContent listAllDevices() {
		//***
		DeviceContent deviceContent = null;
		for (DeviceContent device : api.getAllDevicesInformation()) {
			deviceContent = device;
			log.info(String.format(
							"Device reserved '%s', platform '%s', version '%s', udid '%s'",
							device.getDeviceName(),
							device.getPlatformName(),
							device.getPlatformVersion(),
							device.getUdid()));
		}
		return deviceContent;
	}

	public void getAllDevicesReserved() {
		//***
		List<Reservation> allReservationInformation = api.getAllReservationInformation();
		if (allReservationInformation.isEmpty())
			log.info("No reservations from user " + api.getUserName());
		for (Reservation reservation : allReservationInformation) {
			log.info("User: " + reservation.getReservedForUser().getName() + "\nReservation ID: "
					+ reservation.getReservationUid() + "\nDevice: "
					+ reservation.getDeviceCapabilities().getDeviceName());
		}
	}

	public void obterTodosOsDispositivosReservadorPorUsuario() {
		//***
		List<Reservation> allReservationInformation = api.getAllReservationInformation(USERNAME);
		if (allReservationInformation.isEmpty())
			log.info("No reservations from user " + api.getUserName());
		log.info("All reservations from user " + api.getUserName());

		for (Reservation reservation : allReservationInformation) {
			log.info("Reservation ID: " + reservation.getReservationUid() + "\tDevice: "
					+ reservation.getDeviceCapabilities().getDeviceName());
		}
	}
	
	public void removeDeviceReservedByUser( String udid )
	{
		//***
		log.info("Removing reservation of '" + udid + "' by user " + api.getUserName());
		List<Reservation> allReservationInformation = api.getAllReservationInformation(USERNAME);
		if ( allReservationInformation.isEmpty() )
		{
			log.info("No reservations from user " + api.getUserName());
		}
		
		for (Reservation reservation : allReservationInformation)
		{
			if ( reservation.getDeviceCapabilities().getUdid().equalsIgnoreCase( udid ) )
			{
				log.info("Device " + reservation.getDeviceCapabilities().getDeviceName() + " free for "
						+ reservation.getReservedForUser().getName());
				api.removeReservation(reservation.getReservationUid());
			}
		}
	}

	public void removeAllDevicesReservedByUser() {
		//***
		log.info("Removing all reservations by user " + api.getUserName());
		List<Reservation> allReservationInformation = api.getAllReservationInformation(USERNAME);
		if (allReservationInformation.isEmpty())
			log.info("No reservations from user " + api.getUserName());
		for (Reservation reservation : allReservationInformation) {
			log.info("Device " + reservation.getDeviceCapabilities().getDeviceName() + " free for "
					+ reservation.getReservedForUser().getName());
			api.removeReservation(reservation.getReservationUid());
		}
	}
	
	public void removeAllDevicesReserved() {
		//***
		log.info("Removing all reservations by user " + api.getUserName());
		List<Reservation> allReservationInformation = api.getAllReservationInformation();
		if (allReservationInformation.isEmpty())
			log.info("No reservations from user " + api.getUserName());
		for (Reservation reservation : allReservationInformation) {
			log.info("Device " + reservation.getDeviceCapabilities().getDeviceName() + " free for "
					+ reservation.getReservedForUser().getName());
			api.removeReservation(reservation.getReservationUid());
		}
	}
	
	public boolean verifyAppUploaded(String nameApp) {
		//***
		log.info("Checking apps uploadeds with name: " + nameApp);
		return api.verifyAlreadyAppUploaded(nameApp);
	}
	
	public void doUploadApp(String appAddress) {
		//***
		log.info("Uploading of app from: " + appAddress);
		try {
			api.uploadApp(appAddress);
		} catch (Exception e) {
			throw new CoreException( "Could not upload from the path: " + appAddress + "\n" + e);
		}
	}
	
	public boolean installApp(String nomeDoArquivo, String udid) {
		//***
		log.info("Installing app on device with udid: " + udid);
		return api.installApplicationByFileName(nomeDoArquivo, udid);		
	}
}

