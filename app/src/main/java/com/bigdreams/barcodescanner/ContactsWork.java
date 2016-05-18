package com.bigdreams.barcodescanner;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactsWork {
	Context context;
    ArrayList<ListContactItem> temp;
	public ContactsWork(Context c) {
		this.context = c;
		temp=new ArrayList<ListContactItem>();
	}

	public ArrayList<ListContactItem> getNames() {

		Cursor c = getCursorContacts();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			temp.add(new ListContactItem(name));
		}
		
		return temp;

	}

	public ArrayList<ListContactItem> getNumbers(String name){
		Cursor c = getCursorNumberContacts(name);
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			temp.add(new ListContactItem(number));
		}

		return temp;
	}
	public Cursor getCursorContacts() {
		// Run query
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] {ContactsContract.Contacts.DISPLAY_NAME};
		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
				+ "1" + "'";
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " COLLATE LOCALIZED ASC";

		return context.getContentResolver().query(uri, projection,selection, null, sortOrder);
	}

	private Cursor getCursorNumberContacts(String name) {
		// Run query
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER };
		String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
				+ " = ?";

		return context.getContentResolver().query(uri, projection,
				selection, new String[] { name }, null);
	}
}
