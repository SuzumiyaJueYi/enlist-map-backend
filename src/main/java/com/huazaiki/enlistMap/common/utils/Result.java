package com.huazaiki.enlistMap.common.utils;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

/**
 * 工具类 Result，统一返回结果封装。
 * 提供了多种静态方法便于快速构建常见的响应结果。
 * @complete 2025/01/02 00:17
 */
public record Result<T>(int code, T data, String message) {

    /**
     * 返回一个成功的响应结果（无数据）。
     *
     * @return 一个 `Result` 对象，状态码为 200，数据为 null，消息为 "请求成功"。
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 返回一个成功的响应结果（带数据）。
     *
     * @param data 响应的具体数据。
     * @return 一个 `Result` 对象，状态码为 200，包含传入的数据，消息为 "请求成功"。
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, data, "请求成功");
    }

    /**
     * 返回一个成功的响应结果（带数据和自定义消息）。
     *
     * @param data    响应的具体数据。
     * @param message 自定义的成功消息。
     * @return 一个 `Result` 对象，状态码为 200，包含传入的数据和消息。
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(200, data, message);
    }

    /**
     * 返回一个失败的响应结果。
     *
     * @param code 自定义的状态码，表示失败的具体原因。
     * @param msg  失败的描述信息。
     * @return 一个 `Result` 对象，包含自定义状态码和消息，数据为 null。
     */
    public static <T> Result<T> failure(int code, String msg) {
        return new Result<>(code, null, msg);
    }

    /**
     * 返回一个未授权的响应结果。
     *
     * @param msg 未授权的描述信息。
     * @return 一个 `Result` 对象，状态码为 401，数据为 null，包含未授权信息。
     */
    public static <T> Result<T> unauthorized(String msg) {
        return failure(401, msg);
    }

    /**
     * 返回一个禁止访问的响应结果。
     *
     * @param msg 禁止访问的描述信息。
     * @return 一个 `Result` 对象，状态码为 403，数据为 null，包含禁止访问的信息。
     */
    public static <T> Result<T> forbidden(String msg) {
        return failure(403, msg);
    }

    /**
     * 将 `Result` 对象序列化为 JSON 字符串。
     *
     * @return JSON 格式的字符串表示当前 `Result` 对象。
     */
    public String asJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}