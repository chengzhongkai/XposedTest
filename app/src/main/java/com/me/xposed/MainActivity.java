package com.me.xposed;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.me.xposed.R;
import android.widget.ArrayAdapter;

//import de.robv.android.xposed.XSharedPreferences;

public class MainActivity  extends Activity implements OnClickListener, OnItemSelectedListener{

	String[][] id ={
			 {"770501517700018","softdrinkb0018@gmail.com","softbankq"}
		    ,{"770501517700019","softdrinkb0019@gmail.com","softbankq"}
			,{"770501517700020","softdrinkb0020@gmail.com","softbankq"}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 SharedPreferences prefs = this.getSharedPreferences(
	  		      "prefs",0);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        		
        		R.array.cnt, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        ArrayAdapter<CharSequence> adapterP = ArrayAdapter.createFromResource(this,
        		R.array.points, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
        
        LinearLayout list = (LinearLayout)this.findViewById(R.id.card_list);
        
        String strIds = Utility.input("ids");
        if(strIds.length()>0){
        	String[] arrId=strIds.split("\n");
        	id=new String[arrId.length][3];
        	for (int i = 0; i < arrId.length; i++) {
				id[i][0]=arrId[i].split(":")[0];
				id[i][1]=arrId[i].split(":")[1];
				id[i][2]=arrId[i].split(":")[2];
			}
        }
        
        for (int i = 0; i < id.length; i++) {
        	 LinearLayout item = new LinearLayout(this); 
        	 
        	 Button btnTitle = new Button(this);
        	 btnTitle.setText("Card"+(i+1));
        	 btnTitle.setTag(i);
        	 btnTitle.setOnClickListener(this);
        	 item.addView(btnTitle);
        	 
        	 Spinner cnt= new Spinner(this);
        	 int slt=prefs.getInt("cnt"+i, 0);
        	 if (slt>4 )slt=4;
        	 cnt.setTag("cnt"+i);
        	 cnt.setAdapter(adapter);
        	 cnt.setOnItemSelectedListener(this);
        	 cnt.setSelection(slt);
        	 item.addView(cnt);
        	 
        	 Spinner pnt= new Spinner(this);
        	 slt=prefs.getInt("pnt"+i, 0);
        	 if (slt>20 )slt=20;
        	 pnt.setTag("pnt"+i);
        	 pnt.setAdapter(adapterP);
        	 pnt.setOnItemSelectedListener(this);
        	 pnt.setSelection(slt);
        	 item.addView(pnt);
        	 
        	 list.addView(item);
		}
		
		
	}

	@Override
	public void onClick(View v) {
		Utility.run("am force-stop jp.co.unisys.android.yamadamobile");
		Utility.run("rm /data/data/jp.co.unisys.android.yamadamobile/shared_prefs/prefs.xml");

		Utility.output("imsi",id[(Integer)v.getTag()][0]);
		Utility.output("mail",id[(Integer)v.getTag()][1]);
		Utility.output("pwd",id[(Integer)v.getTag()][2]);
		Intent i;
		PackageManager manager = getPackageManager();
		try {
		    i = manager.getLaunchIntentForPackage("jp.co.unisys.android.yamadamobile");
		    if (i == null)
		        throw new PackageManager.NameNotFoundException();
		    i.addCategory(Intent.CATEGORY_LAUNCHER);
		    startActivity(i);
		} catch (PackageManager.NameNotFoundException e) {

		}
		
	}	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
		SharedPreferences prefs = this.getSharedPreferences(
			      "prefs",0);
		prefs.edit().putInt((String)arg0.getTag(), pos).commit();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
