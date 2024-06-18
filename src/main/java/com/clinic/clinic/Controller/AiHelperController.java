package com.clinic.clinic.Controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/ai-helper")
@CrossOrigin
public class AiHelperController {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @GetMapping
    public String getAllDoctorsByUser(Model model) {
        model.addAttribute("title", "Ai helper");
        return "ai-helper";
    }

    @PostMapping("/chat")
    @ResponseBody
    public String getChatResponse(@RequestBody String userMessage) {
        String apiUrl = "https://api.openai.com/v1/engines/davinci-codex/completions"; // Correct endpoint

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(apiUrl);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + openaiApiKey);

            JSONObject json = new JSONObject();
            json.put("prompt", userMessage);
            json.put("max_tokens", 150);

            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    return "Error: OpenAI API request failed with status code " + statusCode;
                }

                String responseBody = EntityUtils.toString(response.getEntity());

                if (responseBody.isEmpty()) {
                    return "Error: Empty response from OpenAI API";
                }

                JSONObject responseJson = new JSONObject(responseBody);
                if (responseJson.has("choices") && responseJson.getJSONArray("choices").length() > 0) {
                    return responseJson.getJSONArray("choices").getJSONObject(0).getString("text").trim();
                } else {
                    return "Error: Unexpected response structure from OpenAI API";
                }
            }
        } catch (IOException e) {
            return "Error: Unable to get response from OpenAI API due to IOException";
        } catch (Exception e) {
            return "Error: An unexpected error occurred";
        }
    }
}
