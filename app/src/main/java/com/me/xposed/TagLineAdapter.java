package com.me.xposed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.view.View.OnClickListener;

/**
 * Created by cheng on 2016/05/24.
 */
public class TagLineAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Tag> tagList;
    SharedPreferences prefs;
    public TagLineAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.prefs= context.getSharedPreferences(
                "prefs", 0);
    }

    public void setTagtList(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public int getCount() {
        return tagList.size();
    }

    @Override
    public Object getItem(int position) {
        return tagList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tagList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.id_row, parent, false);
        convertView.setTag(position);
        //((TextView)convertView.findViewById(R.id.name)).setText("氏名："+tagList.get(position).getName());
        Button btn = (Button) convertView.findViewById(R.id.btOpewn);
        btn.setText("Card" + (position + 1));
        btn.setTag(position);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Activity activity = (Activity)(v.getContext());
                activity.setTitle(((Button)v).getText().toString());
                int index = (int) v.getTag();
                Utility.run("am force-stop jp.co.unisys.android.yamadamobile");
                Utility.run("rm /data/data/jp.co.unisys.android.yamadamobile/shared_prefs/prefs.xml");

                Utility.output("imsi", tagList.get(index).getImsi());
                Utility.output("mail", tagList.get(index).getMail());
                Utility.output("pwd", tagList.get(index).getPwd());
                Intent i;
                PackageManager manager = v.getContext().getPackageManager();
                try {
                    i = manager.getLaunchIntentForPackage("jp.co.unisys.android.yamadamobile");
                    if (i == null)
                        throw new PackageManager.NameNotFoundException();
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    v.getContext().startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {

                }
            }
        });
        Spinner spCnt=(Spinner) convertView.findViewById(R.id.cnt);
        spCnt.setSelection(tagList.get(position).getCnt());
        spCnt.setTag("cnt" + position);
        spCnt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                prefs.edit().putInt((String) parent.getTag(), position).commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Spinner spPoint=(Spinner) convertView.findViewById(R.id.point);
        spPoint.setSelection(tagList.get(position).getPoint());
        spPoint.setTag("pnt" + position);
        spPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                prefs.edit().putInt((String) parent.getTag(), position).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return convertView;
    }


}
