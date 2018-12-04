package org.tinker.snowflake.server.def.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    ApplicationContext context;

    @PostMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRemoteAddr().equals("127.0.0.1") || request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            SpringApplication.exit(context, () -> 0);
        } else {
            response.setStatus(404);
        }
    }
}
