package com.luca.juniorapi.animation;

/**
 * Representa o estado atual de uma animação sendo executada
 */
public class AnimationState {
    private final AnimationData animation;
    private final int priority;
    private final float speedMultiplier;
    private float currentTime;
    private boolean finished;

    public AnimationState(AnimationData animation, int priority, float speedMultiplier) {
        this.animation = animation;
        this.priority = priority;
        this.speedMultiplier = speedMultiplier;
        this.currentTime = 0.0f;
        this.finished = false;
    }

    /**
     * Avança o tempo da animação em um tick
     */
    public void tick() {
        if (finished) return;

        currentTime += speedMultiplier;

        if (currentTime >= animation.getDuration()) {
            if (animation.isLooping()) {
                currentTime = currentTime % animation.getDuration();
            } else {
                currentTime = animation.getDuration();
                finished = true;
            }
        }
    }

    /**
     * Reinicia a animação do início
     */
    public void reset() {
        currentTime = 0.0f;
        finished = false;
    }

    /**
     * Define o tempo atual da animação
     */
    public void setCurrentTime(float time) {
        this.currentTime = Math.max(0, Math.min(time, animation.getDuration()));
    }

    /**
     * Obtém o progresso da animação (0.0 a 1.0)
     */
    public float getProgress() {
        return currentTime / animation.getDuration();
    }

    public AnimationData getAnimation() {
        return animation;
    }

    public int getPriority() {
        return priority;
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public boolean isFinished() {
        return finished;
    }
}