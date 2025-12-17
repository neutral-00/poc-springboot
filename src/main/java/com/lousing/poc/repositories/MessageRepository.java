package com.lousing.poc.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {

    public String fetchMessage() {
        return "Message fetched from repository";
    }
}
