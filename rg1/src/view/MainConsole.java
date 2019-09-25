package view;

import service.RequestManager;

import java.util.Map;

public class MainConsole {

    public static void main(String[] args){
        //args是否为空
        if("".equals(args[0])){
            System.out.println("程序参数不能为空");
            return;
        }

        //以下处理要求控制台显示的请求
        RequestManager rm = new RequestManager();
        rm.setArgs(args);
        Map<String,Long> resultMap = rm.doRequest();
        if(null == resultMap){
            System.out.println("程序出错,请检查参数是否正确");
            return;
        }
        for (String key :
                args) {
            if (resultMap.containsKey(key)) {
                switch (key) {
                    case "-c":
                        System.out.println("字符数为：" + resultMap.get(key));
                        break;
                    case "-w":
                        System.out.println("单词数为：" + resultMap.get(key));
                        break;
                    case "-l":
                        System.out.println("行数为：" + resultMap.get(key));
                        break;
                }
                if(key.charAt(0) != '-'){
                    if(null == resultMap.get(key)){
                        System.out.println("没有找到此文件:" + key);
                    }else if((long)2 == resultMap.get(key)){
                        System.out.println("此文件与前面的文件重复:" + key);
                    }
                }
            }
        }
        //关闭资源
        rm.closeRequestManager();
    }
}
