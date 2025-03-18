package com.demo.awsdemosdk.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PollMessageRequest {
    private String queueName;
    private int limit;
}
