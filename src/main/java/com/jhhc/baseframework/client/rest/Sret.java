package com.jhhc.baseframework.client.rest;

/**
 * 该Sret和服务端的Sret是一样的格式
 * 
 * @author yecq
 */
public class Sret {

    private String status;      // 返回状态，可为 ok、fail、error三者之一
    private String message;     // 返回字符串信息 
    private Object data;        // 返回数据

    public Sret() {
        this.status = "ok";
        this.message = "ok";
        this.data = null;
    }

    public boolean isOk() {
        return this.status.equals("ok");
    }

    public void setOk() {
        this.status = "ok";
        this.message = "ok";
    }

    public void setOk(String msg) {
        this.status = "ok";
        this.message = msg;
    }

    public boolean isFail() {
        return this.status.equals("fail");
    }

    public void setFail() {
        this.status = "fail";
        this.message = "fail";
    }

    public void setFail(String msg) {
        this.status = "fail";
        this.message = msg;
    }

    public boolean isError() {
        return this.status.equals("error");
    }

    public void setError() {
        this.status = "error";
        this.message = "error";
    }

    public void setError(String msg) {
        this.status = "error";
        this.message = msg;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }
}
