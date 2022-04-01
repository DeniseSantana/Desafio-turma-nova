package br.com.santander.core.leanft.utils;

import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.TestObject;
import com.hp.lft.sdk.stdwin.EditField;

public final class StoredActionsStandardWindows extends StoredActions {

	public void setText(TestObject object, String text) {
		EditField editField = null;
		try {
			editField = (EditField) object;
			editField.setText(text);
		} catch (GeneralLeanFtException e) {
			e.printStackTrace();
		}
	}
}
