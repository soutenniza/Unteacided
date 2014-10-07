package com.example.van.unteacided;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Van on 10/5/2014.
 */
public class TeaCard extends Card {
    protected TextView teaName;
    protected TextView teaType;
    protected TextView teaTempF;
    protected TextView teaTempC;
    protected TextView teaSteep;
    protected RelativeLayout relativeLayout;
    protected String name;
    protected String type;
    protected int tempF;
    protected int tempC;
    protected int steep;
    protected Context context;
    protected Typeface bold;
    protected Typeface normal;


    public TeaCard(Context c, Tea t, Typeface b, Typeface n){
        this(c);
        context = c;
        this.name = t.getName();
        this.type = t.getType();
        this.tempF = t.getTempF();
        this.tempC = t.getTempC();
        this.steep = t.getSteepTime();
        this.bold = b;
        this.normal = n;
    }

    public TeaCard(Context c){
        this(c, R.layout.teacard);
    }

    public TeaCard(Context c, int innerLayout){
        super(c, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view){
        teaName = (TextView) parent.findViewById(R.id.teaCardName);
        teaType = (TextView) parent.findViewById(R.id.teaCardType);
        teaTempF = (TextView) parent.findViewById(R.id.teaCardTempF);
        teaTempC = (TextView) parent.findViewById(R.id.teaCardTempC);
        teaSteep = (TextView) parent.findViewById(R.id.teaCardSteep);

        relativeLayout = (RelativeLayout) parent.findViewById(R.id.cardBackground);

        if(teaName != null){
            teaName.setText(name);
            setTVColor(teaName);
            setFont(teaName, bold);
        }

        if(teaType != null){
            teaType.setText(type);
            setTVColor(teaType);
            setFont(teaType, normal);
        }

        if(teaTempF != null){
            teaTempF.setText(String.valueOf(tempF) + (char) 0x00B0 + "F" );
            setTVColor(teaTempF);
            setFont(teaTempF, normal);
        }

        if(teaTempC != null){
            teaTempC.setText(String.valueOf(tempC) + (char) 0x00B0 + "C" );
            setTVColor(teaTempC);
            setFont(teaTempC, normal);
        }

        if(teaSteep != null){
            float temp = ((float) steep)/60;
            teaSteep.setText(String.valueOf(temp) + " mins");
            setTVColor(teaSteep);
            setFont(teaSteep, normal);
        }
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
