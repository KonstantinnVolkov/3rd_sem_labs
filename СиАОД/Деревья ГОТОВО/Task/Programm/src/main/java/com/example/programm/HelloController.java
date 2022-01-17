package com.example.programm;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class HelloController {

    Tree tree;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nodesAmountField;

    @FXML
    private Button buildTreeBtn;

    @FXML
    public Label preorderLabel;

    @FXML
    private Label inorderLabel;

    @FXML
    private Label postorderLabel;

    @FXML
    private Button sewTreeBtn;

    @FXML
    private Pane treePane;

    @FXML
    private TextField nodeToDeleteField;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {
        buildTreeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Random random = new Random();
                tree = new Tree();
                int value;
                for (int i = 0; i < Integer.parseInt(nodesAmountField.getText()); i++) {
                    value = random.nextInt()%99;
                    if (value < 0)
                        value *= -1;
                    System.out.print(value + " ");
                    tree.insert(value);
                }
                treePane.getChildren().clear();
                drawTree(treePane.getWidth()/2, 10, treePane.getWidth()/2, 10, tree.root);

                preorderLabel.setText("");
                postorderLabel.setText("");
                inorderLabel.setText("");

                tree.preOrder(tree.root, preorderLabel);
                tree.postOrder(tree.root, postorderLabel);
                tree.inOrder(tree.root, inorderLabel);

            }
        });

        deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int key = Integer.parseInt(nodeToDeleteField.getText());
                nodeToDeleteField.setText("");
                tree.delete(key);

                treePane.getChildren().clear();
                drawTree(treePane.getWidth()/2, 10, treePane.getWidth()/2, 10, tree.root);

                preorderLabel.setText("");
                postorderLabel.setText("");
                inorderLabel.setText("");

                tree.preOrder(tree.root, preorderLabel);
                tree.postOrder(tree.root, postorderLabel);
                tree.inOrder(tree.root, inorderLabel);
            }
        });
    }

    public void drawTree(double x1, int y1, double x, int y, Node node) {
        Line line = new Line(x1, y1+15, x, y);
        treePane.getChildren().add(line);
        Circle circle = new Circle(x,y, 15, Paint.valueOf("green"));
        treePane.getChildren().add(circle);
        Text txt = new Text(x-3,y+3, String.valueOf(node.data));
        treePane.getChildren().add(txt);
        if(node.leftChild != null)
            drawTree(x,y,x-50,y+75,node.leftChild);// drawTree(x,y,x-(50),y+75,node.leftChild);
        if(node.rightChild != null)
            drawTree(x,y,x+50,y+75,node.rightChild); //drawTree(x,y,x+(50),y+75,node.rightChild);
    }
}
