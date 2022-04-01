package br.com.santander.core.leanft.utils;

import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.TestObject;
import com.hp.lft.sdk.java.Editor;
import com.hp.lft.sdk.java.UiObjectBase;

public final class StoredActionsJava extends StoredActions {

	public void setText(TestObject object, String text) {
		Editor editor = null;
		try {
			editor = (Editor) object;
			editor.setText(text);
		} catch (GeneralLeanFtException e) {
			e.printStackTrace();
		}
	}

	public void waitBeEnabled(TestObject object, int timeOut) {
		UiObjectBase uiObject = (UiObjectBase) object;

		try {
			while (!uiObject.isEnabled()) {
				if (timeOut == 0)
					break;

				timeOut--;
			}
		} catch (GeneralLeanFtException e) {
			e.printStackTrace();
		}
	}

}
