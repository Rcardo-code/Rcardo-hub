package com.yupi.demo.manager;

import com.yupi.demo.common.AiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;

/**
 * AI 对话管理器 —— 封装 DeepSeek API 调用
 */
@Service
public class DemoAiManager {

    private final String apiKey;
    private final RestClient restClient;

    public DemoAiManager(@Value("${deepseek.api-key}") String apiKey) {
        this.apiKey = apiKey;
        this.restClient = RestClient.builder()
                .baseUrl("https://api.deepseek.com")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    /**
     * 单轮对话：发送消息给 DeepSeek，返回 AI 回复
     */
    @Retryable(retryFor = Exception.class, maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2))
    public AiResponse doChat(String message) {
        long start = System.currentTimeMillis();

        // 构建请求体
        Map<String, Object> requestBody = Map.of(
                "model", "deepseek-chat",
                "messages", new Object[]{
                        Map.of("role", "user", "content", message)
                },
                "stream", false
        );

        // 发 POST 请求
        Map response = restClient.post()
                .uri("/chat/completions")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        long cost = System.currentTimeMillis() - start;

        // 解析响应
        if (response == null) {
            return AiResponse.fail("AI 返回为空");
        }

        try {
            var choices = (java.util.List<Map<String, Object>>) response.get("choices");
            var usage = (Map<String, Object>) response.get("usage");

            String content = null;
            if (choices != null && !choices.isEmpty()) {
                var messageObj = (Map<String, Object>) choices.get(0).get("message");
                content = (String) messageObj.get("content");
            }

            Integer totalTokens = null;
            if (usage != null) {
                totalTokens = (Integer) usage.get("total_tokens");
            }

            return AiResponse.ok(content, totalTokens, cost);

        } catch (Exception e) {
            return AiResponse.fail("解析 AI 响应失败：" + e.getMessage());
        }
    }
}