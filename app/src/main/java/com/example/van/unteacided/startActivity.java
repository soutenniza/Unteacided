package com.example.van.unteacided;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class startActivity extends SharedActivity {

    List<String> listHeaders;
    HashMap<String, List<String>> listChilds;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startList();

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.collectionExpandableListView);



        CustomExpanderAdapter expanderAdapter = new CustomExpanderAdapter(this, listHeaders, listChilds);

        listView.setAdapter(expanderAdapter);
    }

    private void startList() {
        settings = getSharedPreferences(PREFS_NAME, 0);
        boolean started = settings.getBoolean("DBstarted", false);
        TeaSQLiteHelper db = new TeaSQLiteHelper(this);

        db.deleteAll();
         startDB();



        List<Tea> teas = db.getAllTeas();


        listHeaders = new ArrayList<String>();
        listChilds = new HashMap<String, List<String>>();

        listHeaders.add("Green");
        listHeaders.add("Black");
        listHeaders.add("White");
        listHeaders.add("Oolong");
        listHeaders.add("Pu'erh");
        listHeaders.add("Herbal");
        listHeaders.add("Rooibos");
        listHeaders.add("Mate");

        List<String> green = new ArrayList<String>();
        ListIterator i = teas.listIterator();
        for (Tea te: teas){
            if(te.getType().equals("Green")){
                green.add(te.getName());
            }
        }

        List<String> black = new ArrayList<String>();
        i = teas.listIterator();
        for (Tea te: teas){
            if(te.getType().equals("Black")){
                black.add(te.getName());
            }
        }

        List<String> white = new ArrayList<String>();
        i = teas.listIterator();
        for (Tea te: teas){
            if(te.getType().equals("White")){
                white.add(te.getName());
            }
        }

        List<String> oolong = new ArrayList<String>();
        i = teas.listIterator();
        for (Tea te: teas){
            if(te.getType().equals("Oolong")){
                oolong.add(te.getName());
            }
        }

        List<String> herbal = new ArrayList<String>();
        i = teas.listIterator();
        for (Tea te: teas){
            if(te.getType().equals("Herbal")){
                herbal.add(te.getName());
            }
        }

        List<String> rooibos = new ArrayList<String>();
        i = teas.listIterator();
        for (Tea te: teas){
            if(te.getType().equals("Rooibos")){
                rooibos.add(te.getName());
            }
        }

        List<String> mate = new ArrayList<String>();
        i = teas.listIterator();
        for (Tea te: teas){
            if(te.getType().equals("Mate")){
                mate.add(te.getName());
            }
        }

        List<String> puerh = new ArrayList<String>();
        i = teas.listIterator();
        for (Tea te: teas){
            if(te.getType().equals("Pu'erh")){
                puerh.add(te.getName());
            }
        }

        listChilds.put(listHeaders.get(0), green);
        listChilds.put(listHeaders.get(1), black);
        listChilds.put(listHeaders.get(2), white);
        listChilds.put(listHeaders.get(3), oolong);
        listChilds.put(listHeaders.get(4), puerh);
        listChilds.put(listHeaders.get(5), herbal);
        listChilds.put(listHeaders.get(6), rooibos);
        listChilds.put(listHeaders.get(7), mate);

    }

    private void startDB(){
        settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("DBstarted", true);
        editor.commit();


        TeaSQLiteHelper db = new TeaSQLiteHelper(this);

        InputStream is = getResources().openRawResource(R.raw.teas);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int c;
        try{
            c = is.read();
            while( c != -1){
                byteArrayOutputStream.write(c);
                c = is.read();
            }
        } catch (Exception e){
            Log.e("ERROR", e.getMessage(), e);
        }

        try{
            JSONObject jsonObject = new JSONObject( byteArrayOutputStream.toString());
            JSONObject jsonObjectResult = jsonObject.getJSONObject("TeaTable");
            JSONArray jsonArray = jsonObjectResult.getJSONArray("TeaItemRow");
            int id;
            String name;
            String type;
            int tempf;
            int tempc;
            int steeptime;
            int activated;
            Tea t = new Tea();
            for(int i = 0; i < jsonArray.length(); i++){
                id = jsonArray.getJSONObject(i).getInt("id");
                name = jsonArray.getJSONObject(i).getString("name");
                type = jsonArray.getJSONObject(i).getString("type");
                tempf = jsonArray.getJSONObject(i).getInt("tempf");
                tempc = jsonArray.getJSONObject(i).getInt("tempc");
                steeptime = jsonArray.getJSONObject(i).getInt("steeptime");
                activated = jsonArray.getJSONObject(i).getInt("activated");
                t.setId(id);
                t.setName(name);
                t.setType(type);
                t.setTempF(tempf);
                t.setTempC(tempc);
                t.setSteepTime(steeptime);
                t.setActivated(activated);
                db.insertTea(t);
            }

        } catch (Exception e ){
            Log.e("Error", e.getMessage(), e);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
