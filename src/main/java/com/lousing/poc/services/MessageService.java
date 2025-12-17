package com.lousing.poc.services;

import com.lousing.poc.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public String processMessage() {
        return messageRepository.fetchMessage() + " | processed by service";
    }
}
