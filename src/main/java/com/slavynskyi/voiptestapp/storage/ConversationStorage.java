package com.slavynskyi.voiptestapp.storage;

import com.slavynskyi.voiptestapp.model.Conversation;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class ConversationStorage {
    @Getter
    private final Queue<Conversation> queue = new PriorityBlockingQueue<>();
}
