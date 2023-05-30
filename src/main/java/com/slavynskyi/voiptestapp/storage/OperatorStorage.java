package com.slavynskyi.voiptestapp.storage;

import com.slavynskyi.voiptestapp.model.Operator;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperatorStorage {
    @Getter
    private final List<Operator> operators = new ArrayList<>();

    // Init with some data
    public OperatorStorage() {
        operators.add(new Operator(1, "Denys"));
        operators.add(new Operator(2, "Egor"));
        operators.add(new Operator(3, "Evdokyia"));
    }
}
