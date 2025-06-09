package com.demo.awsdemosdk.manager;

import com.demo.awsdemosdk.models.Message;
import com.demo.awsdemosdk.models.request.AcknowledgeMessageRequest;
import com.demo.awsdemosdk.models.request.PollMessageRequest;
import com.demo.awsdemosdk.models.request.SendMessageRequest;
import com.demo.awsdemosdk.models.response.LoginResponse;
import com.demo.awsdemosdk.models.SqsQueue;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class SQSManager implements QueueManager {

    private final SqsQueue sqsQueue;
    private final QueueHelper queueHelper;

    public SQSManager(SqsQueue sqsQueue) {
        this.sqsQueue = sqsQueue;
        this.queueHelper = new QueueHelper();
    }

    @Override
    public boolean sendMessage(String message) {
        return sendMessages(Collections.singletonList(message));
    }

    @Override
    public boolean sendMessages(List<String> messages) {
        String token = getToken();
        SendMessageRequest request = SendMessageRequest.builder()
                .messages(messages)
                .queueName(sqsQueue.getQueueName())
                .build();
        return queueHelper.sendMessages(request, token, sqsQueue);
    }

    @Override
    public List<Message> pollMessages(int limit) {
        String token = getToken();
        PollMessageRequest request = PollMessageRequest.builder()
                .queueName(sqsQueue.getQueueName())
                .limit(limit)
                .build();
        return queueHelper.pollMessages(request, token, sqsQueue);
    }

    @Override
    public boolean deleteMessage(Message message) {
        return deleteMessages(Collections.singletonList(message));
    }

    @Override
    public boolean deleteMessages(List<Message> messages) {
        if(CollectionUtils.isEmpty(messages)){
            return false;
        }
        String token = getToken();
        AcknowledgeMessageRequest request = AcknowledgeMessageRequest.builder()
                .queueName(sqsQueue.getQueueName())
                .messageIds(messages.stream().map(Message::getMessageId).toList())
                .build();
        return queueHelper.acknowledgeMessages(request, token, sqsQueue);
    }

    private String getToken() {
        LoginResponse response = queueHelper.getLoginResponse(sqsQueue);
        return response.getAccessToken();
    }
}
