package com.slavynskyi.voiptestapp.service;

import com.slavynskyi.voiptestapp.model.Operator;

import java.util.List;

public interface OperatorService {
    List<Operator> getAllOperators();
    Operator getById(int id);
    void approveById(int operatorId, int conversationId);
    void declineById(int operatorId, int conversationId);
}
