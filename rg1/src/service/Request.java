package service;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;

public class Request implements Callable<HashMap<String,Long>> {
    private List<File> fileList = new ArrayList<File>();
    HashMap<String,Long> resultMap = new HashMap<String, Long>();
    private String[] args;

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public HashMap<String,Long> call() throws Exception {

        //处理文件名
        addFiles(args);

        //处理选项
        dealingOptions();

        return resultMap;
    }

    private void dealingOptions(){
        for (String arg :
                args) {
            switch (arg) {
                case "-c":
                    resultMap.put(arg, new CharCounter().count(fileList));
                    break;
                case "-w":
                    resultMap.put(arg, new WordCounter().count(fileList));
                    break;
                case "-l":
                    resultMap.put(arg, new LineCounter().count(fileList));
                    break;
                case "-s":
                    String fileType = null;
                    for (String s :
                            args) {
                        if(s.charAt(0) == '*'){
                            fileType = s.substring(2);
                        }
                    }

                    File dir = new File(System.getProperty("user.dir"));
                    addAllFilesOfRoot(dir,fileType);
                    break;
            }
        }
    }

    private void addAllFilesOfRoot(File dir,String fileType){
        if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
            return;
        }
        String[] files = dir.list();// 读取目录下的所有目录文件信息
        for (int i = 0; i < files.length; i++) {
            File file = new File(dir, files[i]);
            if (file.isFile()) {// 如果文件
                if(fileType == null){//如果不指定文件类型
                    fileList.add(file);
                }
                else if(file.getName().substring(file.getName().indexOf(".")).equals("."+fileType)){
                    fileList.add(file);
                }
            } else {// 如果是目录
                addAllFilesOfRoot(file,fileType);
            }
        }
    }

    private void addFiles(String[] files){
        for (String arg :
                files) {
            if(arg.charAt(0) != '-'){
                File file = new File(arg);
                if(file.exists()){
                    //查重
                    boolean repeated = false;
                    for (File f2 :
                            fileList) {
                        if(f2.getAbsolutePath().equals(file.getAbsolutePath())){
                            resultMap.put(arg,(long)2);//使用2标记文件重复
                            repeated = true;
                        }
                    }
                    if(!repeated){
                        fileList.add(file);
                        resultMap.put(arg,(long)1);//使用1标记此文件已找到且不重复
                    }
                }else if(arg.charAt(0) == '*'){
                    resultMap.put(arg,(long)1);
                }else{
                    resultMap.put(arg,null);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request request = (Request) o;
        return fileList.equals(request.fileList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileList);
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
