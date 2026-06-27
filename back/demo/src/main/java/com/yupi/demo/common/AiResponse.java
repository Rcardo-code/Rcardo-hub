package com.yupi.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI 调用的统一响应结构
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiResponse {

    private boolean success;        // 调用是否成功
    private String content;         // AI 返回的文本内容
    private Integer totalTokens;    // 消耗的 token 数（计费用）
    private long costTimeMs;        // 调用耗时（毫秒）
    private String errorMsg;        // 失败时的错误信息

    /**
     * 快速创建一个成功响应
     */
    public static AiResponse ok(String content, Integer totalTokens, long costTimeMs) {
        return new AiResponse(true, content, totalTokens, costTimeMs, null);
    }

    /**
     * 快速创建一个失败响应
     */
    public static AiResponse fail(String errorMsg) {
        return new AiResponse(false, null, 0, 0, errorMsg);
    }
}