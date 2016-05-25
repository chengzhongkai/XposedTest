package com.me.xposed;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MailSender extends AsyncTask<String, Integer, Boolean>{
	String err = "";
	private Activity uiActivity_;

	public MailSender(Activity activity) {
		super();
		uiActivity_ = activity;
	}

	@Override
	protected Boolean doInBackground(String... contents) {
		String from = contents[0];
		String pass = contents[1];
		String to = contents[2];
		String title = contents[3];
		String body = contents[4];

		return sendMail(from, pass, to, title, body);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		String msg = "";
		if(err.length()>0)
			Toast.makeText(uiActivity_, err, Toast.LENGTH_SHORT).show();
		if (result.booleanValue()) {
			msg = "mail has sent";
			Toast.makeText(uiActivity_, msg, Toast.LENGTH_SHORT).show();
			
			Intent i;
			PackageManager manager = uiActivity_.getPackageManager();
			try {
			    i = manager.getLaunchIntentForPackage("jp.co.unisys.android.yamadamobile");
			    if (i == null)
			        throw new PackageManager.NameNotFoundException();
			    i.addCategory(Intent.CATEGORY_LAUNCHER);
			    uiActivity_.startActivity(i);
			} catch (PackageManager.NameNotFoundException e) {

			}
			
			uiActivity_.finish();
			
		} else {
			msg = "can not send mail";
			Toast.makeText(uiActivity_, msg, Toast.LENGTH_SHORT).show();
			((Button)uiActivity_.findViewById(R.id.send)).setEnabled(true);
		}

	}

	boolean sendMail(String from, String pass, String to, String title,
			String body) {
		boolean re = true;
		try {
			GMailSender sender = new GMailSender(from, pass);
			re = sender.sendMail(title, body, from, to);
		} catch (Exception e) {
			re = false;
			err =  e.getMessage();
			Log.e("SendMail", e.getMessage(), e);
		}
		return re;
	}

	
	
}