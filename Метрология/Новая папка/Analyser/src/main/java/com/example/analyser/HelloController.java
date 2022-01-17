package com.example.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

// fx:controller="com.example.analyser.HelloController"
public class HelloController {

    public static FileChooser fileChooser = new FileChooser();
    public static ArrayList<String> codeLines = new ArrayList<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField sumSpenField;

    @FXML
    private TextField filePathField;

    @FXML
    private Button fileChooserBtn;

    @FXML
    void fileChooserAction(ActionEvent event) {     //In this method file with code is being opened and outputed to codeTextField
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File Dialog");
        Stage stage = (Stage) anchorPane.getScene().getWindow();      //Чел на индусе сказал так делать, чтобы получить stage из HelloApplication

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter
                ("TEXT files (*.txt)", "*.txt");                        //File filter

        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {                            // If file was choosed, if will init this file in HelloApplication class
            HelloApplication.setCodeFile(file);
            filePathField.setText(file.getPath());
        }
        else return;

        FileReader fr;                                         //here file being read and outputed to the codeTextField
        BufferedReader reader;
        codeTextField.clear();
        try {
            fr = new FileReader(file);
            reader = new BufferedReader(fr);
            System.out.println("FIle was found!\nSuccess!");

            String line = "";
            do {
                line = reader.readLine();
                codeTextField.appendText(line + "\n");
                codeLines.add(line);
            } while (line != null);

        }
        catch (IOException ex) {
            System.out.println("Ошибка чтения файла!");
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private TextArea codeTextField;

    @FXML
    private TableView<OperandsClass> SpenTable;

    @FXML
    private TableView<ChepinForTable> ChepinTable;

    @FXML
    private TableView<ChepinForTable> ChapinIOTable;

    @FXML
    private Button countMetricsBtn;

    @FXML
    void countMetricsAction(ActionEvent event) {
        //Спен
        Spen spen = new Spen(codeLines);
        final ObservableList<OperandsClass> operandsData = FXCollections.observableArrayList(Spen.getOperands(codeLines));

        TableColumn numbersColl = new TableColumn("№");
        numbersColl.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn operandsColl = new TableColumn("Идентификатор");
        operandsColl.setCellValueFactory(new PropertyValueFactory<>("oprnd"));
        TableColumn spenColl = new TableColumn("Спен идентификатора");
        spenColl.setCellValueFactory(new PropertyValueFactory<>("i"));

        SpenTable.setItems(operandsData);
        SpenTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        SpenTable.getColumns().addAll(numbersColl, operandsColl, spenColl);

        int sumSpen = 0;
        for (OperandsClass obj: operandsData)
            sumSpen += obj.getI();
        sumSpenField.setText(String.valueOf(sumSpen));

        //Полный Чепин
        ArrayList<ChepinForTable> tableArr = Chepin.tableInfo();
        final ObservableList<ChepinForTable> chepinTableInfo = FXCollections.observableArrayList(tableArr);//Chepin.tableInfo()

        TableColumn chepinNumbersColl = new TableColumn("№");
        chepinNumbersColl.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn chepinOperandsColl = new TableColumn("Идентификатор");
        chepinOperandsColl.setCellValueFactory(new PropertyValueFactory<>("operand"));
        TableColumn type = new TableColumn("Тип");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        ChepinTable.setItems(chepinTableInfo);
        ChepinTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ChepinTable.getColumns().addAll(chepinNumbersColl, chepinOperandsColl, type);

        int C = 0;
        int P = 0;
        int T = 0;
        int M = 0;

        for (ChepinForTable obj: tableArr) {
            if (obj.getType().equals("C"))
                C++;
            else if (obj.getType().equals("P"))
                P++;
            else if (obj.getType().equals("T"))
                T++;
            else if (obj.getType().equals("M"))
                M++;
        }
        FullChepin.setText(String.valueOf(P + 2*M + 3*C + 0.5*T));

        //Чепин I/O
        ArrayList<ChepinForTable> tableIOArr = Chepin.tableIOInfo();
        final ObservableList<ChepinForTable> chepinIOTableInfo = FXCollections.observableArrayList(tableIOArr);

        TableColumn chepinIONumbersColl = new TableColumn("№");
        chepinIONumbersColl.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn chepinIO_OperandsColl = new TableColumn("Идентификатор");
        chepinIO_OperandsColl.setCellValueFactory(new PropertyValueFactory<>("operand"));
        TableColumn IOtype = new TableColumn("Тип");
        IOtype.setCellValueFactory(new PropertyValueFactory<>("type"));

        ChapinIOTable.setItems(chepinIOTableInfo);
        ChapinIOTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ChapinIOTable.getColumns().addAll(chepinIONumbersColl, chepinIO_OperandsColl, IOtype);

        int CIO = 0;
        int PIO = 0;
        int TIO = 0;
        int MIO = 0;
        for (ChepinForTable obj: tableIOArr) {
            if (obj.getType().equals("C"))
                CIO++;
            else if (obj.getType().equals("P"))
                PIO++;
            else if (obj.getType().equals("T"))
                TIO++;
            else if (obj.getType().equals("M"))
                MIO++;
        }
        ChepinIO.setText(String.valueOf(PIO + 2*MIO + 3*CIO + 0.5*TIO));

    }

    @FXML
    private TextField FullChepin;

    @FXML
    private TextField ChepinIO;

    @FXML
    void initialize() {

    }
}
