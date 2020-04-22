package com.snake19870227.stiger.admin.endpoint;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/22
 */
@RestController
public class IndexEndpointController {

    @GetMapping(path = "/")
    public String root() {
        return "hello";
    }

    @GetMapping(path = "/hello")
    public Object hello() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
