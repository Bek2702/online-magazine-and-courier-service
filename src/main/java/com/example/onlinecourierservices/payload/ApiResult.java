package com.example.onlinecourierservices.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> implements Serializable {
    private final boolean success;
    private String message;
    private T data;
    private List<ErrorData> errors;


    //RESPONSE WITH BOOLEAN (SUCCESS OR FAIL)
    private ApiResult() {
        this.success = true;
    }


    //SUCCESS RESPONSE WITH DATA
    private ApiResult(T data) {
        this();
        this.data = data;
    }

    //SUCCESS RESPONSE WITH DATA AND MESSAGE
    private ApiResult(T data, String message) {
        this();
        this.data = data;
        this.message = message;
    }

    //SUCCESS RESPONSE WITH MESSAGE
    private ApiResult(String message, boolean msg) {
        this();
        this.message = message;
    }

    //ERROR RESPONSE WITH MESSAGE AND ERROR CODE
    private ApiResult(String errorMsg, Integer errorCode) {
        this.success = false;
        this.errors = Collections.singletonList(new ErrorData(errorMsg, errorCode));
    }

    //ERROR RESPONSE WITH ERROR DATA LIST
    private ApiResult(List<ErrorData> errors) {
        this.success = false;
        this.errors = errors;
    }

    public static <E> ApiResult<E> successResponse(E data) {
        return new ApiResult<>(data);
    }

    public static <E> ApiResult<E> successResponse(E data, String message) {
        return new ApiResult<>(data, message);
    }

    public static <E> ApiResult<E> successResponse() {
        return new ApiResult<>();
    }

    public static ApiResult<String> successResponseForMsg(String message) {
        return new ApiResult<>(message, true);
    }


    public static ApiResult<ErrorData> errorResponse(String errorMsg, Integer errorCode) {
        return new ApiResult<>(errorMsg, errorCode);
    }

    public static ApiResult<ErrorData> errorResponse(List<ErrorData> errors) {
        return new ApiResult<>(errors);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorData {
        //USERGA BORADIGAN XABAR
        private String errorMsg;

        //XATOLIK KODI
        private Integer errorCode;

        //QAYSI FIELD XATO EKANLIGI
        private String fieldName;

        public ErrorData(String errorMsg, Integer errorCode) {
            this.errorMsg = errorMsg;
            this.errorCode = errorCode;
        }
    }
}
