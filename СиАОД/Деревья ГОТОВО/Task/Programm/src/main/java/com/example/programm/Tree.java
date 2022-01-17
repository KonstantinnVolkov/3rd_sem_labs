package com.example.programm;

import javafx.scene.control.Label;

import java.util.Stack;

public class Tree {
    public Node root;

    public Tree() {root = null;}

    public void insert (int id) {
        Node newNode = new Node();
        newNode.data = id;
        if (root == null) {
            root = newNode;
        }
        else {
            Node current = root;
            Node parent;
            while (true) {
                parent = current;
                if (id < current.data) {
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = newNode;
                        return;
                    }
                }
                else {
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }

    public boolean delete(int key) {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while (current.data != key) {  //Поиск узла
            parent = current;
            if (key < current.data) { //двигаться налево?
                isLeftChild = true;
                current = current.leftChild;
            }
            else {                      //или направо
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) {      //конец цепочки
                return false;           //узел не найден
            }
        }
        //удаляемый узел найден

        //если узел не имеет потомков, то он просто удаляется
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root) //усли узел является корневым
                root = null;   //дерево очищается
            else if (isLeftChild)
                parent.leftChild = null; //узел отсоединяется
            else                         //от родителя
                parent.rightChild = null;
        }

        //Если нет правого потомка, узел заменяется левым поддеревом
        else if (current.rightChild == null){
            if (current == root)
                root = current.leftChild;
            else if (isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;
        }

        //если нет левого потомка, узел заменяется правым поддеревом
        else if (current.leftChild == null) {
            if (current == root)
                root = current.rightChild;
            else if (isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;
        }

        //два потомка, узел заменяется преемником
        else {
            //поиск преемника для удаляемого узла (current)
            Node successor = getSuccessor(current);

            //родитель current связывается с посредником
            if (current == root)
                root = successor;
            else if (isLeftChild)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;
        }
        //преемник связывается с левым потомком current
        return true;   //признак успешного завершения
    }

    private Node getSuccessor (Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;

    }

    public void preOrder (Node localRoot, Label label) {
        if (localRoot != null) {
            System.out.print(localRoot.data + " ");
            label.setText(label.getText() + String.valueOf(localRoot.data) + " ");
            preOrder(localRoot.leftChild, label);
            preOrder(localRoot.rightChild, label);
        }
    }

    public void postOrder (Node localRoot, Label label) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild, label);
            postOrder(localRoot.rightChild, label);
            System.out.print(localRoot.data + " ");
            label.setText(label.getText() + String.valueOf(localRoot.data) + " ");
        }
    }

    public void inOrder (Node localRoot, Label label) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild, label);
            System.out.print(localRoot.data + " ");
            label.setText(label.getText() + String.valueOf(localRoot.data) + " ");
            inOrder(localRoot.rightChild, label);
        }
    }

    public void displayTree () {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println("--------------------------------------------------");

        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int j = 0; j < nBlanks; j++) {
                System.out.print(' ');
            }
            while (globalStack.isEmpty() == false){
                Node temp = (Node)globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.data);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);
                    if (temp.leftChild != null || temp.rightChild != null) {
                        isRowEmpty = false;
                    }
                }
                else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks*2-2; j++) {
                    System.out.print(' ');
                }
            }
            System.out.println();
            nBlanks /= 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            }
        }
        System.out.println("--------------------------------------------------");
    }

    private String sim (Node el) {
       String result;
       if (el == null) {
           return  result = "0";
       }
       result = String.valueOf(el.data) + " ";
       result += sim(el.leftChild);
       result += "(" + String.valueOf(el.data) + ") ";

       if ()


    }

    public String simAndSew (Node el) {
        Node prev = el.leftChild;
        return sim(el.leftChild);

    }


}

