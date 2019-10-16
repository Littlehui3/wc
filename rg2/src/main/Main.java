package main;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int num = 0;
        int size = 0;
        String exercisePath = null;
        String answerPath = null;
        if(args.length != 0){
            for(int i = 0; i < args.length; i++){
                //-n
                if(args[i].equals("-n")) {
                    try {
                        num = Integer.parseInt(args[i+1]);
                    }catch (Exception e) {
                        System.out.println("参数有误!");
                    }
                }
                //-r
                if(args[i].equals("-r")) {
                    try {
                        size = Integer.parseInt(args[i+1]);
                    }catch (Exception e) {
                        System.out.println("参数有误!");
                    }
                }
            }
            if(num != 0 && size != 0){
                FileUtils.creatFile(Question.createSuanShi(num,size));
            }
            if(exercisePath != null && answerPath != null){
                try {
                    FileUtils.check(exercisePath,answerPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
