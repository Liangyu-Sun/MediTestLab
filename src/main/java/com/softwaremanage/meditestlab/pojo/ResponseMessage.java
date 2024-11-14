package com.softwaremanage.meditestlab.pojo;

public class ResponseMessage<T> {
    private Integer code;
    private String message;
    private T data;
    public ResponseMessage(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;

    }
    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<T>(200, "success", data);
    }
    public static <T> ResponseMessage<T> success() {
        return new ResponseMessage<T>(200, "success", null);
    }
    public static <T> ResponseMessage<T> error(T data) {
        return new ResponseMessage<T>(0, "error", data);
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

