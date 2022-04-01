package br.com.santander.core.leanft.object;

import com.hp.lft.sdk.Description;
import com.hp.lft.sdk.Desktop;
import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.TestObject;
import com.hp.lft.sdk.TopLevelObject;

public class ObjectLeanFT {

	private static TestObject app = null;

	public TestObject inicializeObject(Description description) {

		String typeObject = description.getClass().getSimpleName().replace("Description", "");
		String platform = description.getClass().toString().split("\\.")[4].replace("Description", "");
		
		if(typeObject.equals("Window"))
			return convertDesktop(convertClassToLevelObject(typeObject, platform), description);
			
		else
			return convertObject(convertClassTestObject(typeObject, platform), description);	

	}

	private TestObject convertObject(Class<? extends TestObject> objectClass, Description description) {
		try {
			
			return app.describe(objectClass, description);
		} catch (GeneralLeanFtException e) {
			System.out.println("Erro na conversão: " + e.getMessage());
		}
		return null;
	}

	private TestObject convertDesktop(Class<? extends TopLevelObject> objectClass, Description description) {
		try {
			app = Desktop.describe(objectClass, description);
			return app;
		} catch (GeneralLeanFtException e) {
			System.out.println("Erro na conversão: " + e.getMessage());
		}
		return null;
	}

	public void changeParentObject(TestObject object) {
		app = object;
	}


	@SuppressWarnings("unchecked")
	private Class<? extends TestObject> convertClassTestObject(String typeObject, String platform) {
		try {
			return (Class<? extends TestObject>) Class
					.forName("com.hp.lft.sdk.".concat(platform).concat(".").concat(typeObject));

		} catch (ClassNotFoundException e) {
			System.out.println("Tipo de objeto não encontrado: " + e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends TopLevelObject> convertClassToLevelObject(String typeObject, String platform) {
		try {
			return (Class<? extends TopLevelObject>) Class
					.forName("com.hp.lft.sdk.".concat(platform).concat(".").concat(typeObject));

		} catch (ClassNotFoundException e) {
			System.out.println("Tipo de objeto não encontrado: " + e.getMessage());
		}
		return null;
	}


}
