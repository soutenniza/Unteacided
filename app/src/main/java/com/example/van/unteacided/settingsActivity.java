package com.example.van.unteacided;


import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;


public class settingsActivity extends SharedActivity {

    List<String> listHeaders;
    HashMap<String, List<String>> listChilds;
    SharedPreferences settings;
    ArrayList<Card> cards;
    String[] navTitles;
    DrawerLayout drawerLayout;
    ListView drawerList;
    TeaSQLiteHelper db;
    boolean started;
    ActionBarDrawerToggle drawerToggle;
    CountDownTimer countDownTimer;
    Card teacard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "Roboto-Regular.ttf");


        startSettings();
        settings = getSharedPreferences(PREFS_NAME, 0);
        started = settings.getBoolean("DBstarted", false);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setContentView(R.layout.activity_start);

        startSettings();
    }

    private void startCards(){
        intializeCards();

        CardListView cardListView = (CardListView) findViewById(R.id.cardList);
        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this, cards);
        if(cardListView != null){
            cardListView.setAdapter(cardArrayAdapter);
        }
    }

    private void startSettings(){
        startDrawer();
        settings = getSharedPreferences(PREFS_NAME, 0);
        boolean started = settings.getBoolean("DBstarted", false);
        db = new TeaSQLiteHelper(this);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.startRL);
        rl.setBackgroundResource(R.color.welcome_background);
    }

    private void startDrawer(){
        navTitles = getResources().getStringArray(R.array.navis);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_start);
        drawerList = (ListView) findViewById(R.id.navigationDrawerList);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, navTitles));
        drawerList.setOnItemClickListener(new DrawerItemClickListerner());

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                actionBar.setTitle("Unteacided");
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view){
                super.onDrawerOpened(view);
                actionBar.setTitle("Menu");
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

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
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home){
            if(drawerLayout.isDrawerOpen(drawerList)){
                drawerLayout.closeDrawer(drawerList);
            }else
                drawerLayout.openDrawer(drawerList);
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_collection){
            Intent i = new Intent(settingsActivity.this, CollectionActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(drawerList)){
            drawerLayout.closeDrawer(drawerList);
        }else
            super.onBackPressed();
    }

    public void intializeCards(){
        TeaSQLiteHelper db = new TeaSQLiteHelper(this);
        Typeface bold = Typeface.createFromAsset(getAssets(), "Roboto-BoldItalic.ttf");
        Typeface normal = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        List<Tea> teaList;
        teaList = db.getAllTeas();
        cards = new ArrayList<Card>();
        long seed = System.nanoTime();
        Collections.shuffle(teaList, new Random(seed));
        for(Tea i: teaList){
            TeaCard card = new TeaCard(this, i, bold, normal);
            if(i.getType().equalsIgnoreCase("Green"))
                card.setBackgroundResourceId(R.color.green_background);
            if(i.getType().equalsIgnoreCase("Oolong"))
                card.setBackgroundResourceId(R.color.oolong_background);
            if(i.getType().equalsIgnoreCase("Black"))
                card.setBackgroundResourceId(R.color.black_background);
            if(i.getType().equalsIgnoreCase("Rooibos"))
                card.setBackgroundResourceId(R.color.rooibos_background);
            if(i.getType().equalsIgnoreCase("Mate"))
                card.setBackgroundResourceId(R.color.mate_background);
            if(i.getType().equalsIgnoreCase("White"))
                card.setBackgroundResourceId(R.color.white_background);
            if(i.getType().equalsIgnoreCase("Herbal"))
                card.setBackgroundResourceId(R.color.herbal_background);
            if(i.getType().equalsIgnoreCase("Pu'erh"))
                card.setBackgroundResourceId(R.color.puerh_background);
            card.setShadow(true);
            if(i.isActive() == 1)
                card.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        openDialog(card);
                    }
                });
                cards.add(card);
        }
    }

    private class DrawerItemClickListerner implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id){
            selectItem(position);
        }
    }

    private void selectItem(int p){
        switch(p){
            case 0:
                CardListView cardListView = (CardListView) findViewById(R.id.cardList);
                Collections.shuffle(cards);
                CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this, cards);
                if(cardListView != null){
                    cardListView.setAdapter(cardArrayAdapter);
                    drawerLayout.closeDrawer(drawerList);
                }
                break;
            case 1:
                Intent i = new Intent(settingsActivity.this, CollectionActivity.class);
                startActivity(i);
                break;
        }
    }

    private void openDialog(Card card){
        Typeface bold = Typeface.createFromAsset(getAssets(), "Roboto-BoldItalic.ttf");
        Typeface normal = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        teacard = card;
        final int time = ((TeaCard) teacard).steep;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timer_dialog, null);

        builder.setView(dialogView).setNegativeButton("Close", new DialogInterface.OnClickListener(){
           @Override
            public void onClick(DialogInterface dialogInterface, int id){
               dialogInterface.cancel();
           }
        });

        final Spinner spinner = (Spinner) dialogView.findViewById(R.id.timeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(time/15 - 1);

        final ArcProgress arcProgress = (ArcProgress) dialogView.findViewById(R.id.arc_progress);
        setArcColors(teacard, arcProgress);

        final Button button = (Button) dialogView.findViewById(R.id.stopStartButton);
        button.setTypeface(normal);

        TextView title = (TextView) dialogView.findViewById(R.id.timerTitle);
        title.setTypeface(bold);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().equalsIgnoreCase("Start")) {
                    button.setText("Stop");
                    final long temp = (spinner.getSelectedItemPosition() + 1) * 15 * 1000;
                    countDownTimer = new CountDownTimer(temp, 100) {
                        public void onTick(long millisUntilFinished) {
                            float progress = ((float) millisUntilFinished / temp);
                            progress = 1 - progress;
                            progress *= 100;
                            arcProgress.setProgress((int) (progress));
                            int minutes = (int) (millisUntilFinished / 1000) / 60;
                            int seconds = (int) (millisUntilFinished / 1000) % 60;

                            arcProgress.setBottomText(millisToMin(minutes, seconds) + " left");
                        }

                        public void onFinish() {
                            arcProgress.setProgress(100);
                            arcProgress.setBottomText("Done!");
                            button.setText("Start");
                            button.setClickable(true);
                            teaReady(((TeaCard) teacard).type);
                        }
                    }.start();
                }else{
                    countDownTimer.cancel();
                    arcProgress.setBottomText("Stopped");
                    button.setText("Start");
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private String millisToMin(int m, int s){
        if(s == 0){
            return String.format("%d:00", m);
        }else
            return String.format("%d:%02d", m, s);
    }

    private void setArcColors(Card c, ArcProgress arcProgress){
        if(((TeaCard) c).type.equalsIgnoreCase("Green")){
            arcProgress.setFinishedStrokeColor(getResources().getColor(R.color.green_text));
            arcProgress.setUnfinishedStrokeColor(getResources().getColor(R.color.green_background));
            arcProgress.setTextColor(getResources().getColor(R.color.green_background));
        }
        if(((TeaCard) c).type.equalsIgnoreCase("Oolong")){
            arcProgress.setFinishedStrokeColor(getResources().getColor(R.color.oolong_text));
            arcProgress.setUnfinishedStrokeColor(getResources().getColor(R.color.oolong_background));
            arcProgress.setTextColor(getResources().getColor(R.color.oolong_background));
        }
        if(((TeaCard) c).type.equalsIgnoreCase("Pu'erh")){
            arcProgress.setFinishedStrokeColor(getResources().getColor(R.color.puerh_text));
            arcProgress.setUnfinishedStrokeColor(getResources().getColor(R.color.puerh_background));
            arcProgress.setTextColor(getResources().getColor(R.color.puerh_background));
        }
        if(((TeaCard) c).type.equalsIgnoreCase("Herbal")){
            arcProgress.setFinishedStrokeColor(getResources().getColor(R.color.herbal_background));
            arcProgress.setUnfinishedStrokeColor(getResources().getColor(R.color.herbal_text));
            arcProgress.setTextColor(getResources().getColor(R.color.herbal_text));
        }
        if(((TeaCard) c).type.equalsIgnoreCase("Rooibos")){
            arcProgress.setFinishedStrokeColor(getResources().getColor(R.color.rooibos_text));
            arcProgress.setUnfinishedStrokeColor(getResources().getColor(R.color.rooibos_background));
            arcProgress.setTextColor(getResources().getColor(R.color.rooibos_background));
        }
        if(((TeaCard) c).type.equalsIgnoreCase("Mate")){
            arcProgress.setFinishedStrokeColor(getResources().getColor(R.color.mate_text));
            arcProgress.setUnfinishedStrokeColor(getResources().getColor(R.color.mate_background));
            arcProgress.setTextColor(getResources().getColor(R.color.mate_background));
        }
    }

    private void teaReady(String type){
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Unteacided")
                .setContentText("Your " + type + " Tea is ready!");
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        manager.notify(001, nBuilder.build());
        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }
}
