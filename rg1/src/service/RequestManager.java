package service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class RequestManager {
    //创建一个线程池管理用户的请求
    private ExecutorService pool = Executors.newSingleThreadExecutor();
    private Request request = new Request();
    private String[] args;

    public Map<String,Long> doRequest(){
        HashMap<String,Long> resultMap = null;

        if(null != args){
            if(null != request) {
                request.setArgs(args);
                Future<HashMap<String, Long>> future = pool.submit(request);
                try {
                    /*
                    int loop = 0;
                    while(!future.isDone()){
                        Thread.currentThread().sleep(100);
                        loop++;
                        if(loop == 50){
                            //等待了5秒还没结果，返回
                            break;
                        }
                    }*/
                    resultMap = future.get();//get方法会等待任务完成
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultMap;
    }

    public void closeRequestManager(){
        pool.shutdown();
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
