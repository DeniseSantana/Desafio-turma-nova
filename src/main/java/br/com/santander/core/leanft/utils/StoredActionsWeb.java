package br.com.santander.core.leanft.utils;

import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.TestObject;
import com.hp.lft.sdk.web.EditField;
import com.hp.lft.sdk.web.ListBox;

public final class StoredActionsWeb extends StoredActions {
	
	public void select(TestObject object, String option)
	{
		ListBox listBox = (ListBox) object;
		try {
			listBox.select(option);
		} catch (GeneralLeanFtException e) {
			
		}
	}
	
	public void setValue(TestObject object, String value)
	{
		EditField editField = (EditField) object;
		try {
			editField.setValue(value);
		} catch (GeneralLeanFtException e) {
			e.printStackTrace();
		}
	}
	
}
