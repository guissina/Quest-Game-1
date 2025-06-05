package com.quest.engine.state;

public class AbilityState {
    private final Boolean active;

    public AbilityState(Boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
