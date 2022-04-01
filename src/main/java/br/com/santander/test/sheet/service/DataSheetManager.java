package br.com.santander.test.sheet.service;

import java.util.List;

import br.com.santander.core.utils.Tag;
import br.com.santander.test.support.Context;
import br.com.santander.test.support.DataTypeTag;

/**
 * Classe auxiliar centralizando a criação de todos datasheets
 *
 */
public class DataSheetManager {

	private static final String INITIAL_NAME_SHEET_MOBILE = "DISPOSITIVO_";

	public DataSheetManager() {
	}

	/**
	 * Método para retornar os dados correspondentes ao runner, adicionar novos
	 * datasheets como um case no switch.
	 * 
	 * @param tags
	 * @return massa da planilha correspondente ao runner
	 * @throws Exception
	 */
	public Object[][] getTestData(Tag tag) {
		List<String> listTags = tag.getListTags();
		String sheetName = null;

		if (listTags.contains(DataTypeTag.MPJ)) {
			sheetName = INITIAL_NAME_SHEET_MOBILE + Context.getDevice().getId();
			Context.setSheetName(sheetName);
			return new MobileDataSheet().getTestData(sheetName);
			
		} else if (listTags.contains(DataTypeTag.GOOGLE)) {
			sheetName = tag.convertTagToSimpleName(DataTypeTag.GOOGLE);
			Context.setSheetName(sheetName);
			return new GoogleDataSheet().getTestData(sheetName);
		}

		else if (listTags.contains(DataTypeTag.BUSCA)) {
			sheetName = tag.convertTagToSimpleName(DataTypeTag.BUSCA);
			Context.setSheetName(sheetName);
			return new BuscaDataSheet().getTestData(sheetName);

		} else if (listTags.contains(DataTypeTag.API)) {
			sheetName = tag.convertTagToSimpleName(DataTypeTag.API);
			Context.setSheetName(sheetName);
			return new RestDataSheet().getTestData(sheetName);
			
		}else 
				if (listTags.contains(DataTypeTag.FORMULARIO)) {
				sheetName = tag.convertTagToSimpleName(DataTypeTag.FORMULARIO);
				Context.setSheetName(sheetName);
				return new FormularioDataSheet().getTestData(sheetName);

		} else {
			throw new RuntimeException("Planilha de massa " + (sheetName == null ? "" : sheetName + " ")
					+ "não encontrada, verificar tags da classe Runner");
		}

		}
}
	
