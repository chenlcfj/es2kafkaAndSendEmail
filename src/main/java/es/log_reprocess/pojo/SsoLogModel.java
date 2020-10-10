package es.log_reprocess.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenlc
 * @date 2020/9/14
 */
@Getter
@Setter
public class SsoLogModel {
    private String id;

    private String action;

    private String who;

    private String time;

    private String userAgent;

    private String clientBrowser;

    private String clientOs;

    private String method;

    private String url;

    private String clientIp;

    private String serverIp;

    private String result;

    private String resultDetail;

    private String application;

    private String userUinqueId;

    private String userId;

    private String userName;

    private String authMethod;

    private String serviceId;

    private String timeUsed;

    private String receiveTime;

    private String hostName;

    @Override
    public String toString() {
        return "Id=" + "\"" + id + "\" " +
                "Action=" + "\"" + action + "\" " +
                "Who=" + "\"" + who + "\" " +
                "Time=" + "\"" + time + "\" " +
                "UserAgent=" + "\"" + userAgent + "\" " +
                "ClientBrowser=" + "\"" + clientBrowser + "\" " +
                "ClientOs=" + "\"" + clientOs + "\" " +
                "Method=" + "\"" + method + "\" " +
                "Url=" + "\"" + url + "\" " +
                "clientIp=" + "\"" + clientIp + "\" " +
                "serverIp=" + "\"" + serverIp + "\" " +
                "result=" + "\"" + result + "\" " +
                "resultDetail=" + "\"" + resultDetail + "\" " +
                "application=" + "\"" + application + "\" " +
                "userUinqueId=" + "\"" + userUinqueId + "\" " +
                "userId=" + "\"" + userId + "\" " +
                "userName=" + "\"" + userName + "\" " +
                "authMethod=" + "\"" + authMethod + "\" " +
                "serviceId=" + "\"" + serviceId + "\" " +
                "timeUsed=" + "\"" + timeUsed + "\" " +
                "receiveTime=" + "\"" + receiveTime + "\" " +
                "hostName=" + "\"" + hostName + "\"";
    }

}
