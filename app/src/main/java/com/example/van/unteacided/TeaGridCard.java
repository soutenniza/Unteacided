package com.example.van.unteacided;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.views.FlatCheckBox;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Van on 10/5/2014.
 */
public class TeaGridCard extends Card {
    protected Tea t;
    protected TextView teaName;
    protected RelativeLayout relativeLayout;
    protected String name;
    protected String type;
    protected Context context;
    protected Typeface bold;
    protected Typeface normal;
    protected CheckBox teaActivated;
    protected int activated;
    protected TeaSQLiteHelper db;


    public TeaGridCard(Context c, Tea t, Typeface b, Typeface n, TeaSQLiteHelper db){
        this(c);
        this.t = t;
        context = c;
        this.name = t.getName();
        this.type = t.getType();
        this.bold = b;
        this.normal = n;
        this.activated = t.isActive();
        this.db = db;
    }

    public TeaGridCard(Context c){
        this(c, R.layout.teagridcard);
    }

    public TeaGridCard(Context c, int innerLayout){
        super(c, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view){
        teaName = (TextView) parent.findViewById(R.id.teaGridName);
        teaActivated = (CheckBox) parent.findViewById(R.id.teaGridFlatCheck);

        relativeLayout = (RelativeLayout) parent.findViewById(R.id.gridBackground);

        if(teaName != null){
            teaName.setText(name);
            setTVColor(teaName);
            setFont(teaName, bold);
        }

        if(teaActivated != null){
            if(activated == 1){
                teaActivated.setChecked(true);
            }else
                teaActivated.setChecked(false);
            teaActivated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(activated == 1)
                        activated = 0;
                    else
                        activated = 1;
                    Tea t = getTea();
                    t.setActivated(activated);
                    db.updateTea(t);
                }
            });
        }
    }

    public Tea getTea(){
        return t;
    }

    public void setTVColor(TextView tv){
        if(type.equalsIgnoreCase("Black"))
            tv.setTextColor(Color.parseColor(context.getString(R.string.black_text)));
        if(type.equalsIgnoreCase("White"))
            tv.setTextColor(Color.parseColor(context.getString(R.string.white_text)));
        if(type.equalsIgnoreCase("Green"))
            tv.setTextColor(Color.parseColor(context.getString(R.string.green_text)));
        if(type.equalsIgnoreCase("Oolong"))
            tv.setTextColor(Color.parseColor(context.getString(R.string.oolong_text)));
        if(type.equalsIgnoreCase("Herbal"))
            tv.setTextColor(Color.parseColor(context.getString(R.string.herbal_text)));
        if(type.equalsIgnoreCase("Mate"))
            tv.setTextColor(Color.parseColor(context.getString(R.string.mate_text)));
        if(type.equalsIgnoreCase("Pu'erh"))
            tv.setTextColor(Color.parseColor(context.getString(R.string.puerh_text)));
        if(type.equalsIgnoreCase("Rooibos"))
            tv.setTextColor(Color.parseColor(context.getString(R.string.rooibos_text)));
    }

    public void setFont(TextView tv, Typeface typeface){
        tv.setTypeface(typeface);
    }

}
