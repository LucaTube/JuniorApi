package com.luca.juniorapi.animation.presets;

import com.luca.juniorapi.animation.AnimationBuilder;
import com.luca.juniorapi.animation.AnimationData;
import com.luca.juniorapi.animation.BoneAnimation.InterpolationType;

/**
 * Biblioteca de animações pré-configuradas prontas para usar
 * Facilita a criação rápida de animações comuns
 */
public class AnimationPresets {

    // ===== ANIMAÇÕES BÁSICAS =====

    /**
     * Idle padrão com balanço suave da cabeça
     */
    public static AnimationData createBasicIdle(String headBone, String bodyBone) {
        return AnimationBuilder.create("idle", 80, true)
                .bone(headBone, InterpolationType.SMOOTH)
                .rotate(0, 0, 8, 0)
                .rotate(40, 0, -8, 0)
                .rotate(80, 0, 8, 0)
                .bone(bodyBone, InterpolationType.SMOOTH)
                .rotate(0, 0, 0, 2)
                .rotate(40, 0, 0, -2)
                .rotate(80, 0, 0, 2)
                .build();
    }

    /**
     * Caminhada bípede padrão
     */
    public static AnimationData createBipedWalk(String leftLeg, String rightLeg,
                                                String leftArm, String rightArm) {
        return AnimationBuilder.create("walk", 20, true)
                .bone(leftLeg, InterpolationType.SMOOTH)
                .rotate(0, 50, 0, 0)
                .rotate(10, -50, 0, 0)
                .rotate(20, 50, 0, 0)
                .bone(rightLeg, InterpolationType.SMOOTH)
                .rotate(0, -50, 0, 0)
                .rotate(10, 50, 0, 0)
                .rotate(20, -50, 0, 0)
                .bone(leftArm, InterpolationType.SMOOTH)
                .rotate(0, -35, 0, 0)
                .rotate(10, 35, 0, 0)
                .rotate(20, -35, 0, 0)
                .bone(rightArm, InterpolationType.SMOOTH)
                .rotate(0, 35, 0, 0)
                .rotate(10, -35, 0, 0)
                .rotate(20, 35, 0, 0)
                .build();
    }

    /**
     * Corrida bípede mais rápida e exagerada
     */
    public static AnimationData createBipedRun(String leftLeg, String rightLeg,
                                               String leftArm, String rightArm, String body) {
        return AnimationBuilder.create("run", 12, true)
                .bone(leftLeg, InterpolationType.EASE_IN_OUT)
                .rotate(0, 70, 0, 0)
                .rotate(6, -70, 0, 0)
                .rotate(12, 70, 0, 0)
                .bone(rightLeg, InterpolationType.EASE_IN_OUT)
                .rotate(0, -70, 0, 0)
                .rotate(6, 70, 0, 0)
                .rotate(12, -70, 0, 0)
                .bone(leftArm, InterpolationType.EASE_IN_OUT)
                .rotate(0, -60, 0, 0)
                .rotate(6, 60, 0, 0)
                .rotate(12, -60, 0, 0)
                .bone(rightArm, InterpolationType.EASE_IN_OUT)
                .rotate(0, 60, 0, 0)
                .rotate(6, -60, 0, 0)
                .rotate(12, 60, 0, 0)
                .bone(body, InterpolationType.SMOOTH)
                .rotate(0, 15, 0, 0)
                .rotate(12, 15, 0, 0)
                .build();
    }

    // ===== ANIMAÇÕES DE COMBATE =====

    /**
     * Ataque rápido com braço direito
     */
    public static AnimationData createQuickAttack(String rightArm, String body) {
        return AnimationBuilder.create("attack", 10, false)
                .bone(rightArm, InterpolationType.EASE_OUT)
                .rotate(0, -20, 0, -15)
                .rotate(3, -100, 0, 20)
                .rotate(10, -20, 0, -15)
                .bone(body, InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(3, 0, 25, 8)
                .rotate(10, 0, 0, 0)
                .build();
    }

    /**
     * Ataque poderoso com preparação
     */
    public static AnimationData createPowerAttack(String rightArm, String leftArm,
                                                  String body, String head) {
        return AnimationBuilder.create("power_attack", 30, false)
                .bone(rightArm, InterpolationType.EASE_IN_OUT)
                .rotate(0, -20, 0, -10)
                .rotate(10, -150, -30, -40)
                .rotate(15, -110, 0, 30)
                .rotate(30, -20, 0, -10)
                .bone(leftArm, InterpolationType.EASE_IN_OUT)
                .rotate(0, -10, 0, 10)
                .rotate(10, -60, 20, 30)
                .rotate(30, -10, 0, 10)
                .bone(body, InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(10, 0, -45, -15)
                .rotate(15, 0, 50, 20)
                .rotate(30, 0, 0, 0)
                .bone(head, InterpolationType.SMOOTH)
                .rotate(10, 0, -30, 0)
                .rotate(15, 0, 35, 0)
                .rotate(30, 0, 0, 0)
                .build();
    }

    /**
     * Bloqueio defensivo
     */
    public static AnimationData createBlock(String leftArm, String rightArm) {
        return AnimationBuilder.create("block", 5, false)
                .bone(leftArm, InterpolationType.EASE_OUT)
                .rotate(0, -45, 0, 15)
                .rotate(5, -90, -30, 45)
                .bone(rightArm, InterpolationType.EASE_OUT)
                .rotate(0, -45, 0, -15)
                .rotate(5, -90, 30, -45)
                .build();
    }

    // ===== ANIMAÇÕES DE REAÇÃO =====

    /**
     * Receber dano
     */
    public static AnimationData createHurt(String body, String head) {
        return AnimationBuilder.create("hurt", 10, false)
                .bone(body, InterpolationType.EASE_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(3, -15, 0, 8)
                .move(0, 0, 0, 0)
                .move(3, 0, 0, -3)
                .move(10, 0, 0, 0)
                .bone(head, InterpolationType.EASE_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(3, -20, 0, -10)
                .rotate(10, 0, 0, 0)
                .build();
    }

    /**
     * Morte dramática
     */
    public static AnimationData createDeath(String body, String head,
                                            String leftArm, String rightArm) {
        return AnimationBuilder.create("death", 40, false)
                .bone(body, InterpolationType.EASE_IN)
                .rotate(0, 0, 0, 0)
                .rotate(15, 30, 0, 15)
                .rotate(40, 90, 0, 0)
                .move(0, 0, 0, 0)
                .move(40, 0, -24, 0)
                .bone(head, InterpolationType.EASE_IN)
                .rotate(0, 0, 0, 0)
                .rotate(15, -30, 15, 0)
                .rotate(40, -45, 0, 0)
                .bone(leftArm, InterpolationType.EASE_IN)
                .rotate(0, -30, 0, 0)
                .rotate(40, -90, -45, 0)
                .bone(rightArm, InterpolationType.EASE_IN)
                .rotate(0, -30, 0, 0)
                .rotate(40, -90, 45, 0)
                .build();
    }

    // ===== ANIMAÇÕES ESPECIAIS =====

    /**
     * Pulo completo
     */
    public static AnimationData createJump(String body, String leftLeg, String rightLeg,
                                           String leftArm, String rightArm) {
        return AnimationBuilder.create("jump", 20, false)
                .bone(body, InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(3, 15, 0, 0)
                .rotate(8, -10, 0, 0)
                .rotate(20, 0, 0, 0)
                .move(0, 0, 0, 0)
                .move(5, 0, 10, 0)
                .move(15, 0, 10, 0)
                .move(20, 0, 0, 0)
                .scale(0, 1.0f)
                .scale(3, 1.1f, 0.8f, 1.0f)
                .scale(5, 0.9f, 1.2f, 0.9f)
                .scale(20, 1.0f)
                .bone(leftLeg, InterpolationType.EASE_OUT)
                .rotate(0, 10, 0, 0)
                .rotate(3, -45, 0, 0)
                .rotate(20, 10, 0, 0)
                .bone(rightLeg, InterpolationType.EASE_OUT)
                .rotate(0, 10, 0, 0)
                .rotate(3, -45, 0, 0)
                .rotate(20, 10, 0, 0)
                .bone(leftArm, InterpolationType.SMOOTH)
                .rotate(0, -20, 0, 0)
                .rotate(5, -120, -20, 0)
                .rotate(20, -20, 0, 0)
                .bone(rightArm, InterpolationType.SMOOTH)
                .rotate(0, -20, 0, 0)
                .rotate(5, -120, 20, 0)
                .rotate(20, -20, 0, 0)
                .build();
    }

    /**
     * Apontar/Indicar direção
     */
    public static AnimationData createPoint(String rightArm, String head) {
        return AnimationBuilder.create("point", 15, false)
                .bone(rightArm, InterpolationType.EASE_OUT)
                .rotate(0, -45, 0, 0)
                .rotate(8, -90, -45, 90)
                .rotate(15, -90, -45, 90)
                .bone(head, InterpolationType.SMOOTH)
                .rotate(0, 0, 0, 0)
                .rotate(8, 0, 30, -10)
                .rotate(15, 0, 30, -10)
                .build();
    }

    /**
     * Celebração/Vitória
     */
    public static AnimationData createVictory(String leftArm, String rightArm,
                                              String body, String head) {
        return AnimationBuilder.create("victory", 40, false)
                .bone(leftArm, InterpolationType.EASE_OUT)
                .rotate(0, -45, 0, 0)
                .rotate(10, -170, -20, 30)
                .rotate(40, -170, -20, 30)
                .bone(rightArm, InterpolationType.EASE_OUT)
                .rotate(0, -45, 0, 0)
                .rotate(10, -170, 20, -30)
                .rotate(40, -170, 20, -30)
                .bone(body, InterpolationType.SMOOTH)
                .rotate(10, -10, 0, 0)
                .rotate(20, 10, 0, 0)
                .rotate(30, -10, 0, 0)
                .rotate(40, 0, 0, 0)
                .bone(head, InterpolationType.SMOOTH)
                .rotate(10, -20, 0, 0)
                .rotate(20, 20, 0, 0)
                .rotate(30, -20, 0, 0)
                .rotate(40, 0, 0, 0)
                .build();
    }

    /**
     * Sentar
     */
    public static AnimationData createSit(String body, String leftLeg, String rightLeg) {
        return AnimationBuilder.create("sit", 20, false)
                .bone(body, InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(20, 0, 0, 0)
                .move(0, 0, 0, 0)
                .move(20, 0, -8, 0)
                .bone(leftLeg, InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(20, 90, 0, 0)
                .bone(rightLeg, InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(20, 90, 0, 0)
                .build();
    }

    // ===== ANIMAÇÕES QUADRÚPEDES =====

    /**
     * Caminhada quadrúpede (animais)
     */
    public static AnimationData createQuadrupedWalk(String frontLeft, String frontRight,
                                                    String backLeft, String backRight) {
        return AnimationBuilder.create("quad_walk", 20, true)
                .bone(frontLeft, InterpolationType.SMOOTH)
                .rotate(0, 30, 0, 0)
                .rotate(10, -30, 0, 0)
                .rotate(20, 30, 0, 0)
                .bone(frontRight, InterpolationType.SMOOTH)
                .rotate(0, -30, 0, 0)
                .rotate(10, 30, 0, 0)
                .rotate(20, -30, 0, 0)
                .bone(backLeft, InterpolationType.SMOOTH)
                .rotate(0, -30, 0, 0)
                .rotate(10, 30, 0, 0)
                .rotate(20, -30, 0, 0)
                .bone(backRight, InterpolationType.SMOOTH)
                .rotate(0, 30, 0, 0)
                .rotate(10, -30, 0, 0)
                .rotate(20, 30, 0, 0)
                .build();
    }

    /**
     * Idle de criatura voadora (balanço de asas)
     */
    public static AnimationData createFlyingIdle(String leftWing, String rightWing, String body) {
        return AnimationBuilder.create("flying_idle", 30, true)
                .bone(leftWing, InterpolationType.SMOOTH)
                .rotate(0, 0, 0, 45)
                .rotate(15, 0, 0, 30)
                .rotate(30, 0, 0, 45)
                .bone(rightWing, InterpolationType.SMOOTH)
                .rotate(0, 0, 0, -45)
                .rotate(15, 0, 0, -30)
                .rotate(30, 0, 0, -45)
                .bone(body, InterpolationType.SMOOTH)
                .move(0, 0, 0, 0)
                .move(15, 0, 1, 0)
                .move(30, 0, 0, 0)
                .build();
    }

    /**
     * Voo batendo asas
     */
    public static AnimationData createFlapping(String leftWing, String rightWing) {
        return AnimationBuilder.create("flapping", 15, true)
                .bone(leftWing, InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, 20)
                .rotate(7, 0, 0, 80)
                .rotate(15, 0, 0, 20)
                .bone(rightWing, InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, -20)
                .rotate(7, 0, 0, -80)
                .rotate(15, 0, 0, -20)
                .build();
    }

    // ===== ANIMAÇÕES DE EMOÇÃO =====

    /**
     * Acenar/Cumprimentar
     */
    public static AnimationData createWave(String rightArm) {
        return AnimationBuilder.create("wave", 40, true)
                .bone(rightArm, InterpolationType.SMOOTH)
                .rotate(0, -110, -45, 0)
                .rotate(10, -110, -45, -30)
                .rotate(20, -110, -45, 30)
                .rotate(30, -110, -45, -30)
                .rotate(40, -110, -45, 0)
                .build();
    }

    /**
     * Chorar/Tristeza
     */
    public static AnimationData createCry(String head, String body) {
        return AnimationBuilder.create("cry", 60, true)
                .bone(head, InterpolationType.SMOOTH)
                .rotate(0, 30, 0, 0)
                .rotate(30, 35, -10, 0)
                .rotate(60, 30, 0, 0)
                .bone(body, InterpolationType.SMOOTH)
                .rotate(0, 10, 0, 0)
                .rotate(30, 15, 0, -5)
                .rotate(60, 10, 0, 0)
                .build();
    }

    /**
     * Dançar
     */
    public static AnimationData createDance(String body, String head,
                                            String leftArm, String rightArm) {
        return AnimationBuilder.create("dance", 40, true)
                .bone(body, InterpolationType.SMOOTH)
                .rotate(0, 0, 15, 0)
                .rotate(10, 0, -15, 5)
                .rotate(20, 0, 15, 0)
                .rotate(30, 0, -15, -5)
                .rotate(40, 0, 15, 0)
                .bone(head, InterpolationType.SMOOTH)
                .rotate(0, 0, -10, 0)
                .rotate(20, 0, 10, 0)
                .rotate(40, 0, -10, 0)
                .bone(leftArm, InterpolationType.EASE_IN_OUT)
                .rotate(0, -120, 0, -20)
                .rotate(20, -120, 0, 20)
                .rotate(40, -120, 0, -20)
                .bone(rightArm, InterpolationType.EASE_IN_OUT)
                .rotate(0, -120, 0, 20)
                .rotate(20, -120, 0, -20)
                .rotate(40, -120, 0, 20)
                .build();
    }
}