package com.example.springboottest.service;

/**
 * @BelongsProject: SpringbooTest
 * @BelongsPackage: com.example.springboottest
 * @Author: lujie
 * @Date: 2021/12/14 19:12
 * @Description:
 */
public interface EmailService {
    /**
     * 发送文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 推送日程（outlook）
     * @param enailFrom 发件人
     * @param mailto 收件人
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
    void sendMeetingInvitationEmail(String enailFrom,String mailto,String organizer,String participant,String subject,String startDate,String endDate,String location,String describe,
                                    String trigger,String msg);


}
