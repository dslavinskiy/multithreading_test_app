package com.slavynskyi.voiptestapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Date;

public class Conversation implements Comparable<Conversation> {
    private static final Comparator<Conversation> COMPARATOR = Comparator.comparing(Conversation::getCreatedDate, Comparator.nullsLast(Comparator.naturalOrder()))
            .thenComparing(Conversation::getId, Comparator.nullsLast(Comparator.naturalOrder()));

    @Getter
    private final int id;
    @Getter
    private final Date createdDate;

    @Getter
    @Setter
    private Date assignedDate;

    @Getter
    @Setter
    private Operator operator;

    @Getter
    @Setter
    private Status status;

    public Conversation(int id, Date createdDate, Status status) {
        this.id = id;
        this.createdDate = createdDate;
        this.status = status;
    }

    @Override
    public int compareTo(Conversation other) {
        return COMPARATOR.compare(this, other);
    }
}


