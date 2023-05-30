package com.slavynskyi.voiptestapp.job;

import com.slavynskyi.voiptestapp.model.Conversation;
import com.slavynskyi.voiptestapp.model.Operator;
import com.slavynskyi.voiptestapp.model.Status;
import com.slavynskyi.voiptestapp.storage.ConversationStorage;
import com.slavynskyi.voiptestapp.storage.OperatorStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Log4j2
@Service
@RequiredArgsConstructor
public class TimeoutCleanupJob {
    private static final int TIMEOUT_INTERVAL = 15;
    private final OperatorStorage operatorStorage;
    private final ConversationStorage conversationStorage;

    @Scheduled(fixedDelay = 200)
    public void run() {
        for (Operator operator: operatorStorage.getOperators()) {
            for (Conversation conversation: operator.getStorage()) {
                if (conversation != null) {
                    var now = ZonedDateTime.now();
                    var assignedDate = conversation.getAssignedDate().toInstant().atZone(ZoneId.systemDefault());
                    long diff = ChronoUnit.SECONDS.between(assignedDate, now);
                    if (!Status.PENDING.equals(conversation.getStatus())) {
                        return;
                    }
                    if (diff >= TIMEOUT_INTERVAL) {
                        log.info("Operator {} {} has not accepted the conversation {} for {} seconds. Return conversation to the main queue", operator.getId(), operator.getUsername(), conversation.getId(), TIMEOUT_INTERVAL);
                        conversation.setStatus(Status.NEW);
                        conversationStorage.getQueue().offer(operator.getStorage().poll());
                    }
                }
            }
        }
    }
}
