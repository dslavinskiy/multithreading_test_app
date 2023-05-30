package com.slavynskyi.voiptestapp.job;

import com.slavynskyi.voiptestapp.model.Conversation;
import com.slavynskyi.voiptestapp.model.Operator;
import com.slavynskyi.voiptestapp.storage.OperatorStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Log4j2
@Service
@RequiredArgsConstructor
public class FakeClientJob {
    private final Random random = new Random();
    private final OperatorStorage operatorStorage;

    @Scheduled(fixedDelay = 10000)
    public void run() {
        for (Operator operator: operatorStorage.getOperators()) {
            for (Conversation conversation: operator.getStorage()) {
                if (random.nextInt(100) % 10 == 0) {
                    log.info("Conversation {} has been closed by client", conversation.getId());
                    operator.getStorage().remove(conversation);
                }
            }
        }
    }
}
