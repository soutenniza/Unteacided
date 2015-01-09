package com.example.van.unteacided;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.view.CardGridView;


public class CollectionActivity extends SharedActivity {

    ArrayList<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        cards = new ArrayList<Card>();
        intializeCards();

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.gridRelativeLayout);
        rl.setBackgroundResource(R.color.welcome_background);

        CardGridArrayAdapter cardGridArrayAdapter = new CardGridArrayAdapter(this, cards);

        CardGridView gridView = (CardGridView) findViewById(R.id.myGrid);
        if(gridView != null)
            gridView.setAdapter(cardGridArrayAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
            }
        });

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_add){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.add_tea_dialog, null);

            builder.setView(dialogView);

            final EditText inputName = (EditText) dialogView.findViewById(R.id.dialogName);
            final EditText inputF = (EditText) dialogView.findViewById(R.id.editTextFDialog);
            final EditText inputC = (EditText) dialogView.findViewById(R.id.editTextCDialog);
            final EditText inputTime = (EditText) dialogView.findViewById(R.id.editTextSteepDialog);

            final Spinner spinner = (Spinner) dialogView.findViewById(R.id.typeSpinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_names, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            final TeaSQLiteHelper db = new TeaSQLiteHelper(this);

            builder.setPositiveButton("Add Tea", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id){
                            String name = inputName.getText().toString();
                            int tempF = Integer.parseInt(inputF.getText().toString());
                            int tempC = Integer.parseInt(inputC.getText().toString());
                            int steep = Integer.parseInt(inputTime.getText().toString());
                            String type = spinner.getSelectedItem().toString();
                            Tea t = new Tea();
                            t.setName(name);
                            t.setType(type);
                            t.setTempF(tempF);
                            t.setTempC(tempC);
                            t.setSteepTime(steep);
                            t.setActivated(1);
                            db.insertTea(t);
                            finish();
                            startActivity(getIntent());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id){
                            dialogInterface.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();


            TextView textViewF = (TextView) dialog.findViewById(R.id.fDialog);
            textViewF.setText((char) 0x00B0 + "F");

            TextView textViewC = (TextView) dialog.findViewById(R.id.cDialog);
            textViewC.setText((char) 0x00B0 + "C");
        }
        if(id == android.R.id.home){
            super.onBackPressed();
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void intializeCards(){
        final TeaSQLiteHelper db = new TeaSQLiteHelper(this);
        Typeface bold = Typeface.createFromAsset(getAssets(), "Roboto-BoldItalic.ttf");
        Typeface normal = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        TextView textView = (TextView) findViewById(R.id.teaCollectionName);
        textView.setTypeface(bold);
        List<Tea> teaList;
        teaList = db.getAllTeas();
        cards = new ArrayList<Card>();
        for(Tea i: teaList){
            TeaGridCard card = new TeaGridCard(this, i, bold, normal, db);
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
            cards.add(card);

        }
    }

}
