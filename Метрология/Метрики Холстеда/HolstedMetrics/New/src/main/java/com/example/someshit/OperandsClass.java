package com.example.someshit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OperandsClass {
    private SimpleIntegerProperty i;
    private SimpleStringProperty oprnd;
    private SimpleIntegerProperty f2i;

    public OperandsClass(int i, String oprnd, int f2i) {
        this.i = new SimpleIntegerProperty(i);
        this.oprnd = new SimpleStringProperty(oprnd);
        this.f2i = new SimpleIntegerProperty(f2i);
    }

    public int getI() {return i.get();}
    public void setI(int i) {this.i.set(i);}

    public String getOprnd() {return oprnd.get();}
    public void setOprnd(String oprnd) {this.oprnd.set(oprnd);}

    public int getF2i() {return f2i.get();}
    public void setF2i(int f2i) {this.f2i.set(f2i);}
}