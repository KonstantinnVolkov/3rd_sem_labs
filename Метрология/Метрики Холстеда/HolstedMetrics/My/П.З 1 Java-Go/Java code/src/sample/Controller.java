package sample;

import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ResourceBundle;

public class Controller {

    ObservableList<OperatorsClass> oprs = FXCollections.observableArrayList();
    ObservableList<OperandsClass> opds = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<OperatorsClass> operatorsTable;

    @FXML
    private TableColumn<OperatorsClass, Integer> jCollumn;

    @FXML
    private TableColumn<OperatorsClass, String> operatorCollumn;

    @FXML
    private TableColumn<OperatorsClass, Integer> F1jCollumn;

    @FXML
    private TableView<OperandsClass> operandsTable;

    @FXML
    private TableColumn<OperandsClass, Integer> iCollumn;

    @FXML
    private TableColumn<OperandsClass, String> operandCollumn;

    @FXML
    private TableColumn<OperandsClass, Integer> F2iCollumn;

    @FXML
    void initialize() {
        initData();

        jCollumn.setCellValueFactory(new PropertyValueFactory<OperatorsClass, Integer>("jCollumn"));
        //jCollumn.setCellValueFactory(data -> data.getValue().jProperty());
        operatorCollumn.setCellValueFactory(new PropertyValueFactory<OperatorsClass, String>("operatorCollumn"));
        F1jCollumn.setCellValueFactory(new PropertyValueFactory<OperatorsClass, Integer>("F1jCollumn"));

        iCollumn.setCellValueFactory(new PropertyValueFactory<OperandsClass, Integer>("iCollumn"));
        operandCollumn.setCellValueFactory(new PropertyValueFactory<OperandsClass, String>("operandCollumn"));
        F2iCollumn.setCellValueFactory(new PropertyValueFactory<OperandsClass, Integer>("F2iCollumn"));

        System.out.println(opds);
        System.out.println(oprs);

        operatorsTable.setItems(oprs);
        operandsTable.setItems(opds);

    }

    private void initData() {
        int i = 0;
        int j = 1;
        Set<String> set = new HashSet<>(HolstedMetrics.operators);
        System.out.println(set);
        System.out.println(HolstedMetrics.operators);
        for (String s : set) {
            i = 0;
            for (String s1 : HolstedMetrics.operators) {
                if (s.equals(s1)) {
                    i++;
                }
            }
            oprs.add(new OperatorsClass(j, s, i));
            j++;
        }
        System.out.println(oprs);

        i = 0;
        j = 0;
        Set<String> set1 = new HashSet<>(HolstedMetrics.operands);
        System.out.println(set1);
        System.out.println(HolstedMetrics.operands);
        for (String s : set1) {
            i = 0;
            for (String s1 : HolstedMetrics.operators) {
                if (s.equals(s1)) {
                    i++;
                }
            }
            opds.add(new OperandsClass(j, s, i));
            j++;
        }
        System.out.println(opds);
    }
}


