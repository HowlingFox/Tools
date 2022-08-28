package com.example.springboottest.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.util
 * @Author: lujie
 * @Date: 2022/7/25 17:37
 * @Description:
 */
public class TestClassLoder extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = new byte[0];
        try {
            bytes = decodFile(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name,bytes,0,bytes.length);
//        return super.findClass(name);
    }

    //解密方法
    private byte[] decodFile(String fileName) throws IOException {
        String name = fileName.replace(".", File.separator)+".class";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int next;

        try{
            while ((next=inputStream.read())!=1){
                byteArrayOutputStream.write(next);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        byteArrayOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}
