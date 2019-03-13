package com.acv.cloud.utils;

public class HttpResult {
        /**
         * 状态码
         */
        private Integer status;
        /**
         * 返回数据
         */
        private String msg;

        public HttpResult() {
        }

        public HttpResult(Integer status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String data) {
            this.msg = msg;
        }

}
