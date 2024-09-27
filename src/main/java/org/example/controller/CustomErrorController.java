package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public void error(HttpServletResponse response) throws IOException {
        response.sendRedirect("/processerror");
    }
}
