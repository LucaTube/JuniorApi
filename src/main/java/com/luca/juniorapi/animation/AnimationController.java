package com.luca.juniorapi.animation;

import net.minecraft.world.entity.LivingEntity;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador principal que gerencia as animações de uma entidade
 */
public class AnimationController {
    private final LivingEntity entity;
    private final Map<String, AnimationState> activeAnimations;
    private final Map<String, AnimationData> registeredAnimations;
    private AnimationState currentPrimaryAnimation;
    private float transitionProgress = 0.0f;
    private float transitionDuration = 5.0f; // 5 ticks de transição padrão

    public AnimationController(LivingEntity entity) {
        this.entity = entity;
        this.activeAnimations = new HashMap<>();
        this.registeredAnimations = new HashMap<>();
    }

    /**
     * Registra uma nova animação no sistema
     */
    public void registerAnimation(AnimationData animation) {
        registeredAnimations.put(animation.getName(), animation);
    }

    /**
     * Inicia uma animação
     * @param animationName Nome da animação
     * @param priority Prioridade (maior = mais importante)
     */
    public void playAnimation(String animationName, int priority) {
        playAnimation(animationName, priority, 1.0f);
    }

    /**
     * Inicia uma animação com velocidade customizada
     * @param animationName Nome da animação
     * @param priority Prioridade
     * @param speed Multiplicador de velocidade (1.0 = normal, 2.0 = 2x mais rápido)
     */
    public void playAnimation(String animationName, int priority, float speed) {
        AnimationData data = registeredAnimations.get(animationName);
        if (data == null) {
            JuniorApi.LOGGER.warn("Animation '{}' not found!", animationName);
            return;
        }

        AnimationState state = new AnimationState(data, priority, speed);
        activeAnimations.put(animationName, state);

        if (currentPrimaryAnimation == null || priority > currentPrimaryAnimation.getPriority()) {
            currentPrimaryAnimation = state;
            transitionProgress = 0.0f;
        }
    }

    /**
     * Para uma animação específica
     */
    public void stopAnimation(String animationName) {
        AnimationState removed = activeAnimations.remove(animationName);

        if (removed == currentPrimaryAnimation) {
            // Encontra a próxima animação com maior prioridade
            currentPrimaryAnimation = activeAnimations.values().stream()
                    .max((a, b) -> Integer.compare(a.getPriority(), b.getPriority()))
                    .orElse(null);
            transitionProgress = 0.0f;
        }
    }

    /**
     * Para todas as animações
     */
    public void stopAllAnimations() {
        activeAnimations.clear();
        currentPrimaryAnimation = null;
    }

    /**
     * Verifica se uma animação está tocando
     */
    public boolean isPlaying(String animationName) {
        return activeAnimations.containsKey(animationName);
    }

    /**
     * Define a duração da transição entre animações
     */
    public void setTransitionDuration(float ticks) {
        this.transitionDuration = ticks;
    }

    /**
     * Atualiza todas as animações ativas
     * Deve ser chamado a cada tick
     */
    public void tick() {
        if (activeAnimations.isEmpty()) return;

        // Atualiza o progresso de transição
        if (transitionProgress < 1.0f) {
            transitionProgress += 1.0f / transitionDuration;
            if (transitionProgress > 1.0f) {
                transitionProgress = 1.0f;
            }
        }

        // Atualiza cada animação ativa
        activeAnimations.values().removeIf(state -> {
            state.tick();

            // Remove animações que terminaram (se não estiverem em loop)
            if (!state.getAnimation().isLooping() && state.isFinished()) {
                if (state == currentPrimaryAnimation) {
                    currentPrimaryAnimation = null;
                }
                return true;
            }
            return false;
        });
    }

    /**
     * Obtém o valor interpolado de rotação para um osso específico
     */
    public Vector3fResult getRotationForBone(String boneName) {
        if (currentPrimaryAnimation == null) {
            return new Vector3fResult(0, 0, 0);
        }

        AnimationData data = currentPrimaryAnimation.getAnimation();
        BoneAnimation boneAnim = data.getBoneAnimation(boneName);

        if (boneAnim == null) {
            return new Vector3fResult(0, 0, 0);
        }

        var rotation = boneAnim.getRotationAt(currentPrimaryAnimation.getCurrentTime());

        // Aplica o fator de transição
        float blend = transitionProgress;
        return new Vector3fResult(
                rotation.x() * blend,
                rotation.y() * blend,
                rotation.z() * blend
        );
    }

    /**
     * Obtém o valor interpolado de posição para um osso específico
     */
    public Vector3fResult getPositionForBone(String boneName) {
        if (currentPrimaryAnimation == null) {
            return new Vector3fResult(0, 0, 0);
        }

        AnimationData data = currentPrimaryAnimation.getAnimation();
        BoneAnimation boneAnim = data.getBoneAnimation(boneName);

        if (boneAnim == null) {
            return new Vector3fResult(0, 0, 0);
        }

        var position = boneAnim.getPositionAt(currentPrimaryAnimation.getCurrentTime());

        float blend = transitionProgress;
        return new Vector3fResult(
                position.x() * blend,
                position.y() * blend,
                position.z() * blend
        );
    }

    /**
     * Obtém o valor interpolado de escala para um osso específico
     */
    public Vector3fResult getScaleForBone(String boneName) {
        if (currentPrimaryAnimation == null) {
            return new Vector3fResult(1, 1, 1);
        }

        AnimationData data = currentPrimaryAnimation.getAnimation();
        BoneAnimation boneAnim = data.getBoneAnimation(boneName);

        if (boneAnim == null) {
            return new Vector3fResult(1, 1, 1);
        }

        var scale = boneAnim.getScaleAt(currentPrimaryAnimation.getCurrentTime());

        float blend = transitionProgress;
        return new Vector3fResult(
                1.0f + (scale.x() - 1.0f) * blend,
                1.0f + (scale.y() - 1.0f) * blend,
                1.0f + (scale.z() - 1.0f) * blend
        );
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public AnimationState getCurrentPrimaryAnimation() {
        return currentPrimaryAnimation;
    }

    /**
     * Classe auxiliar para retornar resultados Vector3f
     */
    public static class Vector3fResult {
        public final float x, y, z;

        public Vector3fResult(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}