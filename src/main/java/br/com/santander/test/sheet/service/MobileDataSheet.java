package br.com.santander.test.sheet.service;

import java.util.ArrayList;
import java.util.List;

import com.codoid.products.fillo.Recordset;

import br.com.santander.core.sheet.DataSheet;
import br.com.santander.core.sheet.SheetException;
import br.com.santander.core.sheet.SheetReader;
import br.com.santander.test.sheet.model.MobileData;
import br.com.santander.test.support.Context;

public class MobileDataSheet extends DataSheet {

	private static final String SHEET_USERS = "USUARIOS";
	private static final String FIELD_AGENCIA = "AGENCIA";
	private static final String FIELD_CONTA = "CONTA";
	private static final String FIELD_USUARIO = "USUARIO";
	private static final String FIELD_SENHA = "SENHA";
	
	public MobileDataSheet() {
		this.excelFilePath = "./test-data/test-data-mobile.xlsx";
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
		String idUsuario = getTestDataListField(sheetName, "select * from " + sheetName + " where ID_CENARIO = '" + Context.getIdScenario() + "'", "ID_USUARIO");					
		return getTestData(sheetName, "select * from " + SHEET_USERS + " where ID_USUARIO = '" + idUsuario + "'");
	}

	@Override
	protected List<Object> parseRecordsToDataObjectList(Recordset recordset, ArrayList<String> list) {
			List<Object> resultsList = new ArrayList<>();

			try (SheetReader sr = new SheetReader(excelFilePath);) {		
				while (recordset.next()) {
					
					resultsList.add(MobileData.builder() //
							.agencia(sr.getField(recordset, FIELD_AGENCIA))//
							.conta(sr.getField(recordset, FIELD_CONTA))//
							.usuario(sr.getField(recordset, FIELD_USUARIO))//
							.senha(sr.getField(recordset, FIELD_SENHA))//
							.build());
				}
			} catch (Exception e) {
				throw new SheetException(e.getMessage(), e);
			}
			return resultsList;
		
	}
}
