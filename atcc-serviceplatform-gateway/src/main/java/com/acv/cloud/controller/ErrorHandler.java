package com.acv.cloud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorHandler {
    @GetMapping(value = "/error")
    public ResponseEntity<ErrorBean> error(HttpServletRequest request) {
        String message = request.getAttribute("javax.servlet.error.message").toString();
        ErrorBean errorBean = new ErrorBean();
        errorBean.setMsg("网关内部错误");
        errorBean.setStatus(500);
        return new ResponseEntity<>(errorBean, HttpStatus.BAD_GATEWAY);
    }

    private static class ErrorBean {
        private String msg;

        private int status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

}
