package com.me.xposed;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.me.xposed.R;

public class MailActivity extends Activity implements OnClickListener {
	TextView title;

	String from = "";
	String pass = "";
	String to = "";
	String body = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mail);

		from = Utility.input("mail");
		pass = Utility.input("pwd");
		((TextView) findViewById(R.id.from)).setText("From:" + from);

		Intent intent = getIntent();
		if (intent.getType().equals("text/plain")) {
			body = intent.getStringExtra(Intent.EXTRA_TEXT);
			if (body != null) {
				((TextView) findViewById(R.id.mailText)).setText("Content:"
						+ body);
			}
			String[] addr = intent.getStringArrayExtra(Intent.EXTRA_EMAIL);
			if (addr != null) {
				to = addr[0];
				((TextView) findViewById(R.id.to)).setText("To:" + to);
			}
		}

		Button send = (Button) findViewById(R.id.send);
		send.setOnClickListener(this);
		onClick(send);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		view.setEnabled(false);
		MailSender mailSender = new MailSender(this);
		mailSender.execute(from, pass, to, "", body);
		// sendMail(from, pass, to, "", body);
		// Toast.makeText(this, "mail has sent", Toast.LENGTH_SHORT).show();
		// this.finish();
	}

	
	
}

