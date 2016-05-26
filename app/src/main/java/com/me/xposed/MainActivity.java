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
import android.widget.ListView;
import android.widget.Spinner;

import com.me.xposed.R;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity  {

    private ArrayList<Tag> taglist = new ArrayList<>();
    TagLineAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new TagLineAdapter(MainActivity.this);
        adapter.setTagtList(taglist);
        listView.setAdapter(adapter);

        SharedPreferences prefs = this.getSharedPreferences(
                "prefs", 0);

        String strIds = Utility.input("ids");
        if (strIds.length() > 0) {
            String[] arrId = strIds.split("\n");
            for (int i = 0; i < arrId.length; i++) {
                String[] taginfo = arrId[i].split(":");
                Tag tag = new Tag();
                tag.setImsi(taginfo[0]);
                tag.setMail(taginfo[1]);
                tag.setPwd(taginfo[2]);
                tag.setCnt(prefs.getInt("cnt" + i, 0));
                tag.setPoint( prefs.getInt("pnt" + i, 0));
                taglist.add(tag);

            }
            adapter.notifyDataSetChanged();
        }

    }

}
