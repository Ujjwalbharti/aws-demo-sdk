package com.demo.awsdemosdk.models.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AcknowledgeMessageRequest {
    private String queueName;
    private List<String> messageIds;
}
