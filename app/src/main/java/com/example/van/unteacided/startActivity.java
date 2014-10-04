package com.example.van.unteacided;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;


public class startActivity extends Activity {

    List<String> listHeaders;
    HashMap<String, List<String>> listChilds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.collectionExpandableListView);

        startList();

        CustomExpanderAdapter expanderAdapter = new CustomExpanderAdapter(this, listHeaders, listChilds);

        listView.setAdapter(expanderAdapter);
    }

    private void startList() {
        TeaSQLiteHelper db = new TeaSQLiteHelper(this);
        db.deleteAll();
        Tea t = new Tea();
        t.setId(0);
        t.setTempF(150);
        t.setTempC(80);
        t.setName("Pearls");
        t.setType("Green");
        t.setSteepTime(120);
        db.insertTea(t);
        t = new Tea();
        t.setId(1);
        t.setTempF(190);
        t.setTempC(90);
        t.setName("White Pearls");
        t.setType("White");
        t.setSteepTime(240);
        db.insertTea(t);
        t = new Tea();
        t.setId(2);
        t.setTempF(190);
        t.setTempC(90);
        t.setName("Silver Needles");
        t.setType("White");
        t.setSteepTime(240);
        db.insertTea(t);
        t = new Tea();
        t.setId(3);
        t.setTempF(206);
        t.setTempC(96);
        t.setName("Yunnan Gold");
        t.setType("Black");
        t.setSteepTime(120);
        db.insertTea(t);

        List<Tea> teas = db.getAllTeas();


        listHeaders = new ArrayList<String>();
        listChilds = new HashMap<String, List<String>>();

        listHeaders.add("Green");
        listHeaders.add("Black");
        listHeaders.add("White");
        listHeaders.add("Oolong");
        listHeaders.add("Herbal");
        listHeaders.add("Rooibos");
        listHeaders.add("Mate");

        List<String> green = new ArrayList<String>();
        green.add("Jasmine Green Tea");
        green.add("Jasmine Pearls");

        List<String> black = new ArrayList<String>();
        black.add("Golden Monkey Black Tea");
        black.add("Black Jasmine Pearls");

        List<String> white = new ArrayList<String>();
        ListIterator i = teas.listIterator();
        for (Tea te: teas){
            if(te.getType().equals("White")){
                white.add(te.getName());
            }
        }

        List<String> oolong = new ArrayList<String>();
        oolong.add("High Mountain");
        oolong.add("Four Seasons");

        List<String> herbal = new ArrayList<String>();
        herbal.add("Pink Lemonade");

        List<String> rooibos = new ArrayList<String>();
        rooibos.add("Rooibos");

        List<String> mate = new ArrayList<String>();
        mate.add("Mate");

        listChilds.put(listHeaders.get(0), green);
        listChilds.put(listHeaders.get(1), black);
        listChilds.put(listHeaders.get(2), white);
        listChilds.put(listHeaders.get(3), oolong);
        listChilds.put(listHeaders.get(4), herbal);
        listChilds.put(listHeaders.get(5), rooibos);
        listChilds.put(listHeaders.get(6), mate);

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
