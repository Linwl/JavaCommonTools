package org.linwl.commonutils.response;

/**
 * @program: JavaCommonTools
 * @description: 返回结果类
 * @author: linwl
 * @create: 2020-08-13 14:32
 **/
public class Result<T> {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    private String message;

    private T data;

    private long count;

    public Result() {
        this.code = ERRORCODE.Success.getErrorcode();
        this.message = ERRORCODE.Success.getMessage();
    }

    public Result(String message) {
        this.code = ERRORCODE.Success.getErrorcode();
        this.message = message;
    }

    public Result(T data, long count) {
        this.code = ERRORCODE.Success.getErrorcode();
        this.message = ERRORCODE.Success.getMessage();
        this.data = data;
        this.count = count;
    }

    public Result(ERRORCODE errorcode, T data, long count) {
        this.code = errorcode.getErrorcode();
        this.message = errorcode.getMessage();
        this.data = data;
        this.count = count;
    }

    public Result(String message, T data, long count) {
        this.code = ERRORCODE.Success.getErrorcode();
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public Result(ERRORCODE errorcode) {
        this.code = errorcode.getErrorcode();
        this.message = errorcode.getMessage();
        this.data = null;
        this.count = 0;
    }

    public Result(ERRORCODE errorcode, String message) {
        this.code = errorcode.getErrorcode();
        this.message = message;
        this.count = 0;
        this.data = null;
    }

    public Result(Builder<T> builder) {
        this.code = builder.code;
        this.data = builder.data;
        this.message = builder.message;
        this.count = builder.count;
    }

    public static class Builder<T> {
        /** 错误码 */
        private int code;

        /** 提示信息 */
        private String message;

        /** 返回数据 */
        private T data;

        /** 记录数 */
        private long count;

        public Builder<T> setCode(ERRORCODE errorcode) {
            this.code = errorcode.getErrorcode();
            this.message = errorcode.getMessage();
            return this;
        }

        public Builder<T> setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> setCount(long count) {
            this.count = count;
            return this;
        }

        public Result<T> build() {
            return new Result<>(this);
        }
    }
}