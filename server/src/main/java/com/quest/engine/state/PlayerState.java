package com.quest.engine.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.quest.enums.AbilityType;
import com.quest.models.Question;

public class PlayerState {

    private final Long playerId;
    private final List<Integer> tokens;
    private final Map<AbilityType, AbilityState> abilities = new EnumMap<>(AbilityType.class);

    private Long currentTileId;
    private Question pendingQuestion;
    private Integer pendingSteps;
    private Integer correctCount;

    public PlayerState(Long playerId, List<Integer> initialTokens, Long startTileId, Integer correctCount) {
        this.playerId = playerId;
        this.tokens = new ArrayList<>(initialTokens);
        this.currentTileId = startTileId;
        this.correctCount = correctCount;
        initializeAbilities();
    }

    private void initializeAbilities() {
        for (AbilityType type : AbilityType.values()) {
            abilities.put(type, new AbilityState(false));
        }
    }

    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    public Integer getPendingSteps() {
        return pendingSteps;
    }

    public void setPendingSteps(int pendingSteps) {
        this.pendingSteps = pendingSteps;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public List<Integer> getTokens() {
        return Collections.unmodifiableList(tokens);
    }

    public void consumeTokens(int steps) {
        if (!tokens.contains(steps))
            throw new IllegalStateException("Player does not have enough tokens");
        tokens.remove((Integer) steps);
    }

    public void addToken(int token) {
        if (token <= 0)
            throw new IllegalArgumentException("Token value must be positive");
        tokens.add(token);
    }

    public void resetTokens(List<Integer> initialTokens) {
        tokens.clear();
        tokens.addAll(initialTokens);
    }

    public Map<AbilityType, AbilityState> getAbilitiesMap() {
        return Collections.unmodifiableMap(abilities);
    }

    public Boolean hasAbility(AbilityType ability) {
        return abilities.containsKey(ability);
    }

    public Boolean isAbilityActive(AbilityType ability) {
        AbilityState state = abilities.get(ability);
        return state != null && state.isActive();
    }

    public void collectAbility(AbilityType ability) {
        abilities.put(ability, new AbilityState(false));
    }

    public void activateAbility(AbilityType ability) {
        AbilityState state = abilities.get(ability);
        if (state == null)
            throw new IllegalArgumentException("Ability not found: " + ability);
        abilities.put(ability, new AbilityState(true));
    }

    public void deactivateAbility(AbilityType ability) {
        AbilityState state = abilities.get(ability);
        if (state == null)
            throw new IllegalArgumentException("Ability not found: " + ability);
        abilities.put(ability, new AbilityState(false));
    }

    public void removeAbility(AbilityType ability) {
        abilities.remove(ability);
    }

    public Long getCurrentTileId() {
        return currentTileId;
    }

    public void moveTo(Long tileId) {
        this.currentTileId = tileId;
    }

    public Question getPendingQuestion() {
        return pendingQuestion;
    }

    public void setPendingQuestion(Question q) {
        this.pendingQuestion = q;
    }

    public void clearPendingQuestionAndSteps() {
        this.pendingQuestion = null;
        this.pendingSteps = null;
    }
}
