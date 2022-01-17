package com.example.analyser;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OperandsClass {
    private SimpleIntegerProperty number;
    private SimpleIntegerProperty i;
    private SimpleStringProperty oprnd;

    public OperandsClass(int number, int i, String oprnd) {
        this.number = new SimpleIntegerProperty(number);
        this.i = new SimpleIntegerProperty(i);
        this.oprnd = new SimpleStringProperty(oprnd);
    }

    public int getNumber() {return number.get();}
    public void setNumber(int number) {this.number.set(number);}

    public int getI() {return i.get();}
    public void setI(int i) {this.i.set(i);}

    public String getOprnd() {return oprnd.get();}
    public void setOprnd(String oprnd) {this.oprnd.set(oprnd);}
}
