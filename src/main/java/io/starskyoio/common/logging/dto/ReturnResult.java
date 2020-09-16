package io.starskyoio.common.logging.dto;

import lombok.Data;

@Data
public class ReturnResult<T> {
    private Integer status;
    private String errCode = "";
    private String message;
    private T data;

    public ReturnResult() {
        this.status = 200;
        this.message = "success";
    }

    public ReturnResult(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ReturnResult(int status, String errCode, String message, T data) {
        this.status = status;
        this.errCode = errCode;
        this.message = message;
        this.data = data;
    }
}

