package com.acv.cloud.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author:@leo.
 */
@Controller
public class homeController {
    @RequestMapping("/home")
    public Object logout(String userName) {
        return "privacyPolicyForACV";
    }

    @RequestMapping("/")
    @ResponseBody
    public String init(){
        return "atcc-serviceplatform-oauth2adapter is runing";
    }

}
