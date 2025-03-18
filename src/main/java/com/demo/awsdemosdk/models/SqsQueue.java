package com.demo.awsdemosdk.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SqsQueue {
    private String queueName;
    private String host;
    private int port;
    private String username;
    private String password;
}
