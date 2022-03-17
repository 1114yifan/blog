package com.yf.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description: 日志切面
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    // 定义切面
    @Pointcut("execution(* com.yf.blog.controller.*.*(..))") // 切入点
    public void log(){}

    // 切面方法之前
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        // 类名 . 方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] objects = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, objects);
//        logger.info("--------切面方法之前执行---------");
        logger.info("Request : {}",requestLog);
    }

    // 切面方法之后
    @After("log()")
    public void doAfter(){
//        logger.info("--------切面方法之后执行---------");
    }

    // 方法返回
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){   // result 返回的结果
        logger.info("Result : {}",result);
    }


    /**
     * 定义返回参数
     */
    private class RequestLog {
        private String url; // 请求的路径
        private String ip;  // 请求的ip
        private String classMethod; // 请求的方法
        private Object[] args;  // 请求参数

        public RequestLog() {
        }

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
