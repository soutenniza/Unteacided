package com.example.van.unteacided;

/**
 * Created by Van on 10/4/2014.
 */
public class Tea {
    private int id;
    private String name;
    private String type;
    private int tempF;
    private int tempC;
    private int steepTime;
    private int activated;

    public Tea() {
        activated = 1;
    }

    public void setId(int i){
        id = i;
    }

    public void setName(String n){
        name = n;
    }

    public void setType(String t){
        type = t;
    }

    public void setTempF(int f){
        tempF = f;
    }

    public void setTempC(int c){
        tempC= c;
    }

    public void setSteepTime(int t){
        steepTime = t;
    }

    public void setActivated(int a){
        activated = a;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public int getTempF(){
        return tempF;
    }

    public int getTempC(){
        return tempC;
    }

    public int getSteepTime(){
        return steepTime;
    }

    public boolean isActive(){
        if(activated == 1)
            return true;
        else
            return false;
    }
}
