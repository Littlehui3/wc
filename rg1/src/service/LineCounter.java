package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.Stream;

public class LineCounter implements Counter{

    @Override
    public long count(List<File> fileList){
        long num = 0;
        for (File file :
                fileList) {
            //读文件
            try {
                Scanner scanner = new Scanner(file);
                Stream<MatchResult> set = scanner.findAll("\\n");
                num = set.count();
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(num == 0){
            //空文件
            num = -1;
        }
        return num+1;
    }

}
