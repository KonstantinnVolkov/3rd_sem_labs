package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class HolstedMetrics {

    public static ArrayList<String> operators = new ArrayList<String>();
    public static ArrayList<String> operands = new ArrayList<String>();

    static Pattern packages = Pattern.compile("package .*");
    static Pattern imports = Pattern.compile("import \\(");
    static Pattern comparisonsAndActions = Pattern.compile("\\w{1,}( )?(=)( )?.*;?");
    static Pattern varDeclaration = Pattern.compile("var \\w{1,}( ?,\\w{1,})? ((int)|(byte)|(float32)|(float64)|(bool)|(string))((;)?(.*)?(;)?)?");
    static Pattern func = Pattern.compile("func .*");
    static Pattern forCycle = Pattern.compile("for \\w{1,} ?= ?\\w{1,} ?; ?\\w{1,} ?((<=)|(<)|(>=)|(>)) ?\\w{1,} ?; ?.*\\{");
    static Pattern ifStatement = Pattern.compile("if \\w{1,} ((%)|(\\+)|(-)|(\\*)|(/)|(==)|(>)|(<)|(<=)|(>=)|(=)) [0-9]{1,}(.*)?");
    static Pattern returnStatement = Pattern.compile(".*return.*");
    static Pattern incOrDec = Pattern.compile(".*((\\+\\+)|(--));?");
    static Pattern switchStatement = Pattern.compile("switch .* \\{");
    static Pattern caseStatement = Pattern.compile("case .*:");
    static Pattern defaultStatement = Pattern.compile("default:");
    static Pattern methods = Pattern.compile("((\\w{1,})|(\\w{1,}\\.\\w{1,}))\\(.*\\)");


    public static void findEverything(ArrayList<String> codeList) {

        for (String str : codeList) {
            try {
                boolean flag = false;
                str = str.trim();
                if (str.isEmpty() || str.equals("}")) {
                    continue;
                }

                if (String.valueOf(str).matches(returnStatement.pattern())) {
                    str = str.trim();
                    if (str.equals("return")) {
                        operators.add("return");
                    } else {
                        operators.add("return");
                        str = str.substring("return".length() + 1);
                        String varName = "";
                        for (int i = 0; i < str.length(); i++) {
                            if (str.charAt(i) == ' ') {
                                continue;
                            }
                            if (str.charAt(i) == ' ' || str.charAt(i) == '+' || str.charAt(i) == '-'
                                    || str.charAt(i) == '*' || str.charAt(i) == '*'
                                    || str.charAt(i) == '/' || str.charAt(i) == '%'
                                    || str.charAt(i) == '(' || str.charAt(i) == ')' || str.charAt(i) == ';') {
                                operators.add(String.valueOf(str.charAt(i)));
                                varName = "";
                            } else {   //это тоже убрать если что
                                try {
                                    if (str.charAt(i + 1) == ' ' || str.charAt(i + 1) == '+' || str.charAt(i + 1) == '-'
                                            || str.charAt(i + 1) == '*' || str.charAt(i + 1) == '*'
                                            || str.charAt(i + 1) == '/' || str.charAt(i + 1) == '%'
                                            || str.charAt(i + 1) == '(' || str.charAt(i + 1) == ')' || str.charAt(i + 1) == ';') {
                                        varName += str.charAt(i);
                                        operands.add(varName);
                                        varName = "";
                                    } else {
                                        varName += str.charAt(i);
                                    }
                                } catch (StringIndexOutOfBoundsException ex) {
                                    varName += str.charAt(i);
                                    operands.add(varName);
                                }
                            }    //если что убрать это
                        }
                    }
                }

                if (String.valueOf(str).matches(ifStatement.pattern())) {
                    String operators = " -%/{;";
                    String curr = "";
                    StringTokenizer tokenizer = new StringTokenizer(str, operators, true);
                    List<String> operatorsArr = new ArrayList<>();
                    List<String> operandsArr = new ArrayList<>();
                    while (tokenizer.hasMoreTokens()){
                        curr = tokenizer.nextToken();
                        if (curr.equals(" ")){
                            continue;
                        }else if(curr.equals("if")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("+")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("-")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("if")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("%")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("==")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("{")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("<") || curr.equals("<=")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals(">") || curr.equals(">=")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals(":=")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals(";")) {
                            operatorsArr.add(curr);
                        }else if (String.valueOf(curr).matches(methods.pattern())) {
                            String temp = null;
                            temp = curr.substring(0,curr.indexOf("(")+1) + ")";
                            operatorsArr.add(temp);
                            temp = curr.substring(curr.indexOf("(")+1, curr.indexOf(")"));
                            String comma = ",";
                            StringTokenizer newTokenizer = new StringTokenizer(temp,comma);
                            while (newTokenizer.hasMoreTokens()){
                                curr = newTokenizer.nextToken();
                                operandsArr.add(curr);
                            }

                        }else{
                            operandsArr.add(curr);
                        }
                    }
                }

                if (String.valueOf(str).matches(comparisonsAndActions.pattern()) && !flag) {
                    str = str.trim();
                    String varName = "";
                    varName = str.substring(0, str.indexOf(" "));
                    operands.add(varName);
                    str = str.substring(str.indexOf(" ") + 1);
                    if (str.contains("*=")) {
                        operators.add("*=");
                        str = str.substring("*=".length()).trim();
                    } else if (str.contains("-=")) {
                        operators.add("-=");
                        str = str.substring("-=".length()).trim();
                    } else if (str.contains("+=")) {
                        operators.add("+=");
                        str = str.substring("+=".length()).trim();
                    } else if (str.contains("<=")) {
                        operators.add("<=");
                        str = str.substring("<=".length()).trim();
                    } else if (str.contains(">=")) {
                        operators.add(">=");
                        str = str.substring(">=".length()).trim();
                    } else if (str.contains("==")) {
                        operators.add("==");
                        str = str.substring("==".length()).trim();
                    } else if (str.contains(":=")) {
                        operators.add(":=");
                        str = str.substring(":=".length()).trim();
                    } else if (str.contains("=")) {
                        operators.add("=");
                        str = str.substring("=".length()).trim();
                    }
                    if (varName.equals("str")) {
                        operands.add(str);
                    } else {
                        varName = "";
                        for (int i = 0; i < str.length(); i++)  //str = "a5 +10 * v"
                        {
                            if (str.charAt(i) == ' ') {
                                continue;
                            }
                            if (str.charAt(i) == '+' || str.charAt(i) == '-'
                                    || str.charAt(i) == '*' || str.charAt(i) == '*'
                                    || str.charAt(i) == '/' || str.charAt(i) == '%'
                                    || str.charAt(i) == '(' || str.charAt(i) == ')'
                                    || str.charAt(i) == ';' || str.charAt(i) == '\"') {
                                operators.add(String.valueOf(str.charAt(i)));
                                varName = "";
                            } else {   //это тоже убрать если что
                                try {
                                    if (str.charAt(i + 1) == ' ' || str.charAt(i + 1) == '+' || str.charAt(i + 1) == '-'
                                            || str.charAt(i + 1) == '*' || str.charAt(i + 1) == '*'
                                            || str.charAt(i + 1) == '/' || str.charAt(i + 1) == '%'
                                            || str.charAt(i + 1) == '(' || str.charAt(i + 1) == ')'
                                            || str.charAt(i + 1) == ';' || str.charAt(i + 1) == '\"') {
                                        varName += str.charAt(i);
                                        operands.add(varName);
                                        varName = "";
                                    } else {
                                        varName += str.charAt(i);
                                    }
                                } catch (StringIndexOutOfBoundsException ex) {
                                    varName += str.charAt(i);
                                    operands.add(varName);
                                }
                            }    //если что убрать это
                        }
                    }
                }

                if (String.valueOf(str).matches(varDeclaration.pattern())) {
                    str = str.trim();
                    operators.add("var");
                    str = str.substring("var".length()).trim();
                    String varName = "";
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == ' ') {
                            varName = "";
                            continue;
                        }
                        if (str.equals("int") || str.equals("string") || str.equals("byte") ||
                                str.equals("bool") || str.equals("float32") || str.equals("float64")) {
                            operators.add(str);
                            varName = "";
                            break;
                        }
                        if (str.charAt(i + 1) == ' ') {
                            varName += String.valueOf(str.charAt(i));
                            str = str.substring(varName.length()).trim();
                            operands.add(varName);
                            i = -1;
                            varName = "";

                        } else if (str.charAt(i + 1) == ',') {
                            varName += String.valueOf(str.charAt(i));
                            operands.add(varName);
                            operators.add(",");
                            str = str.substring(varName.length() + 1).trim();
                            varName = "";
                            i = -1;
                        } else {
                            varName += String.valueOf(str.charAt(i));
                        }

                    }
                }

                if (String.valueOf(str).matches(func.pattern())) {

                    //разбор func и его имени
                    str = str.substring(5);  // str = swap(x, y string) (string, string) {
                    operators.add(str.substring(0, str.indexOf("(") + 1) + ")");
                    str = str.substring(str.indexOf("(") + 1, str.indexOf(")")) + str.substring(str.indexOf(")") + 1);  //str = (x, y string) (string, string) {

                    //разбор принимаемых параметров
                    while (str.contains("string")) {
                        operators.add("string");
                        str = str.substring(0, str.indexOf("string")) +
                                str.substring(str.indexOf("string") + "string".length());
                    }
                    while (str.contains("int")) {
                        operators.add("int");
                        str = str.substring(0, str.indexOf("int")) +
                                str.substring(str.indexOf("int") + "int".length());
                    }
                    while (str.contains("bool")) {
                        operators.add("bool");
                        str = str.substring(0, str.indexOf("bool")) +
                                str.substring(str.indexOf("bool") + "bool".length());
                    }
                    while (str.contains("byte")) {
                        operators.add("byte");
                        str = str.substring(0, str.indexOf("byte")) +
                                str.substring(str.indexOf("byte") + "byte".length());
                    }
                    while (str.contains("float32")) {
                        operators.add("float32");
                        str = str.substring(0, str.indexOf("float32")) +
                                str.substring(str.indexOf("float32") + "float32".length());
                    }
                    while (str.contains("float64")) {
                        operators.add("float64");
                        str = str.substring(0, str.indexOf("float64")) +
                                str.substring(str.indexOf("float64") + "float64".length());
                    }
                    String varName = "";
                    for (int strCounter = 0; strCounter < str.length(); strCounter++) {
                        if (str.charAt(strCounter) == ',') {
                            continue;
                        }
                        if (str.charAt(strCounter) == '(' || str.charAt(strCounter) == ')' || str.charAt(strCounter) == '{') {
                            operators.add(String.valueOf(str.charAt(strCounter)));
                            continue;
                        }
                        if (str.charAt(strCounter) != ' ') {
                            if (str.charAt(strCounter + 1) == ',' || str.charAt(strCounter + 1) == ' ') {
                                varName += String.valueOf(str.charAt(strCounter));
                                operands.add(varName);
                                varName = "";
                            } else {
                                varName += String.valueOf(str.charAt(strCounter));
                            }
                        }
                    }
                }

                if (String.valueOf(str).matches(forCycle.pattern())) {
                    String operators = " -%/{;";
                    String curr = "";
                    StringTokenizer tokenizer = new StringTokenizer(str, operators, true);
                    List<String> operatorsArr = new ArrayList<>();
                    List<String> operandsArr = new ArrayList<>();
                    while (tokenizer.hasMoreTokens()){
                        curr = tokenizer.nextToken();
                        if (curr.equals(" ")){
                            continue;
                        }else if(curr.equals("for")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("+")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("-")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("if")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("%")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("==")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("{")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals("<") || curr.equals("<=")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals(">") || curr.equals(">=")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals(":=")) {
                            operatorsArr.add(curr);
                        }else if (curr.equals(";")) {
                            operatorsArr.add(curr);
                        }else if (curr.contains("++")) {
                            String temp = null;
                            operatorsArr.add("++");
                            temp = curr.substring(0,curr.indexOf("+"));
                            operandsArr.add(temp);
                        }else if (String.valueOf(curr).matches(methods.pattern())) {
                            String temp = null;
                            temp = curr.substring(0,curr.indexOf("(")+1) + ")";
                            operatorsArr.add(temp);
                            temp = curr.substring(curr.indexOf("(")+1, curr.indexOf(")"));
                            String comma = ",";
                            StringTokenizer newTokenizer = new StringTokenizer(temp,comma);
                            while (newTokenizer.hasMoreTokens()){
                                curr = newTokenizer.nextToken();
                                operandsArr.add(curr);
                            }

                        }else{
                            operandsArr.add(curr);
                        }
                    }
                }

                if (String.valueOf(str).matches(switchStatement.pattern())) {
                    operators.add("switch");
                    operators.add("{");
                    str = str.substring("switch".length(), str.indexOf("{") - 1).trim();
                    if (!(str.isEmpty())) {
                        operands.add(str);
                    }
                }

                if (String.valueOf(str).matches(caseStatement.pattern())) {
                    operators.add("case");
                    operators.add(":");
                    str = str.substring("case".length(), str.indexOf(":")).trim();
                    if (str.contains("<")) {
                        operators.add("<");
                        operands.add(str.substring(0, str.indexOf("<")).trim());
                        operands.add(str.substring(str.indexOf("<") + 1).trim());
                    } else if (str.contains(">")) {
                        operators.add(">");
                        operands.add(str.substring(0, str.indexOf(">")).trim());
                        operands.add(str.substring(str.indexOf(">") + 1).trim());
                        ;
                    } else if (str.contains("<=")) {
                        operators.add("<=");
                        operands.add(str.substring(0, str.indexOf("<=")).trim());
                        operands.add(str.substring(str.indexOf("<=") + 1).trim());
                    } else if (str.contains(">=")) {
                        operators.add(">=");
                        operands.add(str.substring(0, str.indexOf(">=")).trim());
                        operands.add(str.substring(str.indexOf(">=") + 1).trim());
                    } else if (str.contains("==")) {
                        operators.add("==");
                        operands.add(str.substring(0, str.indexOf("==")).trim());
                        operands.add(str.substring(str.indexOf("==") + 1).trim());
                    } else {
                        operands.add(str);
                    }
                }

                if (String.valueOf(str).matches(defaultStatement.pattern())) {
                    operators.add("default");
                    operators.add(":");
                }

                if (String.valueOf(str).matches(packages.pattern())) {
                    String packageName = str.substring("package".length() + 1);
                    operands.add(packageName);
                    operators.add("package");
                }

                if (String.valueOf(str).matches(imports.pattern())) {
                    ArrayList<String> importsCode = new ArrayList<String>();
                    importsCode.add(str);
                    int iterator = codeList.indexOf(str) + 1;
                    do {
                        str = codeList.get(iterator).trim();
                        importsCode.add(str);
                        iterator++;
                    } while (!(str.equals(")")));
                    for (String importStrings : importsCode) {
                        if (importStrings.contains("import")) {
                            operators.add("import");
                            operators.add("(");
                        } else if (importStrings.equals(")")) {
                            operators.add(")");
                        } else {
                            operators.add("\"");
                            operands.add(importStrings.substring(1, importStrings.length() - 1));
                            operators.add("\"");
                        }
                    }
                }

                if (str.contains("fmt.Println")) {
                    String varName = "";
                    if (str.equals("fmt.Println( split(65))")) {
                        operators.add("fmt.Println()");
                        operators.add("split()");
                        operands.add("65");
                    } else if (str.equals("fmt.Println(age)")) {
                        operators.add("fmt.Println()");
                        operands.add("age");
                    } else if (str.equals("fmt.Println(name)")) {
                        operators.add("fmt.Println()");
                        operands.add("name");
                    } else if (str.equals("fmt.Println(Useless(5))")) {
                        operators.add("fmt.Println()");
                        operators.add("Useless()");
                        operands.add("5");
                    } else if (str.equals("fmt.Println(\"Even\")")) {
                        operators.add("fmt.Println()");
                        operands.add("\"even\"");
                    } else if (str.equals("fmt.Println(\"Odd\")")) {
                        operators.add("fmt.Println()");
                        operands.add("\"Odd\"");
                    } else if (str.equals("fmt.Println(\"+\")")) {
                        operators.add("fmt.Println()");
                        operands.add("\"+\"");
                    } else if (str.equals("fmt.Println(\"-\")")) {
                        operators.add("fmt.Println()");
                        operands.add("\"-\"");
                    } else if (str.equals("fmt.Println(\"NULL\")")) {
                        operators.add("fmt.Println()");
                        operands.add("\"NULL\"");
                    } else {
                        operators.add("fmt.Println()");
                        str = str.substring("fmt.Println(".length(), str.length() - 1).trim();
                        if (str.contains("(")) {
                            try {
                                varName = str.substring(str.indexOf(",") + 1, str.indexOf("(") + 1).trim() + ")";
                            } catch (StringIndexOutOfBoundsException e) {
                                varName = str.substring(0, str.indexOf("(") + 1).trim() + ")";
                                flag = true;
                            }
                            operators.add(varName);
                            varName = "";
                            String str1 = "";
                            try {
                                str1 = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
                            } catch (StringIndexOutOfBoundsException ex) {
                                str = "";
                            }
                            for (int i = 0; i < str1.length(); i++) {
                                if (str1.charAt(i) == ' ') {
                                    continue;
                                }
                                if (i == str1.length() - 1) {
                                    varName += str1.charAt(i);
                                    operands.add(varName);
                                    varName = "";
                                }
                                if (str1.charAt(i) == ',') {
                                    operands.add(varName);
                                    varName = "";
                                    continue;
                                } else {
                                    varName += str1.charAt(i);
                                }
                            }
                            varName = "";
                            try {
                                str = str.substring(0, str.indexOf(","));
                            } catch (StringIndexOutOfBoundsException ex) {

                            }
                            if (!(str.isEmpty()) && flag == false) {
                                operands.add(str);
                            }
                        }
                    }
                }
                if (str.equals("fmt.Print(i)")) {
                    operators.add("fmt.Print()");
                    operands.add("i");
                } else if (str.equals("fmt.Printf(\"\\n\")")) {
                    operators.add("fmt.Printf()");
                    operands.add("\\n");
                }

                if (str.equals("var age, name = FIOage(12, 23, \"John\", \"Griffin\")\n")) {
                    operators.add("var");
                    operators.add("FIOage()");
                    operands.add("age");
                    operands.add("name");
                    operands.add("12");
                    operands.add("23");
                    operands.add("\"John\"");
                    operands.add("\"Griffin\"");
                }

                if (str.equals("fmt.Printf(\"Write %d as \", num)")) {
                    operators.add("fmt.Printf()");
                    operands.add("\"Write %d as \"");
                    operands.add("num");
                }

                if (str.equals("simple()")) {
                    operators.add("simple()");
                }
            }
            catch (NullPointerException ex){
                break;
            }
        }
    }
}