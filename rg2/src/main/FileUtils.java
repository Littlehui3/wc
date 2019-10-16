package main;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {
    public static void creatFile(HashMap map) {
        LocalDateTime time = LocalDateTime.now();
        String timeFor = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File question = new File("D:\\Exercises@"+timeFor+".txt");
        File answer = new File("D:\\Answers@"+timeFor +".txt");
        try {
            question.createNewFile();
            answer.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter questionWrite = new PrintWriter(new FileWriter(question, true));
             PrintWriter answerWrite = new PrintWriter(new FileWriter(answer, true));) {
            int jiShu = 0;
            for (Object entry : map.entrySet()) {
                Map.Entry<String, String> temp = (Map.Entry<String, String>) entry;
                String key = temp.getKey();
                String value = temp.getValue();
                questionWrite.write(jiShu+1+".    "+value+"= ");
                questionWrite.write("\r\n");
                questionWrite.write("\r\n");
                answerWrite.write(jiShu+1+".    "+key);
                answerWrite.write("\r\n");
                answerWrite.write("\r\n");
                jiShu++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void check(String question,String yourAnswer) throws IOException {
        String answers = question.split("@")[1];
        File answersOff =  new File("D:\\Answers@"+answers);
        File yourAnswers = new File(yourAnswer);
        if(!yourAnswers.exists()) {
            System.out.println("未找到文件，请重新确认文件路径");
            System.exit(0);
        }
        if(!answersOff.exists()) {
            System.out.println("答案文件不存在，请确认");
            System.exit(0);
        }
        LocalDateTime time = LocalDateTime.now();
        String timeFor = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File grade = new File("D:\\Grade@"+timeFor+".txt");
        grade.createNewFile();
        try(BufferedReader answersOffReader = new BufferedReader(new FileReader(answersOff));
            BufferedReader yourAnswersReader = new BufferedReader(new FileReader(yourAnswers));
            PrintWriter gradeWrite = new PrintWriter(new FileWriter(grade))) {
            String temp ;
            String ans ;
            StringBuilder r = new StringBuilder();
            r.append("(");
            StringBuilder w = new StringBuilder();
            w.append("(");
            int right = 0;
            int wrong = 0;
            ans=answersOffReader.readLine();
            while ((temp=yourAnswersReader.readLine())!=null ) {
                if(temp.equals("\r\n") || temp.equals("")) {
                    continue;
                }
                while(ans==null || ans.equals("")) {
                    ans=answersOffReader.readLine();
                }
                String xuHao =ans.split("\\.")[0];
                if(temp.split("\\.").length>1) {

                    ans =ans.split("\\.")[1].trim();
                    String tempAnswer = temp.split("\\.")[1].trim();
                    if(tempAnswer.equals(ans)) {
                        r.append(xuHao+",");
                        right++;
                    }else {
                        w.append(xuHao+",");
                        wrong++;
                    }
                }else {
                    w.append(xuHao+",");
                    wrong++;
                    continue;
                }
                ans = answersOffReader.readLine();
            }
            if(r.toString().contains(",")) {
                r.deleteCharAt(r.lastIndexOf(","));
            }
            if(w.toString().contains(",")) {
                w.deleteCharAt(w.lastIndexOf(","));
            }
            r.insert(r.length(),")");
            w.insert(w.length(),")");
            gradeWrite.write("Correct:"+right+r.toString());
            gradeWrite.write("\r\n");
            gradeWrite.write("Wrong:"+wrong+w.toString());
            System.out.println("Correct:"+right+r.toString());
            System.out.println("Wrong:"+wrong+w.toString());
        }
    }

}