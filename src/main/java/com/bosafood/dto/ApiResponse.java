package com.bosafood.dto;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private ApiError error;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.success = true;
        this.data = data;
    }

    public ApiResponse(String message) {
        this.success = true;
        this.message = message;
    }

    public ApiResponse(T data, String message) {
        this.success = true;
        this.data = data;
        this.message = message;
    }

    public ApiResponse(ApiError error) {
        this.success = false;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public ApiError getError() {
        return error;
    }

    public void setError(ApiError error) {
        this.error = error;
    }
} 