package com.md.drone.common;

/**
 * 统一 REST 响应体，参考 RuoYi {@code AjaxResult} 结构。
 *
 * @param <T> 业务数据类型
 */
public final class AjaxResult<T> {

    private final int code;
    private final String msg;
    private final T data;

    private AjaxResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功响应，携带数据。
     *
     * @param data 业务数据
     * @param <T>  数据类型
     * @return 响应体
     */
    public static <T> AjaxResult<T> success(T data) {
        return new AjaxResult<T>(200, "操作成功", data);
    }

    /**
     * 成功响应，无数据。
     *
     * @return 响应体
     */
    public static AjaxResult<Void> success() {
        return new AjaxResult<Void>(200, "操作成功", null);
    }

    /**
     * 失败响应（默认 500）。
     *
     * @param msg 错误信息
     * @return 响应体
     */
    public static AjaxResult<Void> error(String msg) {
        return fail(500, msg);
    }

    /**
     * 失败响应，指定业务码。
     *
     * @param code 业务码
     * @param msg  错误信息
     * @param <T>  数据类型
     * @return 响应体
     */
    public static <T> AjaxResult<T> fail(int code, String msg) {
        return new AjaxResult<T>(code, msg, null);
    }

    /**
     * @return 业务状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * @return 提示信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @return 业务数据
     */
    public T getData() {
        return data;
    }
}
