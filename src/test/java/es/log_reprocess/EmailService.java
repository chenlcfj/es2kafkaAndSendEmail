package es.log_reprocess;

public interface EmailService {

    /**
     * TODO 发送简单邮件(TEXT)
     *
     * @param to      发送地址
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String[] to, String[] cc, String subject, String content);

    /**
     * TODO 发送HTML邮件(页面,不能加载图片)
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * TODO 发送附件邮件(带文件)
     *
     * @param filePath 文件存放地址
     */
    void sendFileMail(String to, String subject, String content, String filePath);

    /**
     * TODO 发送静态资源邮件
     *
     * @param resourcePath 路径
     * @param resourceId   ID
     */
    void sendStaticResourceMail(String to, String subject, String content, String resourcePath, String resourceId);
}