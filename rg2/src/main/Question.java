package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Question {
    public static HashMap<String, String> createSuanShi(int createNum, int size) {
        if (size == 0) {
            size = 100;
        }
        HashMap<String, String> weWant = new LinkedHashMap<>();
        while (weWant.size() < createNum) {
            int opNum = (int) (Math.random() * 3) + 1;
            String[] op = new String[opNum];
            for (int j = 0; j < opNum; j++) {
                int temp = (int) (Math.random() * 4);
                switch (temp) {
                    case 0: {
                        op[j] = "+";
                        break;
                    }
                    case 1: {
                        op[j] = "-";
                        break;
                    }
                    case 2: {
                        op[j] = "*";
                        break;
                    }
                    case 3: {
                        op[j] = "÷";
                        break;
                    }
                }
            }
            String[] result = createFun(op, size).split("=");

            //判重
            if ((weWant.get(result[1]))!=null) {
                String whoAreYou = weWant.get(result[1]);
                int calChar =0;
                for (int a=0;a<op.length;a++) {
                    if (whoAreYou.contains(op[a]))
                        calChar++;
                }
                if(calChar == op.length) {
                    StringBuilder sb = new StringBuilder(whoAreYou);
                    while(sb.toString().contains("(")) {
                        sb.deleteCharAt(sb.lastIndexOf("("));
                    }
                    while(sb.toString().contains(")")) {
                        sb.deleteCharAt(sb.lastIndexOf(")"));
                    }

                    StringBuilder sb2 = new StringBuilder(result[0]);
                    while(sb2.toString().contains("(")) {
                        sb2.deleteCharAt(sb2.lastIndexOf("("));
                    }
                    while(sb2.toString().contains(")")) {
                        sb2.deleteCharAt(sb2.lastIndexOf(")"));
                    }

                    String[] numArr1 = sb.toString().split("[\\+\\-\\*\\÷]");
                    String numArr2 = Arrays.toString(sb2.toString().split("[\\+\\-\\*\\÷]"));
                    int xxx=0;
                    for (int b = 0;b<numArr1.length;b++) {
                        if(numArr2.contains(numArr1[b])) {
                            xxx++;
                        }
                    }
                    if(xxx==numArr1.length) {
                        continue;
                    }
                }
            }
            weWant.put(result[1], result[0]);
        }
        return weWant;
    }

    public static String createFun(String[] op, int size) {
        StringBuffer suanShi = new StringBuffer();
        //确定符号数
        int length = op.length;
        //初始化结果为0
        Fract result = new Fract(0, 1);
        for (String c : op) {
            switch (c) {
                case "+": {
                    if (suanShi.length() > 0 && suanShi != null) {
                        Fract c1 = createFra(size);
                        result = result.add(c1);
                        if ((suanShi.toString().contains("*") || suanShi.toString().contains("÷")) && (int) (Math.random() * 2) == 0 && length == 2) {
                            suanShi.insert(0, c1.getFract() + "+");
                        } else if (suanShi.toString().contains("*") && suanShi.toString().contains("÷") && (int) Math.random() * 3 == 0) {
                            suanShi.insert(0, c1.getFract() + "+");
                        } else {
                            if (length == 3 && suanShi.toString().contains("÷") && op[0] == "-") {
                                suanShi.insert(0, "(");
                                suanShi.insert(suanShi.length(), ")");
                            }
                            suanShi.append("+" + c1.getFract());
                        }
                    } else {
                        Fract a = createFra(size);
                        Fract b = createFra(size);
                        result = a.add(b);
                        suanShi.append(a.getFract() + "+" + b.getFract());
                    }
                    break;
                }
                case "-": {
                    if (suanShi.length() > 0 && suanShi != null) {
                        Fract c1 = createFra(size);
                        boolean flag = whoBig(result, c1);
                        if (flag) {
                            result = c1.sub(result);
                        } else {
                            result = result.sub(c1);
                        }

                        if ((flag && suanShi.toString().contains("*") || suanShi.toString().contains("÷")) && (int) (Math.random() * 3) == 0 && (length == 2 || length == 3)) {
                            if (length == 3 && (suanShi.toString().contains("*") || suanShi.toString().contains("÷")) && op[1] == "+") {
                                suanShi.insert(0, "(");
                                suanShi.insert(suanShi.length(), ")");
                            }
                            suanShi.insert(0, c1.getFract() + "-");
                        } else if (flag && suanShi.toString().contains("*") && suanShi.toString().contains("÷") && (int) (Math.random() * 3) == 0) {
                            if (length == 3 && (suanShi.toString().contains("*") || suanShi.toString().contains("÷")) && op[1].equals("+")) {
                                suanShi.insert(0, "(");
                                suanShi.insert(suanShi.length(), ")");
                            }
                            suanShi.insert(0, c1.getFract() + "-");
                        } else {
                            if (flag) {
                                suanShi.insert(0, "(");
                                suanShi.insert(suanShi.length(), ")");
                                suanShi.insert(0, c1.getFract() + "-");
                            } else {
                                suanShi.append("-" + c1.getFract());
                            }
                        }
                        if (length == 3 && op[1].equals("-") && (op[2].equals("*") || op[2].equals("÷"))) {
                            suanShi.insert(0, "(");
                            suanShi.insert(suanShi.length(), ")");
                        }
                    } else {
                        Fract a = createFra(size);
                        Fract b = createFra(size);
                        if (whoBig(a, b)) {
                            result = b.sub(a);
                            suanShi.append(b.getFract() + "-" + a.getFract());
                        } else {
                            result = a.sub(b);
                            suanShi.append(a.getFract() + "-" + b.getFract());
                        }
                    }
                    break;
                }
                case "*": {
                    if (suanShi.length() > 0 && suanShi != null) {
                        Fract c1 = createFra(size);
                        result = result.multi(c1);
                        if (length == 3 && (op[0].equals("+") || op[0].equals("-")) && (op[1].equals("+") || op[1].equals("-")) && !suanShi.toString().contains("(")) {
                            suanShi.insert(0, "(");
                            suanShi.insert(suanShi.length(), ")");
                        } else {
                            if ((length == 2 || length == 3) && ((suanShi.toString().contains("+") || suanShi.toString().contains("-"))) && !suanShi.toString().contains("(")) {
                                suanShi.insert(0, "(");
                                suanShi.insert(suanShi.length(), ")");
                            }
                            if (length == 3 && (suanShi.toString().contains("÷") && (suanShi.toString().contains("+") || suanShi.toString().contains("-"))) && !suanShi.toString().contains("((") && op[0] != "*") {
                                suanShi.insert(0, "(");
                                suanShi.insert(suanShi.length(), ")");
                            }
                        }
                        suanShi.append("*" + c1.getFract());
                    } else {
                        Fract a = createFra(size);
                        Fract b = createFra(size);
                        result = a.multi(b);
                        suanShi.append(a.getFract() + "*" + b.getFract());
                    }
                    break;
                }
                case "÷": {
                    if (suanShi.length() > 0 && suanShi != null) {
                        Fract c1 = createFra(size);
                        while (c1.getA() == 0) {
                            c1 = createFra(size);
                        }
                        result = result.div(c1);
                        if ((length == 3 && (op[0].equals("+") || op[0].equals("-")) && (op[1].equals("+") || op[1].equals("-")) && !suanShi.toString().contains("("))) {
                            suanShi.insert(0, "(");
                            suanShi.insert(suanShi.length(), ")");
                        }

                        if ((length == 2 || length == 3) && (suanShi.toString().contains("+") || suanShi.toString().contains("-")) && (suanShi.toString().contains("*") && suanShi.toString().contains("÷"))) {
                            suanShi.insert(0, "(");
                            suanShi.insert(suanShi.length(), ")");
                        }
                        if (length == 3 && (suanShi.toString().contains("*")) && ((suanShi.toString().contains("+") || suanShi.toString().contains("-"))) && !suanShi.toString().contains("((") && !op[0].equals("*")) {
                            suanShi.insert(0, "(");
                            suanShi.insert(suanShi.length(), ")");
                        }
                        if (length == 3 && (suanShi.toString().contains("+") || suanShi.toString().contains("-")) && (op[2].equals("-") || op[2].equals("+"))) {
                            suanShi.insert(0, "(");
                            suanShi.insert(suanShi.length(), ")");
                        }
                        if (length == 3 && op[1].equals("÷") && (op[0].equals("+") || op[0].equals("-"))) {
                            suanShi.insert(0, "(");
                            suanShi.insert(suanShi.length(), ")");
                        }
                        suanShi.append("÷" + c1.getFract());
                    } else {
                        Fract a = createFra(size);
                        Fract b = createFra(size);
                        while (b.getA() == 0) {
                            b = createFra(size);
                        }
                        result = a.div(b);
                        suanShi.append(a.getFract() + "÷" + b.getFract());
                    }
                    break;
                }
            }
        }
        return suanShi.append("=" + (result.getA() == 0 ? 0 : result.getFract())).toString();
    }

    private static boolean whoBig(Fract l, Fract r) {

        boolean flag = false;
        if (l.getA() * r.getB() < l.getB() * r.getA()) {
            flag = true;
        }
        return flag;
    }

    private static Fract createFra(int size) {
        if ((int) (Math.random() * 3) == 0) {
            return new Fract((int) (Math.random() * size + 1), (int) (Math.random() * size) + 2);
        } else {
            return new Fract((int) (Math.random() * size + 1), 1);
        }
    }
}
