package org.autumn.autoconfiguration.aspectlog;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 切面日志配置
 *
 * @author : SunLin
 * @date : 2020/8/20
 */
@ConfigurationProperties("log")
public class AspectLogProperties {

    private String applicationName = "";

    private Integer argsStrLength = 5000;

    private Boolean printErrorLog = Boolean.FALSE;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getArgsStrLength() {
        return argsStrLength;
    }

    public void setArgsStrLength(Integer argsStrLength) {
        this.argsStrLength = argsStrLength;
    }

    public Boolean getPrintErrorLog() {
        return printErrorLog;
    }

    public void setPrintErrorLog(Boolean printErrorLog) {
        this.printErrorLog = printErrorLog;
    }
}
