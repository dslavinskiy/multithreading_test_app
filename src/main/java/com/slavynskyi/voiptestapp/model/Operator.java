package com.slavynskyi.voiptestapp.model;

import lombok.Getter;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Operator {
    private static final int CONVERSATION_LIMIT = 4;

    @Getter
    private final int id;

    @Getter
    private final String username;

    @Getter
    private final Queue<Conversation> storage;

    public Operator(int id, String username) {
        this.id = id;
        this.username = username;
        this.storage = new ArrayBlockingQueue<>(CONVERSATION_LIMIT);
    }

    public boolean isReady() {
        return storage.size() < CONVERSATION_LIMIT;
    }
}
