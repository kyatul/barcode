package com.bigdreams.barcodescanner;

import java.util.ArrayList;
import java.util.Collections;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

public class HistoryDatabase {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_TYPE = "type";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_TIME = "time";
	public static final String KEY_STATUS = "status";
	public static final String KEY_NAME = "name";
	public static final String KEY_CATEGORY = "category";

	public static final String DATABASE_NAME = "new_database";
	public static final String DATABASE_TABLE = "history_lists";
	public static final int DATABASE_VERSION = 1;

	DbHelper ourHelper;
	Context ourContext;
	SQLiteDatabase ourDatabase;
	Time today;


    class DbHelper extends SQLiteOpenHelper {

		DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT , " + KEY_TYPE
					+ " TEXT NOT NULL, " + KEY_CONTENT + " TEXT NOT NULL, "
					+ KEY_TIME + " TEXT , "+ KEY_NAME + " TEXT , "+ KEY_CATEGORY + " TEXT , " + KEY_STATUS
					+ " TEXT NOT NULL );");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}// END OF DBHELPER CLASS

	public HistoryDatabase(Context c) {
		ourContext = c;
	}

	public HistoryDatabase open() {
		
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {

		ourHelper.close();
	}
    
	public SQLiteDatabase getDatabase(){
		return ourDatabase;
	}

	public void newEntry(String barcode_type, String barcode_content,String originCountry,String name,String category) {
				
		ContentValues cv=new ContentValues();
		cv.put(KEY_TYPE, barcode_type);
		cv.put(KEY_CONTENT, barcode_content);
		cv.put(KEY_TIME, ScanDate.getDate());
		cv.put(KEY_STATUS,originCountry);    //we are using status to save "country of origin"
		cv.put(KEY_NAME,name);
		cv.put(KEY_CATEGORY,category);
		ourDatabase.insert(DATABASE_TABLE, null, cv);
		
	}
	
	public Cursor getDatabaseCursor(){
		
		String[] col = new String[] { KEY_ROWID,KEY_TYPE,KEY_CONTENT,KEY_TIME,KEY_STATUS,KEY_NAME,KEY_CATEGORY  };
		Cursor cr = ourDatabase.query(DATABASE_TABLE, col, null, null,
				null, null, null);
		return cr;		
	} 
	
	public ArrayList<String> getHistoryDataName(){
		ArrayList<String> ab=new ArrayList<String>();
		Cursor c =getDatabaseCursor();
		String nameTemp="";
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			nameTemp=c.getString(c.getColumnIndex(KEY_NAME));

            if(nameTemp.equals("No match found")){
				nameTemp=c.getString(c.getColumnIndex(KEY_CONTENT));

                if(nameTemp.length()>22){
                    nameTemp=nameTemp.substring(0, 22)+"...";
                }
			}

            if(nameTemp.length()>22){
                nameTemp=nameTemp.substring(0, 22)+"...";
            }

			ab.add(nameTemp);
		}
		Collections.reverse(ab);
		return ab;
		
	}

    public ArrayList<String> getHistoryDataDate(){
        ArrayList<String> ab=new ArrayList<String>();
        Cursor c =getDatabaseCursor();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            ab.add(c.getString(c.getColumnIndex(KEY_TIME)));
        }
        Collections.reverse(ab);
        return ab;
    }

	public void deleteAll() {

        ourDatabase.delete(DATABASE_TABLE, null, null);
	}


    public void deleteHistoryItem(String date) {
        String selection = KEY_TIME + " = '"
                + date + "'";
        ourDatabase.delete(DATABASE_TABLE, selection, null);
    }
	
	public int getRowCount() {

		String[] col = new String[] { KEY_ROWID, KEY_TYPE, KEY_CONTENT,KEY_STATUS,KEY_NAME,KEY_CATEGORY };
		Cursor cr = ourDatabase.query(DATABASE_TABLE, col, null, null, null,
				null, null);
		return cr.getCount();
	}
	
	public Cursor getHistoryItemCursor(String time){
		
	
		String[] projection = new String[] {KEY_ROWID,KEY_TYPE,KEY_CONTENT,KEY_TIME,KEY_STATUS,KEY_NAME,KEY_CATEGORY};
		String selection = KEY_TIME + " = '"
				+ time + "'";

		Cursor cr = ourDatabase.query(DATABASE_TABLE, projection, selection, null, null,null, null);
		return cr;
		
	}
	
}

