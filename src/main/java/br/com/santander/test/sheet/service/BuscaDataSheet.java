package br.com.santander.test.sheet.service;

import java.util.ArrayList;
import java.util.List;

import com.codoid.products.fillo.Recordset;

import br.com.santander.core.sheet.DataSheet;
import br.com.santander.core.sheet.SheetException;
import br.com.santander.core.sheet.SheetReader;
import br.com.santander.test.sheet.model.BuscaData;

public class BuscaDataSheet extends DataSheet{

	public BuscaDataSheet() {
		this.excelFilePath = "./test-data/test-data-busca.xlsx";
	}
	
	@Override
	protected List<Object> parseRecordsToDataObjectList(Recordset recordset, ArrayList<String> list) {
			List<Object> resultsList = new ArrayList<>();
			try (SheetReader sr = new SheetReader(excelFilePath);) 
			{			
				while (recordset.next()) {
					resultsList.add(BuscaData.builder() //
							.idcenario(sr.getField(recordset, "ID_CENARIO"))//
							.firstname(sr.getField(recordset, "FIRST_NAME"))
							.lastname(sr.getField(recordset, "LAST_NAME"))
							.password(sr.getField(recordset, "PASSWORD"))
							.email(sr.getField(recordset, "EMAIL"))
							.textarea(sr.getField(recordset, "TEXTAREA"))
							.build());
			}
			} catch (Exception e) {
				throw new SheetException(e.getMessage(), e);
			}
			return resultsList;		
	}
}
