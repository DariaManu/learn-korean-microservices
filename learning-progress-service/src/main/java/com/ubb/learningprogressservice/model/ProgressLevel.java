package com.ubb.learningprogressservice.model;

public enum ProgressLevel {
    BEGINNER(0),
    INTERMEDIATE(1),
    ADVANCED(2);

    private final int order;
    private ProgressLevel(final int order) {
        this.order = order;
    }

    public boolean isHigherThan(ProgressLevel other) {
        return this.order >= other.order;
    }
}
