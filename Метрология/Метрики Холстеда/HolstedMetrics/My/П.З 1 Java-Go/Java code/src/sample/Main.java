package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main extends Application {

    FileReader fr;
    static BufferedReader reader;

    ObservableList<OperatorsClass> operatorsObservableList; //To fill this list the method FXCollections.observableList() is used in controller class

    public Main() {
        try {
            File file = new File("E:\\Лабы\\Метрология\\Метрики Холстеда\\HolstedMetrics\\My\\П.З 1 Java-Go\\Go code\\GoCodeExample.txt");
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

    @Override
    public void start(Stage stage) throws Exception{

        TableView <OperatorsClass> operatorsTable = new TableView<OperatorsClass>();

        //Creating columns
        TableColumn jCollumn = new TableColumn("J");
        jCollumn.setCellValueFactory(new PropertyValueFactory<>("j"));
        TableColumn operatorsCollumn = new TableColumn("Операторы");
        operatorsCollumn.setCellValueFactory(new PropertyValueFactory<>("oprtr"));
        TableColumn F1JCollumn = new TableColumn("F1J");
        operatorsCollumn.setCellValueFactory(new PropertyValueFactory<>("f1j"));

        //Adding data to the table
        ObservableList <String> list = FXCollections.observableArrayList();
        operatorsTable.setItems(operatorsObservableList);
        operatorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        operatorsTable.getColumns().addAll(jCollumn, operatorsCollumn, F1JCollumn);

        //Setting the size of the table
        operatorsTable.setMaxSize(350, 200);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 50, 50, 60));
        vbox.getChildren().addAll(operatorsTable);
        //Setting the scene
        Scene scene = new Scene(vbox, 595, 230);
        stage.setTitle("Table View Exmple");
        stage.setScene(scene);
        stage.show();

//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Метрики Холстеда");
//        primaryStage.setScene(new Scene(root, 1200, 760));
//        primaryStage.show();

    }

    @Override
    public void init() {
        ArrayList<String> codeLines = new ArrayList<>();
        readCodeByStrings(codeLines);
        HolstedMetrics.findEverything(codeLines);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
