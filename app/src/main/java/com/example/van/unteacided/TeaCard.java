package com.example.van.unteacided;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
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


    public TeaCard(Context c, Tea t){
        this(c);
        this.name = t.getName();
        this.type = t.getType();
        this.tempF = t.getTempF();
        this.tempC = t.getTempC();
        this.steep = t.getSteepTime();
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
            if(type.equalsIgnoreCase("Black"))
                teaName.setTextColor(ColorStateList.valueOf(R.color.black_text));
        }

        if(teaType != null){
            teaType.setText(type);
        }

        if(teaTempF != null){
            teaTempF.setText(String.valueOf(tempF) + (char) 0x00B0 + "F" );
        }

        if(teaTempC != null){
            teaTempC.setText(String.valueOf(tempC) + (char) 0x00B0 + "C" );
        }

        if(teaSteep != null){
            float temp = ((float) steep)/60;
            teaSteep.setText(String.valueOf(temp) + " mins");
        }
    }
}
