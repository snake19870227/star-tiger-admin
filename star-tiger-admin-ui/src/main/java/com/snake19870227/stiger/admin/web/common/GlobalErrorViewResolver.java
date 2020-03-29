package com.snake19870227.stiger.admin.web.common;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import com.snake19870227.stiger.web.exception.PostWebErrorHandler;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/21
 */
public class GlobalErrorViewResolver extends DefaultErrorViewResolver implements ErrorViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalErrorViewResolver.class);

    private PostWebErrorHandler postWebErrorHandler;

    /**
     * Create a new {@link DefaultErrorViewResolver} instance.
     *
     * @param applicationContext the source application context
     * @param resourceProperties resource properties
     */
    public GlobalErrorViewResolver(ObjectProvider<PostWebErrorHandler> postExceptionHandlerProvider, ApplicationContext applicationContext, ResourceProperties resourceProperties) {
        super(applicationContext, resourceProperties);
        this.postWebErrorHandler = postExceptionHandlerProvider.getIfAvailable();
        logger.debug("创建4xx错误页面处理器");
    }

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {

        ModelAndView mv = super.resolveErrorView(request, status, model);

        if (postWebErrorHandler != null) {
            postWebErrorHandler.errorPageHandler(request, status, mv);
        }

        return mv;
    }
}
