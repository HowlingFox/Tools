package com.example.springboottest.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;

/**
 * @BelongsProject: apps-customize
 * @BelongsPackage: com.seeyon.apps.kk2.util
 * @Author: lujie
 * @Date: 2021/4/13 12:56
 * @Description: 文件上传，下载，token获取
 */
@Component
public class HttpCli {
    @Value("http.a")
    String a;

    private final Log log = LogFactory.getLog(HttpCli.class);
    BASE64Decoder decoder = new BASE64Decoder();

    /**
     * 根据url下载文件，保存到filepath中
     *
     * @param url
     * @return
     */
    public void download(String url, File file) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("Authorization","A934660890303F06E0536560010AFF46");
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream in = entity.getContent();
                    try {
                        byte[] buffer = new byte[1024];
                        BufferedInputStream bufferedIn = new BufferedInputStream(in);
                        int len = 0;
                        FileOutputStream fileOutStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOutStream);
                        while ((len = bufferedIn.read(buffer, 0, 1024)) != -1) {
                            bufferedOut.write(buffer, 0, len);
                        }
                        bufferedOut.flush();
                        bufferedOut.close();
                    } catch (IOException ex) {
                        throw ex;
                    } finally {
                        in.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    /**
     * 获取token
     *
     * @param url
     * @return
     */
    public JSONObject getToken(String url, String loginName) {
        String login = "";
        if (loginName.equals("")) {
            login = "lijie";
        } else {
            login = loginName;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String token = "";
        try {
            HttpGet httpget = new HttpGet(url + login);
            //设置参数到请求对象中
            httpget.setHeader("Content-type", "application/json");
            httpget.setHeader("Accept", "application/json");
            httpget.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = httpclient.execute(httpget);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                String body = EntityUtils.toString(entity, "utf-8");
                JSONObject json = JSONObject.parseObject(body);
                //获取token
                return json;

            }
            EntityUtils.consume(entity);
            //释放链接
            response.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将base64编码转换成图片
     *
     * @param base64sString 1.使用BASE64Decoder对编码的字符串解码成字节数组
     *                      2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *                      3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *                      4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     * @param fileName      文件名称
     */
    public void base64StringToImage(String base64sString, String fileName) {
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            // 将base64编码的字符串解码成字节数组
            byte[] bytes = decoder.decodeBuffer(base64sString);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            // 创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            // 指定输出的文件
            File file = new File(fileName);
            // 创建到指定文件的输出流
            fout = new FileOutputStream(file);
            // 为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            // 刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bout.close();
                fout.close();
                bin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject doPost(String url, JSONObject json) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        if (json.getString("sessionId") != null) {
            httpPost.setHeader("cookie", "JSESSIONID=" + json.getString("sessionId"));
        }
        String jsonStr = JSONObject.toJSONString(json);
        StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String jsons = EntityUtils.toString(entity);
        return JSONObject.parseObject(jsons);
    }

    public static String download(String url, String cache_path) {
        String file_path = cache_path;
        if (url == null || "".equals(url)) {
            return null;
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
//        get.setHeader("Content-Type","application/pdf");
        get.setHeader("Authorization","A934660890303F06E0536560010AFF46");
//        get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36 Edg/96.0.1054.62");
        HttpResponse resp = null;
        try {
            resp = httpClient.execute(get);
            HttpEntity entity = resp.getEntity();

            byte[] bytes = EntityUtils.toByteArray(entity);
            File file = new File(file_path);
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
            } catch (IOException e) {

            }
            OutputStream out = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(bytes);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
            is.close();
            out.close();
            return file_path;
        } catch (ConnectTimeoutException cte) {
            return null;
        } catch (SocketTimeoutException cte) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (resp != null) {
                    resp.getEntity().getContent().close();
                }
            } catch (IllegalStateException e) {
            } catch (IOException e) {
            }
        }
    }
}
