package com.luca.juniorapi.animation;

import com.luca.juniorapi.animation.BoneAnimation.InterpolationType;

/**
 * Builder pattern para facilitar a criação de animações
 */
public class AnimationBuilder {
    private final AnimationData animationData;
    private String currentBone;
    private BoneAnimation currentBoneAnimation;

    private AnimationBuilder(String name, float duration, boolean looping) {
        this.animationData = new AnimationData(name, duration, looping);
    }

    /**
     * Cria um novo builder de animação
     * @param name Nome da animação
     * @param durationInTicks Duração em ticks (20 ticks = 1 segundo)
     * @param looping Se a animação deve repetir
     */
    public static AnimationBuilder create(String name, float durationInTicks, boolean looping) {
        return new AnimationBuilder(name, durationInTicks, looping);
    }

    /**
     * Cria uma animação não-looping
     */
    public static AnimationBuilder create(String name, float durationInTicks) {
        return new AnimationBuilder(name, durationInTicks, false);
    }

    /**
     * Define o modo de blend da animação
     */
    public AnimationBuilder blendMode(AnimationData.AnimationBlendMode mode) {
        // Nota: Você precisaria modificar AnimationData para aceitar isso após construção
        // ou criar um novo construtor
        return this;
    }

    /**
     * Inicia a animação de um osso específico
     * @param boneName Nome do osso
     * @param interpolation Tipo de interpolação
     */
    public AnimationBuilder bone(String boneName, InterpolationType interpolation) {
        // Salva o osso anterior se existir
        if (currentBone != null && currentBoneAnimation != null) {
            animationData.addBoneAnimation(currentBone, currentBoneAnimation);
        }

        this.currentBone = boneName;
        this.currentBoneAnimation = new BoneAnimation(interpolation);
        return this;
    }

    /**
     * Inicia a animação de um osso com interpolação linear (padrão)
     */
    public AnimationBuilder bone(String boneName) {
        return bone(boneName, InterpolationType.LINEAR);
    }

    /**
     * Adiciona um keyframe de rotação
     * @param time Tempo em ticks
     * @param x Rotação X em graus
     * @param y Rotação Y em graus
     * @param z Rotação Z em graus
     */
    public AnimationBuilder rotate(float time, float x, float y, float z) {
        if (currentBoneAnimation == null) {
            throw new IllegalStateException("Deve chamar bone() antes de rotate()");
        }
        // Converte graus para radianos
        currentBoneAnimation.addRotationKeyframe(
                time,
                (float) Math.toRadians(x),
                (float) Math.toRadians(y),
                (float) Math.toRadians(z)
        );
        return this;
    }

    /**
     * Adiciona um keyframe de posição
     * @param time Tempo em ticks
     * @param x Deslocamento X
     * @param y Deslocamento Y
     * @param z Deslocamento Z
     */
    public AnimationBuilder move(float time, float x, float y, float z) {
        if (currentBoneAnimation == null) {
            throw new IllegalStateException("Deve chamar bone() antes de move()");
        }
        currentBoneAnimation.addPositionKeyframe(time, x, y, z);
        return this;
    }

    /**
     * Adiciona um keyframe de escala
     * @param time Tempo em ticks
     * @param x Escala X
     * @param y Escala Y
     * @param z Escala Z
     */
    public AnimationBuilder scale(float time, float x, float y, float z) {
        if (currentBoneAnimation == null) {
            throw new IllegalStateException("Deve chamar bone() antes de scale()");
        }
        currentBoneAnimation.addScaleKeyframe(time, x, y, z);
        return this;
    }

    /**
     * Adiciona escala uniforme
     */
    public AnimationBuilder scale(float time, float scale) {
        return scale(time, scale, scale, scale);
    }

    /**
     * Finaliza a construção e retorna a AnimationData
     */
    public AnimationData build() {
        // Salva o último osso
        if (currentBone != null && currentBoneAnimation != null) {
            animationData.addBoneAnimation(currentBone, currentBoneAnimation);
        }
        return animationData;
    }

    /**
     * Métodos de conveniência para criar animações comuns
     */

    /**
     * Cria uma animação de idle simples (balanço suave)
     */
    public static AnimationData createIdleAnimation(String boneName, float swayAmount) {
        return AnimationBuilder.create("idle", 40, true)
                .bone(boneName, InterpolationType.SMOOTH)
                .rotate(0, 0, swayAmount, 0)
                .rotate(20, 0, -swayAmount, 0)
                .rotate(40, 0, swayAmount, 0)
                .build();
    }

    /**
     * Cria uma animação de caminhada básica
     */
    public static AnimationData createWalkAnimation(String leftLeg, String rightLeg,
                                                    String leftArm, String rightArm) {
        return AnimationBuilder.create("walk", 20, true)
                .bone(leftLeg, InterpolationType.SMOOTH)
                .rotate(0, 45, 0, 0)
                .rotate(10, -45, 0, 0)
                .rotate(20, 45, 0, 0)
                .bone(rightLeg, InterpolationType.SMOOTH)
                .rotate(0, -45, 0, 0)
                .rotate(10, 45, 0, 0)
                .rotate(20, -45, 0, 0)
                .bone(leftArm, InterpolationType.SMOOTH)
                .rotate(0, -30, 0, 0)
                .rotate(10, 30, 0, 0)
                .rotate(20, -30, 0, 0)
                .bone(rightArm, InterpolationType.SMOOTH)
                .rotate(0, 30, 0, 0)
                .rotate(10, -30, 0, 0)
                .rotate(20, 30, 0, 0)
                .build();
    }

    /**
     * Cria uma animação de ataque
     */
    public static AnimationData createAttackAnimation(String attackingArm) {
        return AnimationBuilder.create("attack", 10, false)
                .bone(attackingArm, InterpolationType.EASE_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(3, -90, 0, 0)
                .rotate(10, 0, 0, 0)
                .build();
    }
}