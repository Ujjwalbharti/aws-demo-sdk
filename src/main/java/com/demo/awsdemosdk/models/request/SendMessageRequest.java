package com.demo.awsdemosdk.models.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SendMessageRequest {
    private String queueName;
    private List<String> messages;
}
