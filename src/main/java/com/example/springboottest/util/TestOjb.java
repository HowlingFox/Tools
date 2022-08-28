package com.example.springboottest.util;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.util
 * @Author: lujie
 * @Date: 2022/7/25 17:07
 * @Description:
 */
public class TestOjb {
    private int a = 0;
    public String doPost(){
        System.out.println("post请求");
        return "post";
    }

    public String doGet(String url,String data){
        System.out.println("GET请求:"+url+"\t"+data);
        return "get";
    }

    public String doPut(){
        System.out.println("put请求");
        return "put";
    }

    public String doDelete(){
        System.out.println("delete请求");
        return "delete";
    }
}
