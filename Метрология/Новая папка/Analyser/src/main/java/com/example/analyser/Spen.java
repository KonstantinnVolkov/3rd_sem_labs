package com.example.analyser;

import java.util.*;
import java.util.regex.Pattern;

public class Spen {

    private static Pattern forCycle = Pattern.compile("for .*");
    private static Pattern ifStatement = Pattern.compile("if .*");
    private static Pattern varDeclaration = Pattern.compile("(\\w{1,} ((:=)|(=)) (.){1,}$)|((var)?(\\w{1,},? ?){1,} ((int)|(byte)|(float32)|(float64)|(bool)|(string)))");
    private static Pattern actions = Pattern.compile("^(([a-zA-Z]){1,} (=|\\+=|-=|\\*=|\\/=) \\w{1,10})");

    public static ArrayList<String> codeLines = new ArrayList<>();
    public static ArrayList<String> operands = new ArrayList<>();
    public static ArrayList<OperandsClass> sortedOperands = new ArrayList<OperandsClass>();

    public static int i;

    public Spen(ArrayList<String> arr) {
        codeLines = arr;
    }

    public static ArrayList<OperandsClass> getOperands(ArrayList<String> codeLines) {

        //парсинг кода
        for (String str: codeLines) {
            try {
                str = str.trim();
                if (str.isEmpty() || str.equals("{") || str.equals("}"))
                    continue;
                if (str.contains("ReadLine()")) {
                    operands.add(str.substring(0, str.indexOf(" ")));
                }
                else if (str.contains("fmt.Print")) {
                    String buf = str.substring(str.indexOf("(")+1, str.indexOf(")"));
                    if (buf.contains("%d")|| buf.length()>2) {
                        operands.add(buf.substring(buf.indexOf(", ")+1).trim());
                    }
                }
                else if (String.valueOf(str).matches(varDeclaration.pattern()) && !str.contains("for")) {
                    if (str.contains("ReadLine()"))
                        continue;
                    String[] buf = str.split(" ");
                    String notVariables = ":=+-/*%0123456789;<>()";
                    for (String s: buf) {
                        if (notVariables.contains(s)) {
                            continue;
                        }
                        else {
                            operands.add(s);
                        }
                    }
                }
                else if (String.valueOf(str).matches(actions.pattern())) {
                    String[] buf = str.split(" ");
                    String notVariables = ":+=-=*=/=0123456789;<>()";
                    for (String s: buf) {
                        if (notVariables.contains(s)) {
                            continue;
                        }
                        else {
                            operands.add(s);
                        }
                    }
                }
                else if (String.valueOf(str).matches(forCycle.pattern())) {
                    String operators = " -%/{;";
                    String curr = "";
                    StringTokenizer tokenizer = new StringTokenizer(str, operators, true);
                    while (tokenizer.hasMoreTokens()){
                        curr = tokenizer.nextToken();
                        if (curr.equals(" ")){
                            continue;
                        }else if(curr.equals("for")) {
                            continue;
                        }else if (curr.equals("+") || (curr.equals("+="))) {
                            continue;
                        }else if (curr.equals("-") || (curr.equals("-="))) {
                            continue;
                        }else if (curr.equals("if")) {
                            continue;
                        }else if (curr.equals("%")) {
                            continue;
                        }else if (curr.equals("==")  || curr.equals("=")) {
                            continue;
                        }else if (curr.equals("{")) {
                            continue;
                        }else if (curr.equals("<") || curr.equals("<=")) {
                            continue;
                        }else if (curr.equals(">") || curr.equals(">=")) {
                            continue;
                        }else if (curr.equals(":=")) {
                            continue;
                        }else if (curr.equals(";")) {
                            continue;
                        }else if ("0123456789".contains(curr)) {
                            continue;
                        }else if (curr.contains("++")) {
                            String temp = null;
                            temp = curr.substring(0,curr.indexOf("+"));
                            operands.add(temp);
                        }
                        else{
                            operands.add(curr);
                        }
                    }
                }
                else if (String.valueOf(str).matches(ifStatement.pattern())) {
                    String[] buf = str.split(" ");
                    String notVariables = ":+=-=*=/==||&&0123456789;<>(){";
                    for (String s : buf) {
                        if (notVariables.contains(s) || s.equals("if")) {
                            continue;
                        } else {
                            operands.add(s);
                        }
                    }
                }
            }
            catch (NullPointerException ex) {
                break;
            }
        }

        //подсчет кол-ва операторов
        Set<String> set = new HashSet<>(operands);
        int number = 0;
        for (String s: set) {
            if (s.equals("\" \"") || s.equals("14"))
                continue;

            i = 0;
            for (String s1: operands) {
                if (s.equals(s1)) {
                    i++;
                }
            }
            number++;
            sortedOperands.add(new OperandsClass(number,i - 1 , s));
        }
        return sortedOperands;
    }
}
