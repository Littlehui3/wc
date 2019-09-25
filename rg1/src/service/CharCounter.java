package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class CharCounter implements Counter {
    @Override
    public long count(List<File> fileList) {
        long num = 0;

        for (File file :
                fileList) {
            //读文件
            try {
                Scanner scanner = new Scanner(file);
                scanner = scanner.useDelimiter(" ");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] strings = line.split(" ");
                    for (String s :
                            strings) {
                        if (!s.equals("")) num += s.length();
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return num;
    }
}
