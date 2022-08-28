package com.example.springboottest.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

@Component
@ConfigurationProperties(prefix = "upload")
public class RSAUtil {
    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */

    private final static int KEY_SIZE = 1024;

    private static RSAUtil rsaUtil = null;

    private Map<String, String> keyMap;

    private RSAUtil() {

    }

    public static RSAUtil initRSAUtil() {
        if (rsaUtil == null) {
            rsaUtil = new RSAUtil();
        }
        return rsaUtil;
    }

    /**
     * 初始化密匙
     */
    public void t() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
    }

    public Map<String,String> initKey() throws NoSuchAlgorithmException {

        //强制单例
        if (keyMap == null) {
            keyMap = new HashMap<>();
            // 公钥
            keyMap.put("RSAPublicKey", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgGwww1IN02a9hWikxZRaesVybehvMhwiUeo9SQdHnxzQOjN4ojIERe1fAH9voiwzAEvFXbNv6LFNsal+n3kPXVOnCMaBDowUj/DWihjiyfd2aprX4kUkj5L9olPUA1kIjpCnVr/o27Qig/wugqnTAd9LISKRZxseOAi7eJZfVnwIDAQAB");
            // 私钥
            keyMap.put("RSAPrivateKey", "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKAbDDDUg3TZr2FaKTFlFp6xXJt6G8yHCJR6j1JB0efHNA6M3iiMgRF7V8Af2+iLDMAS8Vds2/osU2xqX6feQ9dU6cIxoEOjBSP8NaKGOLJ93ZqmtfiRSSPkv2iU9QDWQiOkKdWv+jbtCKD/C6CqdMB30shIpFnGx44CLt4ll9WfAgMBAAECgYEAnoSJGmOeT/gji5jQBibXMYHbddh9Y5Air5d1BXVDlV8GrI1OfAk41Q0xAXjJq0DY++cFapDOb7CpXEMpuDP5J82pog5g7/Ut4RjQT9MhBl6BeFqOA++qmY2Qhg3GPHtY+0EM4pD7tbMyN2lQWa4ofrXy1rPFu4U6IoiFLdhvZ/ECQQDScHlurVCvo7kDd3fg980X5UuFAUxTVd8oFLbXMQ/xSvdTws2zPVUC5m8IBdwwNFenDFUD3FOMm9wWvnGVDBg3AkEAwsTYmpmqQ/70Ep9d7NB23jpXrVhPmWeVZPtShxYHv1FEHLPTDayKzmT0mu+NKYKKmfv/Xlhl8d6/FSd3PP2p2QJBAKNX9on4MacD2HpeY0jWT/X6X3IwHcSZ5QvBeGL0jEFpRS1ZCj3Un4YOw6RgmBc6qcbaharnfFqqKF3nGVbt+mUCQBFh8hbyKfEBmxTiZkaRYFp7CIIvBIOjbADkMTxfKffYL+UD440v3f+HoREG2ilfPjF5/ROVfhu1Doa7y/eEiBkCQDBkbA5KCfhxy06K+ph3QChGTFOJvOaO+s0ubSZo/XWKF9NkHEZAymqyCWtddG/1ZfxJJj15GCb0lrivfqBEKtw=");

        }
        return keyMap;
    }

    public String getPublicKey(Map<String,String> key){
        String rsaPublicKey = "";
        try {
            rsaPublicKey = key.get("RSAPublicKey");
        } catch (Exception e){
            System.out.println("获取公钥失败,请先调用initKey()");
        }
        return rsaPublicKey;
    }

    public static String getPrivateKey(Map<String,String> key){
        String rsaPublicKey = "";
        try {
            rsaPublicKey = key.get("RSAPrivateKey");
        } catch (Exception e){
            System.out.println("获取私钥失败,请先调用initKey()");
        }
        return rsaPublicKey;
    }
    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str);
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static Key DEFAULT_KEY = null;

    public static final String DEFAULT_SECRET_KEY = "MIIEYzCCAkugAwIBAgIDIAZmMA0GCSqGSIb3DQEBCwUAMC4xCzAJBgNVBAYTAkRF MRIwEAYDVQQKDAlTU09DaXJjbGUxCzAJBgNVBAMMAkNBMB4XDTE2MDgwMzE1MDMy M1oXDTI2MDMwNDE1MDMyM1owPTELMAkGA1UEBhMCREUxEjAQBgNVBAoTCVNTT0Np cmNsZTEaMBgGA1UEAxMRaWRwLnNzb2NpcmNsZS5jb20wggEiMA0GCSqGSIb3DQEB AQUAA4IBDwAwggEKAoIBAQCAwWJyOYhYmWZF2TJvm1VyZccs3ZJ0TsNcoazr2pTW cY8WTRbIV9d06zYjngvWibyiylewGXcYONB106ZNUdNgrmFd5194Wsyx6bPvnjZE ERny9LOfuwQaqDYeKhI6c+veXApnOfsY26u9Lqb9sga9JnCkUGRaoVrAVM3yfghv /Cg/QEg+I6SVES75tKdcLDTt/FwmAYDEBV8l52bcMDNF+JWtAuetI9/dWCBe9VTC asAr2Fxw1ZYTAiqGI9sW4kWS2ApedbqsgH3qqMlPA7tg9iKy8Yw/deEn0qQIx8Gl VnQFpDgzG9k+jwBoebAYfGvMcO/BDXD2pbWTN+DvbURlAgMBAAGjezB5MAkGA1Ud EwQCMAAwLAYJYIZIAYb4QgENBB8WHU9wZW5TU0wgR2VuZXJhdGVkIENlcnRpZmlj YXRlMB0GA1UdDgQWBBQhAmCewE7aonAvyJfjImCRZDtccTAfBgNVHSMEGDAWgBTA 1nEA+0za6ppLItkOX5yEp8cQaTANBgkqhkiG9w0BAQsFAAOCAgEAAhC5/WsF9ztJ Hgo+x9KV9bqVS0MmsgpG26yOAqFYwOSPmUuYmJmHgmKGjKrj1fdCINtzcBHFFBC1 maGJ33lMk2bM2THx22/O93f4RFnFab7t23jRFcF0amQUOsDvltfJw7XCal8JdgPU g6TNC4Fy9XYv0OAHc3oDp3vl1Yj8/1qBg6Rc39kehmD5v8SKYmpE7yFKxDF1ol9D KDG/LvClSvnuVP0b4BWdBAA9aJSFtdNGgEvpEUqGkJ1osLVqCMvSYsUtHmapaX3h iM9RbX38jsSgsl44Rar5Ioc7KXOOZFGfEKyyUqucYpjWCOXJELAVAzp7XTvA2q55 u31hO0w8Yx4uEQKlmxDuZmxpMz4EWARyjHSAuDKEW1RJvUr6+5uA9qeOKxLiKN1j o6eWAcl6Wr9MreXR9kFpS6kHllfdVSrJES4ST0uh1Jp4EYgmiyMmFCbUpKXifpsN WCLDenE3hllF0+q3wIdu+4P82RIM71n7qVgnDnK29wnLhHDat9rkC62CIbonpkVY mnReX0jze+7twRanJOMCJ+lFg16BDvBcG8u0n/wIDkHHitBI7bU1k6c6DydLQ+69 h8SCo6sO9YuD+/3xAGKad4ImZ6vTwlB4zDCpu6YgQWocWRXE+VkOb+RBfvP755PU aLfL63AFVlpOnEpIio5++UjNJRuPuAA=";

    public static final String DES = "DES";

    static {
        DEFAULT_KEY = obtainKey(DEFAULT_SECRET_KEY);
    }

    /**
     * 获得key
     **/
    public static Key obtainKey(String key) {
        if (key == null) {
            return DEFAULT_KEY;
        }
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance(DES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        generator.init(new SecureRandom(key.getBytes()));
        Key key1 = generator.generateKey();
        generator = null;
        return key1;
    }

    /**
     * 加密<br>
     * String明文输入,String密文输出
     */
    public static String encode(String str) {
        return encode(null, str);
    }

    /**
     * 加密<br>
     * String明文输入,String密文输出
     */
    public static String encode(String key, String str) {
        return Base64.encodeBase64URLSafeString(obtainEncode(key, str.getBytes()));
        // return Hex.encodeHexString(obtainEncode(key, str.getBytes()));
        // 可以转化为16进制数据
    }

    /**
     * 解密<br>
     * 以String密文输入,String明文输出
     */
    public static String decode(String str) {
        return decode(null, str);
    }

    /**
     * 解密<br>
     * 以String密文输入,String明文输出
     */
    public static String decode(String key, String str) {
        return new String(obtainDecode(key, Base64.decodeBase64(str)));
        // 可以转化为16进制的数据
//      try {
//          return new String(obtainDecode(key, Hex.decodeHex(str.toCharArray())));
//      } catch (DecoderException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }
    }

    /**
     * 加密<br>
     * 以byte[]明文输入,byte[]密文输出
     */
    private static byte[] obtainEncode(String key, byte[] str) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            Key key1 = obtainKey(key);
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, key1);
            byteFina = cipher.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * 解密<br>
     * 以byte[]密文输入,以byte[]明文输出
     */
    private static byte[] obtainDecode(String key, byte[] str) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            Key key1 = obtainKey(key);
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, key1);
            byteFina = cipher.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    public static String encodeRedirectFormat(String samlXML) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, true);
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(os, deflater);
        deflaterOutputStream.write(samlXML.getBytes("UTF-8"));
        deflaterOutputStream.close();
        os.close();
        String base64 = Base64.encodeBase64String(os.toByteArray());
        return URLEncoder.encode(base64, "UTF-8");
    }



    public static void main(String[] args) throws Exception {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        SimpleDateFormat year = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.sssssss");
        String yearformat = year.format(System.currentTimeMillis());
        String timeformat = time.format(System.currentTimeMillis());
        String a = "";
        String s = encodeRedirectFormat(a);
        System.out.println(s);
    }
}
