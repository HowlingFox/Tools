package com.example.springboottest.controller;

import com.example.springboottest.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest{

    @Autowired
    private EmailService emailService;

    @Test
    public void sendSimpleEmail(){
        String content = "你好，恭喜你...";
        emailService.sendSimpleMail("lujie_dev@qq.com","祝福邮件",content);
    }

    @Test
    public void sendMimeEmail(){
        String htmlTemplate ="<table style='width:650px;height:550px; border-collapse:collapse;border:1px solid #ccc;font-family:Microsoft YaHei;' >\n" +
                "<tr style='height:80px;background:#036FC1;text-align:left;color:white;font-size:25px;width:100%;border-bottom: 1px solid #ccc;font-family:Microsoft YaHei;'>\n" +
                "<td> <label style='line-height:90px;font-family:Microsoft YaHei;font-size:25px;'>#flowName#</label></td>\n" +
                "</tr>\n" +
                "<tr style='height:50px;text-align:left;font-size:14px;'>\n" +
                "<td style='border-bottom: 1px solid #ccc;'>\n" +
                "<label style='text-transform:lowercase;line-height:50px;color:#4065B2;min-width:100px;text-align:right;font-weight:bold;font-size:14px;display:inline-block;font-family:Microsoft YaHei;'>任务名:</label>\n" +
                "<span  style='font-size:14px;font-family:Microsoft YaHei;'>(<a href='#link#'>#taskName#</a>)</span>\n" +
                "</td></tr>\n" +
                "<tr style='height:50px;text-align:left;font-size:14px;border-bottom: 1px solid #ccc;'>\n" +
                "<td style='border-bottom: 1px solid #ccc;'>\n" +
                "<label style='line-height:50px;color:#4065B2;min-width:100px;text-align:right;font-weight:bold;font-size:14px;display:inline-block;font-family:Microsoft YaHei;'>申请人:</label>\n" +
                "<span  style='font-size:14px;font-family:Microsoft YaHei;'>#userName#</span>\n" +
                "</td></tr>\n" +
                "<tr style='height:50px;text-align:left;font-size:14px;border-bottom: 1px solid #ccc;'>\n" +
                "<td style='border-bottom: 1px solid #ccc;'>\n" +
                "<label style='line-height:50px;color:#4065B2;min-width:100px;text-align:right;font-weight:bold;font-size:14px;display:inline-block;font-family:Microsoft YaHei;'>申请部门:</label>\n" +
                "<span  style='font-size:14px;font-family:Microsoft YaHei;'>#deapName#</span>\n" +
                "</td></tr>\n" +
                "<tr style='height:50px;text-align:left;font-size:14px;border-bottom: 1px solid #ccc;'>\n" +
                "<td style='border-bottom: 1px solid #ccc;'>\n" +
                "<label style='line-height:50px;color:#4065B2;min-width:100px;text-align:right;font-weight:bold;font-size:14px;display:inline-block;font-family:Microsoft YaHei;'>申请时间:</label>\n" +
                "<span  style='font-size:14px;font-family:Microsoft YaHei;'>#endTime#</span>\n" +
                "</td></tr>\n" +
                "<tr style='height:40px;background:#EAEAEA;;text-align:left;font-size:13px;color:#9A9A9A;padding-left:10px;padding-top:14px;padding-right:14px;border-bottom: 1px solid #ccc;'>\n" +
                "<td>\n" +
                "<span style=\"font-family:Microsoft YaHei;\">提醒：您可以通过企业微信审批此流程，此邮件由系统发出，请勿恢复；如有问题，请联系总部IT综合服务6677（021-38600677）</span>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n";
        String html = htmlTemplate
                .replace("#link#","http://10.4.157.106/seeyon/kk/ssoLogin.do?method=ssoLogin&loginName=HAX/jUZ3z/9Qq8R8+A5QCw==")
                .replace("#flowName#","上线运营事务流程")
                .replace("#taskName#","上线运营事务流程-林子晨-2021.10.11")
                .replace("#userName#","林子晨")
                .replace("#deapName#","致远")
                .replace("#endTime#","2021-12-12");
        emailService.sendHtmlMail("lujie_dev@qq.com","激活邮件",html);
    }

    @Test
    public void sendAttachment(){
        emailService.sendAttachmentsMail("lujie_dev@qq.com","发送附件","这是Java体系图","F:/图片/爱旅行.jpg");
    }

    @Test
    public void sendMeetingInvitationEmail(){
        emailService.sendMeetingInvitationEmail("lujie_dev@outlook.com","lujie_dev@outlook.com","lujie_dev@outlook.com",
                "lujie_dev@qq.com","个人会议","2022-06-16 12:00:00","2022-06-16 13:00:00","地点",
                "方案讨论会议","30","请准时参加");
    }
}