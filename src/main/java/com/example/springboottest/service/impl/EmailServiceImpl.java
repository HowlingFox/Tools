package com.example.springboottest.service.impl;

import com.example.springboottest.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest.service
 * @Author: lujie
 * @Date: 2021/12/14 19:16
 * @Description:
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;
    //注入配置文件中配置的信息——>from
    @Value("${spring.mail.from}")
    private String from;

    private static Properties props;
    private static Session session;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom(from);
        //收件人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        javaMailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message,true);
            messageHelper.setFrom(new InternetAddress(from,"歌斐业务审批系统(新)","UTF-8"));
            messageHelper.setTo(to);
            message.setSubject(subject);
            messageHelper.setText(content,true);
            javaMailSender.send(message);
            logger.info("邮件已经发送！");
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常："+e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content,true);
            //携带附件
            FileSystemResource file = new FileSystemResource(filePath);
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            messageHelper.addAttachment(fileName,file);

            javaMailSender.send(message);
            logger.info("邮件加附件发送成功！");
        } catch (MessagingException e) {
            logger.error("发送失败："+e);
        }
    }

    /**
     * 推送日程（outlook）
     * @param enailFrom 发件人
     * @param mailTo 收件人
     * @param organizer 组织者
     * @param participant 参与者
     * @param subject 主题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param location 地点
     * @param describe 会议描述
     * @param trigger 提前多长时间提醒
     * @param msg 邮件正文
     */
    @Override
    public void sendMeetingInvitationEmail(String enailFrom,String mailTo,String organizer,String participant,
            String subject, String startDate, String endDate, String location, String describe, String trigger, String msg) {
        try {
            props = new Properties();
            //发件人
            String fromEmail = props.getProperty("fromEmail", enailFrom);
            //收件人(会议官)
            String toEmail = props.getProperty("toEmail", mailTo);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.host", "smtp.office365.com");
            //当前smtp host设为可信任 否则抛出javax.mail.MessagingException: Could not  convert socket to TLS
            props.put("mail.smtp.ssl.trust", "smtp.office365.com");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl", "true");
            //开启debug调试，控制台会打印相关信息
            props.put("mail.debug", "true");
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    //发件人邮箱账号
                    String userId = props.getProperty("userId", "lujie_dev@outlook.com");
                    //发件人邮箱密码(qq、163等邮箱用的是授权码,outlook是密码)
                    String password = props.getProperty("password", "lujie@2022");
                    return new PasswordAuthentication(userId, password);
                }
            };
            session = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            //标题
            message.setSubject(subject);
            //开始时间
            String startTime = getUtc(startDate);
            //会议结束时间
            String endTime = getUtc(endDate);
            StringBuffer buffer = new StringBuffer();
            buffer.append("BEGIN:VCALENDAR\n"
                    + "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
                    + "VERSION:2.0\n"
                    + "METHOD:REQUEST\n"
                    + "BEGIN:VEVENT\n"
                    //参会者
                    + "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+participant+"\n"
                    //组织者
                    + "ORGANIZER:MAILTO:"+organizer+"\n"
                    + "DTSTART:" + startTime + "\n"
                    + "DTEND:" + endTime + "\n"
                    //会议地点
                    + "LOCATION:"+location+"\n"
                    //如果id相同的话，outlook会认为是同一个会议请求，所以使用uuid。
                    + "UID:" + UUID.randomUUID() + "\n"
                    + "CATEGORIES:\n"
                    //会议描述
                    //+ "DESCRIPTION:Stay Hungry.<br>Stay Foolish.\n\n"
                    + "SUMMARY:"+describe+"\n" + "PRIORITY:5\n"
                    + "CLASS:PUBLIC\n" + "BEGIN:VALARM\n"
                    //提前x分钟提醒
                    + "TRIGGER:-PT"+trigger+"M\n" + "ACTION:DISPLAY\n"
                    + "DESCRIPTION:Reminder\n" + "END:VALARM\n"
                    + "END:VEVENT\n" + "END:VCALENDAR");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(),
                    "text/calendar;method=REQUEST;charset=\"UTF-8\"")));
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            //String emailText = getHtmlContent(sendEmailApi.getTemplateContent(tempValue),tempMap);
            //文本类型正文
            mimeBodyPart.setText(msg);
            //html类型正文
            //mimeBodyPart.setContent(emailText,"text/html;charset=UTF-8");
            //添加正文
            multipart.addBodyPart(mimeBodyPart);
            //添加日历
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            message.setSentDate(new Date());
            message.saveChanges();
            Transport.send(message);
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 转utc时间
     *
     * @param str
     * @return
     */
    private static String getUtc(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long millionSeconds = 0;
        try {
            millionSeconds = sdf.parse(str).getTime();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        //utc时间差8小时
        long currentTime = millionSeconds - 8 * 60 * 60 * 1000;
        Date date = new Date(currentTime);
        //格式化日期
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = "";
        nowTime = df.format(date);
        //转换utc时间
        String utcTime = nowTime.replace("-", "").replace(" ", "T").replace(":", "");
        return utcTime;
    }

}
