package sample;

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

    public SimpleIntegerProperty getI() {return i;}
    public SimpleIntegerProperty iProperty() {return i;}
    public void setI(SimpleIntegerProperty i) {this.i = i;}

    public SimpleStringProperty getOprnd() {return oprnd;}
    public SimpleStringProperty oprndProperty() {return oprnd;}
    public void setOprnd(SimpleStringProperty oprnd) {this.oprnd = oprnd;}

    public SimpleIntegerProperty getF2i() {return f2i;}
    public SimpleIntegerProperty f2iProperty() {return f2i;}
    public void setF2i(SimpleIntegerProperty f2i) {this.f2i = f2i;}
}
