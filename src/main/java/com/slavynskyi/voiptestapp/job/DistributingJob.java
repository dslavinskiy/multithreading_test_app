package com.slavynskyi.voiptestapp.job;

import com.slavynskyi.voiptestapp.model.Operator;
import com.slavynskyi.voiptestapp.model.Status;
import com.slavynskyi.voiptestapp.storage.ConversationStorage;
import com.slavynskyi.voiptestapp.storage.OperatorStorage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Log4j2
@Service
@AllArgsConstructor
public class DistributingJob {
    private ConversationStorage conversationStorage;
    private OperatorStorage operatorStorage;

    @Scheduled(fixedDelay = 100)
    public void run() {
        if (!conversationStorage.getQueue().isEmpty()) {
            for (Operator operator : operatorStorage.getOperators()) {
                if (operator.isReady()) {
                    var nextConversation = conversationStorage.getQueue().poll();
                    if (nextConversation != null) {
                        nextConversation.setAssignedDate(new Date());
                        nextConversation.setStatus(Status.PENDING);
                        operator.getStorage().offer(nextConversation);
                        log.info("Conversation {} has been assigned to the operator {} {}", nextConversation.getId(), operator.getId(), operator.getUsername());
                    }
                    break;
                }
            }
        }
    }
}
