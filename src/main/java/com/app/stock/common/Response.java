package com.app.stock.common;

/**
 * 数据统一格式返回类
 * @param <T>
 */
public class Response<T> {
    private T data;
    private String reason;
    private int error_code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static Response ok(Object object, String reason,int errorCode){
        Response response = new Response();
        response.setData(object == null?"{}":object);
        response.setReason(reason);
        response.setError_code(errorCode);
        return response;
    }

    public static Response ok(String reason){
        // 控制所有的返回状态码
        if(reason.contains("不能为空") || !reason.contains("成功")){
            return ok(null,reason,1);
        }else{
            return ok(null, reason, 0);
        }
    }

    public static Response ok(Object object, String reason){
        return ok(object,reason,0);
    }
}
