package br.com.santander.test.sheet.service;

import java.util.ArrayList;
import java.util.List;

import com.codoid.products.fillo.Recordset;

import br.com.santander.core.sheet.DataSheet;
import br.com.santander.core.sheet.SheetException;
import br.com.santander.core.sheet.SheetReader;
import br.com.santander.test.sheet.model.FormularioData;
import br.com.santander.test.support.Context;

public class FormularioDataSheet extends DataSheet {

	
	public FormularioDataSheet() {
		this.excelFilePath = "./test-data/test-data-formulario.xlsx";
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
					
					resultsList.add(FormularioData.builder() //
						.idcenario(sr.getField(recordset,"ID_CENARIO"))
						.nome(sr.getField(recordset,"NOME"))
						.ultimo_nome(sr.getField(recordset,"ULTIMO_NOME"))
						.email(sr.getField(recordset,"EMAIL"))
						.endereco(sr.getField(recordset,"ENDERECO"))
						.universidade(sr.getField(recordset,"UNIVERSIDADE"))
						.profissao(sr.getField(recordset,"PROFISSAO"))
						.genero(sr.getField(recordset,"GENERO"))
						.idade(sr.getField(recordset,"IDADE"))
						.build());
				}
			} catch (Exception e) {
				throw new SheetException(e.getMessage(), e);
			}
			return resultsList;
	}
}
