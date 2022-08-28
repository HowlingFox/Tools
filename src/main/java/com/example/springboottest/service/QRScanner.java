package com.example.springboottest.service;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.*;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.service
 * @Author: lujie
 * @Date: 2022/6/24 3:26
 * @Description:
 */
public interface QRScanner {
    String readQR(InputStream inputStream) throws IOException, NotFoundException;
    String createQR(String arg, String outPutPuth, int height, int width, String charset) throws IOException, WriterException;
}
