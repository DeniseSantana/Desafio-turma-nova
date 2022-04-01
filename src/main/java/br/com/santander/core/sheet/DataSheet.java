package br.com.santander.core.sheet;

import java.util.ArrayList;
import java.util.List;

import com.codoid.products.fillo.Recordset;

/**
 * Classe abstrata com métodos para leitura de uma aba do .xlsx
 *
 */
public abstract class DataSheet {

	protected String excelFilePath;

	/**
	 * Método para recuperar o array para o data provider de massa
	 * 
	 * @param sheetName
	 * @return lista de massas da planilha
	 * @throws Exception
	 */
	public Object[][] getTestData(String sheetName) {
		return getTestData(sheetName, "select * from " + sheetName);
	}
	
	/**
	 * Método para recuperar o array para o data provider de massa
	 * 
	 * @param sheetName
	 * @return lista de massas da planilha
	 * @throws Exception
	 */
	public Object[][] getTestData(String sheetName, String query) {
		try (SheetReader reader = new SheetReader(excelFilePath)) {
			Recordset recordset = reader.performQuery(query);

			ArrayList<String> list = recordset.getFieldNames();
			List<Object> testDataList = parseRecordsToDataObjectList(recordset, list);

			return listToArray(testDataList);
		} catch (Exception e) {
			throw new SheetException("Erro ao capturar dados de teste: " + e.getMessage(), e);
		}		
	}
	
	/**
	 * Método para recuperar o array para o data provider de massa
	 * 
	 * @param sheetName
	 * @return lista de massas da planilha
	 * @throws Exception
	 */
	public String getTestDataListField(String sheetName, String query, String field) {
		try (SheetReader reader = new SheetReader(excelFilePath)) 
		{					
			Recordset recordset = reader.performQuery(query);
			recordset.next();
			return recordset.getField(field);
		} catch (Exception e) {
			throw new SheetException("Erro ao capturar dados de teste: " + e.getMessage(), e);
		}	
		
	}

	/**
	 * Método abstrato para fazer a leitura das colunas da planilha para o model
	 * criado
	 * 
	 * @param recordset
	 * @param list
	 * @return lista de objetos do model da planilha
	 * @throws Exception
	 */
	protected abstract List<Object> parseRecordsToDataObjectList(Recordset recordset, ArrayList<String> list);

	/**
	 * Método auxiliar para converter lista em array de objetos
	 * 
	 * @param listaMassaDeDados
	 * @return array convertido de massa
	 */
	protected Object[][] listToArray(List<Object> listaMassaDeDados) {
		Object[][] arrayMassa = new Object[listaMassaDeDados.size()][];

		for (int i = 0; i < listaMassaDeDados.size(); i++)
			arrayMassa[i] = new Object[] { listaMassaDeDados.get(i) };

		return arrayMassa;
	}

	/**
	 * Método para fazer update de um valor num certo registro
	 * 
	 * @param sheetName
	 * @param fieldToUpdate
	 * @param fieldToUpdateValue
	 * @param registerField
	 * @param registerFieldValue
	 */
	protected void updateResult(String sheetName, String fieldToUpdate, String fieldToUpdateValue, String registerField,
			String registerFieldValue) {
		try (SheetReader sr = new SheetReader(excelFilePath)) {
			sr.performUpdate("update " + sheetName + " set " + fieldToUpdate + "='" + fieldToUpdateValue + "' where "
					+ registerField + "='" + registerFieldValue + "'");
		} catch (Exception e) {
			throw new SheetException("Erro ao fazer update do resultado", e);
		}
		
	}
}