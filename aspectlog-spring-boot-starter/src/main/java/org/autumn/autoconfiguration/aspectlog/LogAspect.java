package org.autumn.autoconfiguration.aspectlog;

import com.alibaba.fastjson.JSON;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * 日志切面
 *
 * @author : SunLin
 * @date : 2020/8/19
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    private static final String COMMA = ",";

    private final AspectLogProperties aspectLogProperties;

    public LogAspect(AspectLogProperties aspectLogProperties) {
        this.aspectLogProperties = aspectLogProperties;
    }


    @Around("@annotation(org.autumn.autoconfiguration.aspectlog.AspectLog) ")
    public Object deBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        before(joinPoint);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object object;

        if (aspectLogProperties.getPrintErrorLog()) {
            object = printErrorLogProcess(joinPoint);
        } else {
            object = joinPoint.proceed();
        }

        stopWatch.stop();

        after(object, stopWatch.getTotalTimeMillis());

        return object;
    }

    public Object printErrorLogProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable t) {
            LOGGER.error("异常信息【{}】：", t.getMessage(), t);
            throw t;
        }
    }

    private void before(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> names = request.getHeaderNames();

        List<String> header = new ArrayList<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            header.add(name + " = " + request.getHeader(name));
        }

        if (LOGGER.isInfoEnabled()) {
            String message = "\n【{}】请求相关信息：\n【请求头信息】->【{}】,\n【请求方法】->【{}】,\n【请求参数】->【{}】";
            //入参长度截取
            String argsStr = interceptFixedLength(Arrays.toString(joinPoint.getArgs()));
            LOGGER.info(message, aspectLogProperties.getApplicationName(), StringUtils.join(header, COMMA), joinPoint.getSignature(), argsStr);
        }
    }

    private void after(Object object, long totalTimeMillis) {
        if (null != object && LOGGER.isInfoEnabled()) {
            String message = "\n【{}】执行情况：\n执行时间为：【{}毫秒】\n返回值为：【{}】";
            LOGGER.info(message, aspectLogProperties.getApplicationName(), totalTimeMillis, object instanceof ModelAndView ? interceptFixedLength(object.toString()) : interceptFixedLength(JSON.toJSONString(object)));
        }
    }

    private String interceptFixedLength(String argsStr) {
        if (argsStr.length() > aspectLogProperties.getArgsStrLength()) {
            argsStr = argsStr.substring(0, aspectLogProperties.getArgsStrLength());
        }
        return argsStr;
    }
}
