package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OperatorsClass {
    private SimpleIntegerProperty j;
    private SimpleStringProperty oprtr;
    private SimpleIntegerProperty f1j;

    public OperatorsClass(int j, String oprtr, int f1j) {
        this.j = new SimpleIntegerProperty(j);
        this.oprtr = new SimpleStringProperty(oprtr);
        this.f1j = new  SimpleIntegerProperty(f1j);
    }

    public SimpleIntegerProperty getJ() {return j;}
    public SimpleIntegerProperty jProperty() {return j;}
    public void setJ(SimpleIntegerProperty j) {this.j = j;}

    public SimpleStringProperty getOprtr() {return oprtr;}
    public SimpleStringProperty oprtrProperty() {return oprtr;}
    public void setOprtr(SimpleStringProperty oprtr) {this.oprtr = oprtr;}

    public SimpleIntegerProperty getF1j() {return f1j;}
    public SimpleIntegerProperty f1jProperty() {return f1j;}
    public void setF1j(SimpleIntegerProperty f1j) {this.f1j = f1j;}
}