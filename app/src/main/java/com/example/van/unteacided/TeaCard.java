package com.example.van.unteacided;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Van on 10/5/2014.
 */
public class TeaCard extends Card {
    protected TextView teaName;
    protected TextView teaType;
    protected String name;
    protected String type;

    public TeaCard(Context c, Tea t){
        this(c);
        this.name = t.getName();
        this.type = t.getType();
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

        if(teaName != null){
            teaName.setText(name);
        }

        if(teaType != null){
            teaType.setText(type);
        }
    }
}
