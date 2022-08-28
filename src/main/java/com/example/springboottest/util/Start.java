package com.example.springboottest.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.util
 * @Author: lujie
 * @Date: 2021/12/29 11:19
 * @Description:
 */
public class Start {
    public static void sendEmail(String toEmail, String title, String content, String fromEmail, String password, String HOST, String userName) throws MessagingException {
        System.out.println("toEmail:"+toEmail);
        Properties props;
        Session session;
        props = new Properties();
//        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "smtp.office365.com");
        //当前smtp host设为可信任 否则抛出javax.mail.MessagingException: Could not  convert socket to TLS
        props.put("mail.smtp.ssl.trust", "smtp.office365.com");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl", "true");
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //发件人邮箱账号
//                String userId = userName;
                String userId = "lujie_dev@outlook.com";
                //发件人邮箱密码(qq、163等邮箱用的是授权码,outlook是密码)
//                String pwd = KmsUtil.decrypt("noahkms", password);// PASSWORD;
                String pwd = "lujie@2022";
                return new PasswordAuthentication(userId, pwd);
            }
        };
        session = Session.getInstance(props, authenticator);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));

        //设置收件人地址，以逗号隔开
        InternetAddress[] sendTo = InternetAddress.parse(toEmail);
        message.setRecipients(Message.RecipientType.TO, sendTo);
        //标题
        message.setSubject(title);
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        //html类型正文
        mimeBodyPart.setContent(content, "text/html;charset=UTF-8");
        //添加正文
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        message.setSentDate(new Date());
        message.saveChanges();
        Transport.send(message);
    }

    public String getEmails(List<String> emails) {
        String result = "";
        if (emails != null && emails.size() > 0) {
            result = result + ",";
        }
        if (result.length() > 0) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (isLinux()){

        }else {
            reStartWin();
        }

    }
    static boolean isLinux(){
        String os = System.getProperty("os.name");
        if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            return true;
        }
        return false;
    }
    static void reStartWin() throws IOException, InterruptedException {
        String sourcePath ="D:\\Code\\IdeaProjects\\公司OA\\api\\";
        powerOff();
        bulid(sourcePath);
        run(sourcePath);
    }

    static void run(String sourcePath) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        BufferedReader br = new BufferedReader(new InputStreamReader(runtime.exec("cmd /c cd "+sourcePath+" && java -jar target\\api.jar").getInputStream()));
        String line;
        while ((line = br.readLine())!=null){
            System.out.println(line);
        }
    }
    static void powerOff() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        BufferedReader br = new BufferedReader(new InputStreamReader(runtime.exec("cmd /c netstat -a -n -o | findstr :88").getInputStream()));
        String line;
        Long pid = 0L;
        while ((line = br.readLine())!=null){
            System.out.println(line);
            if (line.contains("0.0.0.0")){
                int i = line.lastIndexOf(" ");
                pid = Long.valueOf(line.substring(i+1));
                runtime.exec("cmd /c taskkill /pid "+pid+" -t -f");
            }
        }
    }

    static void bulid(String sourcePath) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd /c cd "+sourcePath+" && mvn package");
        Thread.sleep(1000*20);
    }
}
