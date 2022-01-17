package com.example.someshit;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    FileReader fr;
    static BufferedReader reader;
    Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);

    public HelloApplication() {
        try {
            File file = new File("G:\\Лабы\\Метрология\\Метрики Холстеда\\HolstedMetrics\\New\\GoCode\\GoCode.txt");
            fr = new FileReader(file);
            reader = new BufferedReader(fr);
            System.out.println("FIle was found!\nSuccess!");
        }
        catch (IOException ex){
            System.out.println("Ошибка чтения файла!");
            System.out.println(ex.getMessage());
        }
    }

    public static void readCodeByStrings(List<String> codeList){
        try {
            String line;
            do {
                line = reader.readLine();
                codeList.add(line);
            } while(line != null);

        }
        catch (IOException ex){
            System.out.println("Ошибка чтения из файла в методе readCodeByStrings");
        }
    }

    public void start(Stage stage) {
        //Creating a table of operators
        TableView<OperatorsClass> operatorsTable = new TableView<OperatorsClass>();
        final ObservableList<OperatorsClass> operatorsData = FXCollections.observableArrayList(HolstedMetrics.countOperators());
        //Creating columns
        TableColumn jCol = new TableColumn("J");
        jCol.setCellValueFactory(new PropertyValueFactory<>("j"));
        TableColumn operatorsCol = new TableColumn("Operators");
        operatorsCol.setCellValueFactory(new PropertyValueFactory("oprtr"));
        TableColumn f1jCol = new TableColumn("F1J");
        f1jCol.setCellValueFactory(new PropertyValueFactory("f1j"));
        //Adding data to the table
        operatorsTable.setItems(operatorsData);
        operatorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        operatorsTable.getColumns().addAll(jCol,operatorsCol,f1jCol);
        //Setting the size of the table
        operatorsTable.setMaxSize(250, 700);

        //Creating a table of operands
        TableView<OperandsClass> operandsTable = new TableView<OperandsClass>();
        final ObservableList<OperandsClass> operandsData = FXCollections.observableArrayList(HolstedMetrics.countOperands());
        //Creating collumns
        TableColumn iCol = new TableColumn("I");
        iCol.setCellValueFactory(new PropertyValueFactory<>("i"));
        TableColumn operandsCol = new TableColumn("Operands");
        operandsCol.setCellValueFactory(new PropertyValueFactory("oprnd"));
        TableColumn f2iCol = new TableColumn("F2I");
        f2iCol.setCellValueFactory(new PropertyValueFactory("f2i"));
        //Adding data to the table
        operandsTable.setItems(operandsData);
        operandsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        operandsTable.getColumns().addAll(iCol, operandsCol, f2iCol);
        //Setting the size of the table
        operandsTable.setMaxSize(250, 700);

        //Labels with Holsted's metrics
        HBox hbox = new HBox();
        hbox.setSpacing(15);
        hbox.setPadding(new Insets(10, 50, 50, 60));
        hbox.getChildren().addAll(operatorsTable, operandsTable);

        Label label1 = new Label("Словарь программы: " + HolstedMetrics.countProgrammVocabulary());
        label1.setFont(font);

        Label label2 = new Label("Длина программы: " + HolstedMetrics.countProgrammLength());
        label2.setFont(font);

        Label label3 = new Label("Объем программы: " + HolstedMetrics.countProgrammVolume());
        label3.setFont(font);

        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(10, 50, 50, 60));
        vBox.getChildren().addAll(label1,label2, label3);

        HBox hbox1 = new HBox();
        hbox1.setSpacing(50);
        hbox1.setPadding(new Insets(10, 50, 50, 60));
        hbox1.getChildren().addAll(hbox, vBox);

        //Setting the scene
        Scene scene = new Scene(hbox1, 1200, 1000);
        stage.setTitle("Holsded's metrics");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() {
        ArrayList<String> codeLines = new ArrayList<>();
        readCodeByStrings(codeLines);
        HolstedMetrics.findEverything(codeLines);
    }

    public static void main(String args[]){
        launch(args);
    }
}