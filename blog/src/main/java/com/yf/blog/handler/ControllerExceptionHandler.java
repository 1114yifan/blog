package com.yf.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 错误页面 全局异常处理
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    // 获取日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 异常处理
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)  // 拦截级别
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL : {}, Exception : {}",request.getRequestURI(),e);

        /**
         * 不拦截的异常
         * 参数信息:
         * e.getClass()：异常类
         * ResponseStatus.class:异常的标识码
         */
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURI());
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

}
