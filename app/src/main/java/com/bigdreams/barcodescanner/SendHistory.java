package com.bigdreams.barcodescanner;

import java.io.File;
import java.io.PrintWriter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class SendHistory {
	HistoryDatabase hData;
	Cursor cr;
	String content = "";
	Context context;
    ProgressDialog dialog;
	public SendHistory(Context c) {
		hData = new HistoryDatabase(c);
		context = c;
	}

	public void mailHistory() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			
			dialog=ProgressDialog.show(context, "Please wait. .", "processing history elements",true);
			
			File sdCard = Environment.getExternalStorageDirectory();
			File barcodeDir = new File(sdCard.getAbsolutePath()
					+ "/BarcodeBigDreamsApps/");
			barcodeDir.mkdirs();

			File historyFile = new File(barcodeDir, "History.txt");
			try {
				historyFile.createNewFile();

				int status = getHistoryContent();  //return 0 if no history data present otherwise 1

				if (status == 1) {
					PrintWriter pw = new PrintWriter(historyFile);
					pw.print(content);
					pw.flush();
					pw.close();
					
					dialog.dismiss();
					mailTextFile(historyFile);
				}
			} catch (Exception e) {
				Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}

		}

		else {
			// external storage NOT available
			Toast.makeText(context, "Sorry! External Storage not available for processing.", Toast.LENGTH_LONG).show();
			dialog.dismiss();
		}
	}

	public int getHistoryContent() {
		hData.open();
		cr = hData.getDatabaseCursor();

		int count = 0;
		if (cr.getCount() > 0) {
			for (cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
				content += (++count) + ".\n Type: " + cr.getString(1) + "\n Details: "+ cr.getString(2) + 
						"\n Date: "+cr.getString(3)+
						"\n Name: "+ cr.getString(4)+ "\n Category: "+ cr.getString(5)  ;
			}

			return 1;
		} else {
			// pop up a dialog that no history element is present
			Toast.makeText(context, "No History Present", Toast.LENGTH_LONG).show();
			return 0;
		}

	}

	public void mailTextFile(File file) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.putExtra(Intent.EXTRA_SUBJECT,
				"History/QuickBarcodeScanner");
		i.putExtra(
				Intent.EXTRA_TEXT,
				"A text file containing history of Barcode scans is attached with file name \'History.txt\' \n\nGrab this free app at http://goo.gl/ImKFE7");
		i.setType("application/octet-stream");
		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		context.startActivity(Intent.createChooser(i, "Send History via Email"));
	}
}
