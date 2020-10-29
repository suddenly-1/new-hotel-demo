package com.suddenly.responseResult;

public class ResponseResult<T> {

    private Integer code;

    private String message;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<T> returnSuccess() {
        ResponseResult<T> result = getSuccessResult(ResultEnum.SUCCESS);
        return result;
    }

    public static <T> ResponseResult<T> returnSuccess(T data) {
        ResponseResult<T> result = getSuccessResult(ResultEnum.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> ResponseResult<T> returnFailure() {
        ResponseResult<T> result = getSuccessResult(ResultEnum.FAILURE);
        return result;
    }

    public static <T> ResponseResult<T> returnFailure(T data) {
        ResponseResult<T> result = getSuccessResult(ResultEnum.FAILURE);
        result.setData(data);
        return result;
    }

    public static <T> ResponseResult<T> returnFailure(IResultEnum resultEnum) {
        ResponseResult<T> result = getSuccessResult(resultEnum);
        return result;
    }

    public static <T> ResponseResult<T> returnFailure(T data, IResultEnum resultEnum) {
        ResponseResult<T> result = getSuccessResult(resultEnum);
        result.setData(data);
        return result;
    }


    private static <T> ResponseResult<T> getSuccessResult(IResultEnum resultEnum) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMessage());
        return result;
    }


}
