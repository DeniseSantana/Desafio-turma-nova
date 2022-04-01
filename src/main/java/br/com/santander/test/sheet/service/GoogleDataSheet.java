package br.com.santander.test.sheet.service;

import java.util.ArrayList;
import java.util.List;

import com.codoid.products.fillo.Recordset;

import br.com.santander.core.sheet.DataSheet;
import br.com.santander.core.sheet.SheetException;
import br.com.santander.core.sheet.SheetReader;
import br.com.santander.test.sheet.model.GoogleData;

public class GoogleDataSheet extends DataSheet {

	public GoogleDataSheet() {
		this.excelFilePath = "./test-data/test-data-google.xlsx";
	}

	@Override
	protected List<Object> parseRecordsToDataObjectList(Recordset recordset, ArrayList<String> list) {
			List<Object> resultsList = new ArrayList<>();
			try (SheetReader sr = new SheetReader(excelFilePath);) 
			{			
				while (recordset.next()) {
					resultsList.add(GoogleData.builder() //
							.queryText(sr.getField(recordset, "QUERY_TEXT"))//
							.build());
			}
			} catch (Exception e) {
				throw new SheetException(e.getMessage(), e);
			}
			return resultsList;		
	}
}
