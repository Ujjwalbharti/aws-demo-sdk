package com.demo.awsdemosdk.manager;

import com.demo.awsdemosdk.config.RestTemplateConfig;
import com.demo.awsdemosdk.models.Message;
import com.demo.awsdemosdk.models.request.AcknowledgeMessageRequest;
import com.demo.awsdemosdk.models.request.LogInRequest;
import com.demo.awsdemosdk.models.request.PollMessageRequest;
import com.demo.awsdemosdk.models.request.SendMessageRequest;
import com.demo.awsdemosdk.models.response.LoginResponse;
import com.demo.awsdemosdk.models.SqsQueue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

class QueueHelper {
    private final RestTemplate restTemplate;

    public QueueHelper() {
        this.restTemplate = RestTemplateConfig.getRestTemplate();
    }

    public LoginResponse getLoginResponse(SqsQueue queue) {
        String loginUrl = "http://" + queue.getHost() + ":" + queue.getPort() + "/v1/sqs/login";
        LogInRequest loginRequest = LogInRequest.builder()
                .username(queue.getUsername())
                .password(queue.getPassword())
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LogInRequest> requestEntity = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> responseEntity = restTemplate.exchange(
                loginUrl,
                HttpMethod.POST,
                requestEntity,
                LoginResponse.class
        );
        return responseEntity.getBody();
    }

    public boolean sendMessages(SendMessageRequest sendMessageRequest, String token, SqsQueue queue) {
        String loginUrl = "http://" + queue.getHost() + ":" + queue.getPort() + "/v1/sqs/send-messages";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<SendMessageRequest> requestEntity = new HttpEntity<>(sendMessageRequest, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                loginUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return responseEntity.getStatusCode().is2xxSuccessful();
    }

    public List<Message> acknowledgeMessages(PollMessageRequest request, String token, SqsQueue queue) {
        String loginUrl = "http://" + queue.getHost() + ":" + queue.getPort() + "/v1/sqs/poll-messages";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<PollMessageRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<List<Message>> responseEntity = restTemplate.exchange(
                loginUrl,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return responseEntity.getBody();
    }

    public boolean acknowledgeMessages(AcknowledgeMessageRequest request, String token, SqsQueue queue) {
        String loginUrl = "http://" + queue.getHost() + ":" + queue.getPort() + "/v1/sqs/acknowledge-messages";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<AcknowledgeMessageRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                loginUrl,
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );
        return responseEntity.getStatusCode().is2xxSuccessful();
    }
}
