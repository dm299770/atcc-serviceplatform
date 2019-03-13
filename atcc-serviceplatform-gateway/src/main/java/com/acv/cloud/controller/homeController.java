package com.acv.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class homeController {
    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "acms-platform-gateway is running ";
    }


}
