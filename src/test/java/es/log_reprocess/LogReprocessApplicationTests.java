package es.log_reprocess;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@Service
public class LogReprocessApplicationTests {
    @Autowired
    private EmailService emailService;

//    public LogReprocessApplicationTests(EmailService emailService) {
//        this.emailService = emailService;
//    }

    @Test
    public void sendSimpleMailTest() throws IOException {
        String[] to = {"2494371797@qq.com"};
        String subject = "主题: 测试";
        String content = "内容: 测试测试";
        String[] cc = {"2494371797@qq.com", "chenlcfff@koal.com"};
        try {
            emailService.sendSimpleMail(to, cc, subject, content);
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    @Test
    public void test() {
        String aa = "    403908086@qq.com,    chenlc@koal.com,    , ";
        String[] split = aa.split(",");
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        String s = "web@xauat.edu.cn";
        char c = s.charAt(0);
        System.out.println(c);
        boolean matches = Pattern.matches(regex, s);
        System.out.println("matches: " + matches);
        System.out.println(split.length);
        for (String s1 : split) {
            String trim = s1.trim();
            if (!trim.equals("")) {
                System.out.println(trim);
            }
        }
        System.out.println("split " + Arrays.toString(split));
        System.out.println(Arrays.toString(removeBlank(split)));
    }

    private String[] removeBlank(String[] oldArr) {
        List<String> list = new ArrayList<>();
        for (String arr : oldArr) {
            String trim = arr.trim();
            if (!"".equals(trim)) {
                list.add(trim);
            }
        }
        String[] newArray = list.toArray(new String[list.size()]);
        return newArray;
    }

    @Test
    public void testJavaMail() throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");//指定邮件的发送服务器地址
        props.put("mail.smtp.auth", "true");//服务器是否要验证用户的身份信息

        Session session = Session.getInstance(props);//得到Session
        session.setDebug(true);//代表启用debug模式，可以在控制台输出smtp协议应答的过程


        //创建一个MimeMessage格式的邮件
        MimeMessage message = new MimeMessage(session);

        //设置发送者
        Address fromAddress = new InternetAddress("clc_xkd123@163.com");//邮件地址
        message.setFrom(fromAddress);//设置发送的邮件地址
        //设置接收者
        Address toAddress = new InternetAddress("403908086@qq.com");//要接收邮件的邮箱
        message.setRecipient(MimeMessage.RecipientType.TO, toAddress);//设置接收者的地址

        //设置邮件的主题
        message.setSubject("邮件发送测试");
        //设置邮件的内容
        message.setText("陈立朝邮件发送测试");
        //保存邮件
        message.saveChanges();


        //得到发送邮件的服务器(这里用的是smtp服务器)
        Transport transport = session.getTransport("smtp");

        //发送者的账号连接到smtp服务器上  @163.com可以不写
        transport.connect("smtp.163.com", "clc_xkd123@163.com", "DOOATPXMIUMVFDRK");
        //发送信息
        transport.sendMessage(message, message.getAllRecipients());
        //关闭服务器通道
        transport.close();
    }


   /* @Override
    public String toString() {
        return "id=" + "\"" + id + "\" " +
                "action=" + "\"" + action + "\" " +
                "who=" + "\"" + who + "\" " +
                "time=" + "\"" + time + "\" " +
                "useragent=" + "\"" + userAgent + "\" " +
                "client_browser=" + "\"" + clientBrowser + "\" " +
                "client_os=" + "\"" + clientOs + "\" " +
                "method=" + "\"" + method + "\" " +
                "url=" + "\"" + url + "\" " +
                "client_ip=" + "\"" + clientIp + "\" " +
                "server_ip=" + "\"" + serverIp + "\" " +
                "result=" + "\"" + result + "\" " +
                "result_detail=" + "\"" + resultDetail + "\" " +
                "application=" + "\"" + application + "\" " +
                "user_uinque_id=" + "\"" + userUinqueId + "\" " +
                "user_id=" + "\"" + userId + "\" " +
                "user_name=" + "\"" + userName + "\" " +
                "auth_method=" + "\"" + authMethod + "\" " +
                "service_id=" + "\"" + serviceId + "\" " +
                "time_used=" + "\"" + timeUsed + "\" " +
                "receive_time=" + "\"" + receiveTime + "\" " +
                "host_name=" + "\"" + hostName + "\"";
    }*/
}
