package com.example.analyser;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ChepinForTable {
    private SimpleIntegerProperty number;
    private SimpleStringProperty operand;
    private SimpleStringProperty type;

    public ChepinForTable(int number, String operand, String type) {
        this.number = new SimpleIntegerProperty(number);
        this.operand = new SimpleStringProperty(operand);
        this.type = new SimpleStringProperty(type);
    }

    public int getNumber() {return number.get();}
    public void setNumber(int number) {this.number.set(number);}

    public String getOperand() {
        return operand.get();
    }
    public void setOperand(String operand) {
        this.operand.set(operand);
    }

    public String getType() {
        return type.get();
    }
    public void setType(String type) {
        this.type.set(type);
    }

}


