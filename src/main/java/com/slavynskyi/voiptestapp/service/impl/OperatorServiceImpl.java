package com.slavynskyi.voiptestapp.service.impl;

import com.slavynskyi.voiptestapp.model.Conversation;
import com.slavynskyi.voiptestapp.model.Operator;
import com.slavynskyi.voiptestapp.model.Status;
import com.slavynskyi.voiptestapp.service.OperatorService;
import com.slavynskyi.voiptestapp.storage.ConversationStorage;
import com.slavynskyi.voiptestapp.storage.OperatorStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {
    private final OperatorStorage operatorStorage;
    private final ConversationStorage conversationStorage;

    @Override
    public List<Operator> getAllOperators() {
        return operatorStorage.getOperators();
    }

    @Override
    public Operator getById(int id) {
        return operatorStorage.getOperators().stream().filter(operator -> operator.getId() == id)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void approveById(int operatorId, int conversationId) {
        var operator = getById(operatorId);
        var conversation = getConversationById(operator, conversationId);
        conversation.setStatus(Status.PROCESSING);
        conversation.setOperator(operator);
    }

    @Override
    public void declineById(int operatorId, int conversationId) {
        var operator= getById(operatorId);
        var conversation = getConversationById(operator, conversationId);
        conversation.setStatus(Status.NEW);
        operator.getStorage().remove(conversation);
        conversationStorage.getQueue().add(conversation);
    }

    private Conversation getConversationById(Operator operator, int conversationId) {
        return operator.getStorage().stream().filter(conv -> conv.getId() == conversationId)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
