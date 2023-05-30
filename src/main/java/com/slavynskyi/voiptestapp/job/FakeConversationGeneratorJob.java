package com.slavynskyi.voiptestapp.job;

import com.slavynskyi.voiptestapp.model.Conversation;
import com.slavynskyi.voiptestapp.model.Status;
import com.slavynskyi.voiptestapp.storage.ConversationStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log4j2
@Service
@RequiredArgsConstructor
public class FakeConversationGeneratorJob {
    private final ConversationStorage conversationStorage;
    private int counter = 0;

    @Scheduled(fixedDelay = 5000)
    public void run() {
        conversationStorage.getQueue().offer(new Conversation(counter++, new Date(), Status.NEW));
    }
}
