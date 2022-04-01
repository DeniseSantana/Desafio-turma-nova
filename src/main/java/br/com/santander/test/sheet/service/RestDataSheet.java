package br.com.santander.test.sheet.service;

import java.util.ArrayList;
import java.util.List;

import com.codoid.products.fillo.Recordset;

import br.com.santander.core.sheet.DataSheet;
import br.com.santander.core.sheet.SheetException;
import br.com.santander.core.sheet.SheetReader;
import br.com.santander.test.sheet.model.RestData;
import br.com.santander.test.support.Context;

public class RestDataSheet extends DataSheet {

	private static final String FIELD_USER_ID = "USER_ID";
	private static final String FIELD_USERNAME = "USERNAME";
	private static final String FIELD_PASSWORD = "PASSWORD";
	
	public RestDataSheet() {
		this.excelFilePath = "./test-data/test-data-api.xlsx";
	}
	
	/**
	 * Método para recuperar o array para o data provider de massa
	 * 
	 * @param sheetName
	 * @return lista de massas da planilha
	 * @throws Exception
	 */
	@Override
	public Object[][] getTestData(String sheetName) {
		//String idUsuario = getTestDataListField(sheetName, "select * from " + sheetName + " where ID_CENARIO = '" + Context.getIdScenario() + "'", "ID_USUARIO");					
		return getTestData(sheetName, "select * from " + sheetName + " where ID_CENARIO = '" + Context.getIdScenario() + "'");
	}

	@Override
	protected List<Object> parseRecordsToDataObjectList(Recordset recordset, ArrayList<String> list) {
			List<Object> resultsList = new ArrayList<>();

			try (SheetReader sr = new SheetReader(excelFilePath);) {		
				while (recordset.next()) {
					
					resultsList.add(RestData.builder() //
							.userId(sr.getField(recordset, FIELD_USER_ID))//
							.userName(sr.getField(recordset, FIELD_USERNAME))//
							.password(sr.getField(recordset, FIELD_PASSWORD))//
							.build());
				}
			} catch (Exception e) {
				throw new SheetException(e.getMessage(), e);
			}
			return resultsList;
	}
}
