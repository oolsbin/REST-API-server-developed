package com.example.demo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomErrorCode {

    OVER_FILE_SIZE(400, "804", "파일 사이즈는 320*240 이하여야만 합니다."),
    INTERNAL_SERVER_ERROR(500, "500", "서버 에러");

    private int status;
    private final String code;
    private final String message;

    CustomErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
