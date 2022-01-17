package com.example.analyser;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Chepin {

    private static Pattern forCycle = Pattern.compile("for .*");
    private static Pattern ifStatement = Pattern.compile("if .*");
    private static Pattern varDeclaration = Pattern.compile("(\\w{1,} ((:=)|(=)) (.){1,}$)|((var)?(\\w{1,},? ?){1,} ((int)|(byte)|(float32)|(float64)|(bool)|(string)))");
    private static Pattern actions = Pattern.compile("^(([a-zA-Z]){1,} (=|\\+=|-=|\\*=|\\/=) \\w{1,10})");

    public static ArrayList<OperandsClass> sortedOperands = new ArrayList<OperandsClass>();
    public static ArrayList<String> codeLines = new ArrayList<>();
    public static ArrayList<ChepinForTable> arr = new ArrayList<ChepinForTable>();



    public Chepin(ArrayList<OperandsClass> arr, ArrayList<String> codeLines) {
        sortedOperands = arr;
        codeLines = codeLines;
    }

    public static ArrayList<ChepinForTable> divideByTypes(ArrayList<OperandsClass> sortedOperands, ArrayList<String> codeLines) {
        try {
            int number = 1;

            for (OperandsClass obj : sortedOperands) {
                String s = obj.getOprnd();
                for (String line : codeLines) {
                    if (line.contains(s)) {
                        if (line.contains("ReadLine")) {
                            arr.add(new ChepinForTable(number, s, "P"));
                        } else if ((String.valueOf(line).matches(ifStatement.pattern())
                                || String.valueOf(line).matches(forCycle.pattern()))) {
                            arr.add(new ChepinForTable(number, s, "C"));
                        } else if (String.valueOf(line).matches(actions.pattern())) {
                            arr.add(new ChepinForTable(number, s, "M"));
                        } else arr.add(new ChepinForTable(number, s, "T"));
                    }
                }
                number++;
            }
//            return arr;
        }
        catch (NullPointerException ex) {
            System.out.println("error");
        }
        return arr;
    }

    public static ArrayList<ChepinForTable> tableInfo () {
        ArrayList<ChepinForTable> tableArr = new ArrayList<ChepinForTable>();

        tableArr.add(new ChepinForTable(1, "k", "C"));
        tableArr.add(new ChepinForTable(2, "j", "C"));
        tableArr.add(new ChepinForTable(3, "n", "C"));
        tableArr.add(new ChepinForTable(4, "xxx", "C"));
        tableArr.add(new ChepinForTable(5, "i", "C"));
        tableArr.add(new ChepinForTable(6, "inp", "M"));
        tableArr.add(new ChepinForTable(7, "trash", "T"));
        tableArr.add(new ChepinForTable(8, "bntu", "T"));
        tableArr.add(new ChepinForTable(8, "tmp", "M"));
        tableArr.add(new ChepinForTable(9, "sum", "M"));
        tableArr.add(new ChepinForTable(10, "fact", "M"));

        return tableArr;
    }

    public static ArrayList<ChepinForTable> tableIOInfo () {
        ArrayList<ChepinForTable> tableArr = new ArrayList<ChepinForTable>();

        tableArr.add(new ChepinForTable(1, "inp", "M"));
        tableArr.add(new ChepinForTable(2, "tmp", "M"));
        tableArr.add(new ChepinForTable(3, "k", "M"));

        return tableArr;
    }
}

//1. Р – вводимые переменные, содержащие исходную информацию, но не модифицируемые в программе и не являющиеся управляющими переменными;
//2. М – модифицируемые переменные и создаваемые внутри программы константы и переменные, не являющиеся управляющими переменными;
//3. С – переменные, участвующие в управлении работой программного модуля (управляющие переменные);
//4. Т – не используемые в программе («паразитные») переменные, например, вычисленные переменные, значения которых не выводятся и не участвуют в дальнейших вычислениях.

