package com.example.someshit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OperatorsClass {
    SimpleIntegerProperty j;
    SimpleStringProperty oprtr;
    SimpleIntegerProperty f1j;

    public OperatorsClass(int j, String oprtr, int f1j) {
        this.j = new SimpleIntegerProperty(j);
        this.oprtr = new SimpleStringProperty(oprtr);
        this.f1j = new  SimpleIntegerProperty(f1j);
    }

    public int getJ() {return j.get();}
    public void setJ(int j) {this.j.set(j);}

    public String getOprtr() {return oprtr.get();}
    public void setOprtr(String oprtr) {this.oprtr.set(oprtr);}

    public int getF1j() {return f1j.get();}
    public void setF1j(int f1j) {this.f1j.set(f1j);}
}
