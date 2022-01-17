package com.example.programm;

public class Node {
    public int data;
    public Node leftChild;
    public Node rightChild;
    public boolean lTag;
    public boolean rTag;

    public void displayNode () {
        System.out.println('{');
        System.out.println(data);
        System.out.println('}');
    }
}