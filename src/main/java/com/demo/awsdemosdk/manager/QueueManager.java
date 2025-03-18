package com.demo.awsdemosdk.manager;

import com.demo.awsdemosdk.models.Message;

import java.util.List;

public interface QueueManager {
    boolean sendMessage(String message);
    boolean sendMessages(List<String> messages);

    /**
     * Poll messages
     * @param limit
     * @return List<Message>
     */
    List<Message> pollMessages(int limit);
    boolean deleteMessage(Message message);
    boolean deleteMessages(List<Message> messages);
}
