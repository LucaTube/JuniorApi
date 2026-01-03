package com.luca.juniorapi.animation.presets;

import com.luca.juniorapi.animation.AnimationBuilder;
import com.luca.juniorapi.animation.AnimationData;
import com.luca.juniorapi.animation.BoneAnimation.InterpolationType;

import java.util.HashMap;
import java.util.Map;

/**
 * Presets de animações flexíveis que se adaptam a modelos customizados
 * Permite especificar qualquer nome de osso e criar animações genéricas
 */
public class FlexibleAnimationPresets {

    /**
     * Builder de configuração para criar animações flexíveis
     */
    public static class FlexibleAnimationBuilder {
        private final String animationName;
        private final float duration;
        private final boolean looping;
        private final Map<String, BoneConfig> boneConfigs;

        private FlexibleAnimationBuilder(String animationName, float duration, boolean looping) {
            this.animationName = animationName;
            this.duration = duration;
            this.looping = looping;
            this.boneConfigs = new HashMap<>();
        }

        /**
         * Adiciona um osso à animação com configuração customizada
         */
        public FlexibleAnimationBuilder addBone(String boneName, BoneConfig config) {
            boneConfigs.put(boneName, config);
            return this;
        }

        /**
         * Constrói a animação final
         */
        public AnimationData build() {
            AnimationBuilder builder = AnimationBuilder.create(animationName, duration, looping);

            for (Map.Entry<String, BoneConfig> entry : boneConfigs.entrySet()) {
                String boneName = entry.getKey();
                BoneConfig config = entry.getValue();

                builder.bone(boneName, config.interpolation);

                // Aplica todos os keyframes configurados
                for (KeyframeData keyframe : config.keyframes) {
                    if (keyframe.rotation != null) {
                        builder.rotate(keyframe.time,
                                keyframe.rotation[0],
                                keyframe.rotation[1],
                                keyframe.rotation[2]);
                    }
                    if (keyframe.position != null) {
                        builder.move(keyframe.time,
                                keyframe.position[0],
                                keyframe.position[1],
                                keyframe.position[2]);
                    }
                    if (keyframe.scale != null) {
                        builder.scale(keyframe.time,
                                keyframe.scale[0],
                                keyframe.scale[1],
                                keyframe.scale[2]);
                    }
                }
            }

            return builder.build();
        }
    }

    /**
     * Configuração de um osso individual
     */
    public static class BoneConfig {
        private final InterpolationType interpolation;
        private final java.util.List<KeyframeData> keyframes;

        public BoneConfig(InterpolationType interpolation) {
            this.interpolation = interpolation;
            this.keyframes = new java.util.ArrayList<>();
        }

        public BoneConfig addKeyframe(float time, float[] rotation, float[] position, float[] scale) {
            keyframes.add(new KeyframeData(time, rotation, position, scale));
            return this;
        }

        public BoneConfig rotate(float time, float x, float y, float z) {
            return addKeyframe(time, new float[]{x, y, z}, null, null);
        }

        public BoneConfig move(float time, float x, float y, float z) {
            return addKeyframe(time, null, new float[]{x, y, z}, null);
        }

        public BoneConfig scale(float time, float x, float y, float z) {
            return addKeyframe(time, null, null, new float[]{x, y, z});
        }
    }

    private static class KeyframeData {
        final float time;
        final float[] rotation;
        final float[] position;
        final float[] scale;

        KeyframeData(float time, float[] rotation, float[] position, float[] scale) {
            this.time = time;
            this.rotation = rotation;
            this.position = position;
            this.scale = scale;
        }
    }

    /**
     * Cria um builder flexível para animações customizadas
     */
    public static FlexibleAnimationBuilder createFlexible(String name, float duration, boolean looping) {
        return new FlexibleAnimationBuilder(name, duration, looping);
    }

    // ===== ANIMAÇÕES GENÉRICAS PARA QUALQUER MODELO =====

    /**
     * Idle genérico - balança suavemente qualquer osso especificado
     * @param bones Mapa de ossos e suas intensidades de balanço (0.0-1.0)
     */
    public static AnimationData createGenericIdle(Map<String, Float> bones) {
        FlexibleAnimationBuilder builder = createFlexible("idle", 80, true);

        for (Map.Entry<String, Float> entry : bones.entrySet()) {
            String boneName = entry.getKey();
            float intensity = entry.getValue();

            BoneConfig config = new BoneConfig(InterpolationType.SMOOTH)
                    .rotate(0, 0, intensity * 10, 0)
                    .rotate(40, 0, -intensity * 10, 0)
                    .rotate(80, 0, intensity * 10, 0);

            builder.addBone(boneName, config);
        }

        return builder.build();
    }

    /**
     * Respiração para corpo/torso - funciona com qualquer nome de osso
     */
    public static AnimationData createBreathing(String bodyBone) {
        return AnimationBuilder.create("breathing", 60, true)
                .bone(bodyBone, InterpolationType.SMOOTH)
                .scale(0, 1.0f, 1.0f, 1.0f)
                .scale(30, 1.02f, 0.98f, 1.01f)
                .scale(60, 1.0f, 1.0f, 1.0f)
                .build();
    }

    /**
     * Balanço de cauda/cape/acessório alongado
     */
    public static AnimationData createTailSwing(String tailBone, float intensity) {
        return AnimationBuilder.create("tail_swing", 40, true)
                .bone(tailBone, InterpolationType.SMOOTH)
                .rotate(0, 0, intensity * 20, intensity * 15)
                .rotate(20, 0, -intensity * 20, -intensity * 15)
                .rotate(40, 0, intensity * 20, intensity * 15)
                .build();
    }

    /**
     * Movimento de mandíbula/boca - para criaturas que falam/comem
     */
    public static AnimationData createJawMovement(String jawBone) {
        return AnimationBuilder.create("jaw_open", 10, false)
                .bone(jawBone, InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(5, 25, 0, 0)
                .rotate(10, 0, 0, 0)
                .build();
    }

    /**
     * Mastigação contínua
     */
    public static AnimationData createChewing(String jawBone, String mouthBone) {
        FlexibleAnimationBuilder builder = createFlexible("chewing", 20, true);

        builder.addBone(jawBone, new BoneConfig(InterpolationType.LINEAR)
                .rotate(0, 0, 0, 0)
                .rotate(5, 15, 0, 0)
                .rotate(10, 0, 0, 0)
                .rotate(15, 15, 0, 0)
                .rotate(20, 0, 0, 0));

        if (mouthBone != null) {
            builder.addBone(mouthBone, new BoneConfig(InterpolationType.SMOOTH)
                    .scale(0, 1.0f)
                    .scale(5, 1.1f, 0.9f, 1.0f)
                    .scale(10, 1.0f)
                    .scale(15, 1.1f, 0.9f, 1.0f)
                    .scale(20, 1.0f));
        }

        return builder.build();
    }

    /**
     * Ataque com arma - suporta bone de arma separado
     */
    public static AnimationData createWeaponAttack(String armBone, String weaponBone,
                                                   AttackStyle style) {
        FlexibleAnimationBuilder builder = createFlexible("weapon_attack",
                style == AttackStyle.FAST ? 10 : 20, false);

        switch (style) {
            case FAST:
                builder.addBone(armBone, new BoneConfig(InterpolationType.EASE_OUT)
                        .rotate(0, -20, 0, -10)
                        .rotate(4, -100, 0, 20)
                        .rotate(10, -20, 0, -10));

                if (weaponBone != null) {
                    builder.addBone(weaponBone, new BoneConfig(InterpolationType.EASE_OUT)
                            .rotate(0, 0, 0, 0)
                            .rotate(4, -30, 0, 0)
                            .rotate(10, 0, 0, 0));
                }
                break;

            case HEAVY:
                builder.addBone(armBone, new BoneConfig(InterpolationType.EASE_IN_OUT)
                        .rotate(0, -30, 0, -20)
                        .rotate(8, -150, -40, -30)
                        .rotate(12, -110, 0, 40)
                        .rotate(20, -30, 0, -20));

                if (weaponBone != null) {
                    builder.addBone(weaponBone, new BoneConfig(InterpolationType.EASE_IN_OUT)
                            .rotate(0, 0, 0, 0)
                            .rotate(8, -60, -20, 0)
                            .rotate(12, 40, 0, 0)
                            .rotate(20, 0, 0, 0));
                }
                break;

            case THRUST:
                builder.addBone(armBone, new BoneConfig(InterpolationType.EASE_OUT)
                        .rotate(0, -90, -20, 0)
                        .move(0, 0, 0, 0)
                        .rotate(6, -90, 0, 0)
                        .move(6, 0, 0, 8)
                        .rotate(15, -90, -20, 0)
                        .move(15, 0, 0, 0));

                if (weaponBone != null) {
                    builder.addBone(weaponBone, new BoneConfig(InterpolationType.LINEAR)
                            .rotate(0, 0, 0, 0)
                            .rotate(6, 0, 0, 0)
                            .rotate(15, 0, 0, 0));
                }
                break;
        }

        return builder.build();
    }

    public enum AttackStyle {
        FAST,    // Ataque rápido
        HEAVY,   // Ataque pesado
        THRUST   // Estocada
    }

    /**
     * Caminhada genérica para qualquer configuração de pernas
     */
    public static AnimationData createGenericWalk(String[] leftLegs, String[] rightLegs,
                                                  String[] leftArms, String[] rightArms) {
        FlexibleAnimationBuilder builder = createFlexible("walk", 20, true);

        // Pernas esquerdas
        for (String leg : leftLegs) {
            if (leg != null) {
                builder.addBone(leg, new BoneConfig(InterpolationType.SMOOTH)
                        .rotate(0, 45, 0, 0)
                        .rotate(10, -45, 0, 0)
                        .rotate(20, 45, 0, 0));
            }
        }

        // Pernas direitas
        for (String leg : rightLegs) {
            if (leg != null) {
                builder.addBone(leg, new BoneConfig(InterpolationType.SMOOTH)
                        .rotate(0, -45, 0, 0)
                        .rotate(10, 45, 0, 0)
                        .rotate(20, -45, 0, 0));
            }
        }

        // Braços esquerdos
        for (String arm : leftArms) {
            if (arm != null) {
                builder.addBone(arm, new BoneConfig(InterpolationType.SMOOTH)
                        .rotate(0, -30, 0, 0)
                        .rotate(10, 30, 0, 0)
                        .rotate(20, -30, 0, 0));
            }
        }

        // Braços direitos
        for (String arm : rightArms) {
            if (arm != null) {
                builder.addBone(arm, new BoneConfig(InterpolationType.SMOOTH)
                        .rotate(0, 30, 0, 0)
                        .rotate(10, -30, 0, 0)
                        .rotate(20, 30, 0, 0));
            }
        }

        return builder.build();
    }

    /**
     * Balançar cape/manto/asa
     */
    public static AnimationData createCapeFlow(String capeBone, float intensity) {
        return AnimationBuilder.create("cape_flow", 30, true)
                .bone(capeBone, InterpolationType.SMOOTH)
                .rotate(0, intensity * 8, 0, intensity * 5)
                .rotate(15, -intensity * 8, 0, -intensity * 5)
                .rotate(30, intensity * 8, 0, intensity * 5)
                .build();
    }

    /**
     * Animação de asas batendo (funciona para qualquer osso de asa)
     */
    public static AnimationData createWingFlap(String leftWing, String rightWing,
                                               String[] subWingsLeft, String[] subWingsRight) {
        FlexibleAnimationBuilder builder = createFlexible("wing_flap", 15, true);

        // Asa principal esquerda
        builder.addBone(leftWing, new BoneConfig(InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, 25)
                .rotate(7, 0, 0, 85)
                .rotate(15, 0, 0, 25));

        // Asa principal direita
        builder.addBone(rightWing, new BoneConfig(InterpolationType.EASE_IN_OUT)
                .rotate(0, 0, 0, -25)
                .rotate(7, 0, 0, -85)
                .rotate(15, 0, 0, -25));

        // Sub-asas esquerdas com delay
        if (subWingsLeft != null) {
            for (int i = 0; i < subWingsLeft.length; i++) {
                if (subWingsLeft[i] != null) {
                    float delay = i * 1.5f;
                    builder.addBone(subWingsLeft[i], new BoneConfig(InterpolationType.EASE_IN_OUT)
                            .rotate(0 + delay, 0, 0, 20)
                            .rotate(7 + delay, 0, 0, 70)
                            .rotate(15, 0, 0, 20));
                }
            }
        }

        // Sub-asas direitas com delay
        if (subWingsRight != null) {
            for (int i = 0; i < subWingsRight.length; i++) {
                if (subWingsRight[i] != null) {
                    float delay = i * 1.5f;
                    builder.addBone(subWingsRight[i], new BoneConfig(InterpolationType.EASE_IN_OUT)
                            .rotate(0 + delay, 0, 0, -20)
                            .rotate(7 + delay, 0, 0, -70)
                            .rotate(15, 0, 0, -20));
                }
            }
        }

        return builder.build();
    }

    /**
     * Tentáculos ou membros múltiplos ondulantes
     */
    public static AnimationData createTentacleWave(String[] tentacleBones, float phaseShift) {
        FlexibleAnimationBuilder builder = createFlexible("tentacle_wave", 40, true);

        for (int i = 0; i < tentacleBones.length; i++) {
            if (tentacleBones[i] != null) {
                float phase = i * phaseShift;

                builder.addBone(tentacleBones[i], new BoneConfig(InterpolationType.SMOOTH)
                        .rotate(0 + phase, 0, 20, 10)
                        .rotate(20 + phase, 0, -20, -10)
                        .rotate(40 + phase, 0, 20, 10));
            }
        }

        return builder.build();
    }

    /**
     * Mão/garra abrindo e fechando
     */
    public static AnimationData createHandGrip(String handBone, String[] fingerBones) {
        FlexibleAnimationBuilder builder = createFlexible("hand_grip", 20, false);

        // Mão principal
        if (handBone != null) {
            builder.addBone(handBone, new BoneConfig(InterpolationType.EASE_IN_OUT)
                    .scale(0, 1.0f)
                    .scale(10, 1.1f, 0.9f, 1.05f)
                    .scale(20, 1.0f));
        }

        // Dedos individuais
        if (fingerBones != null) {
            for (String finger : fingerBones) {
                if (finger != null) {
                    builder.addBone(finger, new BoneConfig(InterpolationType.EASE_OUT)
                            .rotate(0, 0, 0, 0)
                            .rotate(10, 45, 0, 10)
                            .rotate(20, 0, 0, 0));
                }
            }
        }

        return builder.build();
    }

    /**
     * Criatura com múltiplas cabeças
     */
    public static AnimationData createMultiHeadIdle(String[] headBones) {
        FlexibleAnimationBuilder builder = createFlexible("multi_head_idle", 60, true);

        for (int i = 0; i < headBones.length; i++) {
            if (headBones[i] != null) {
                // Cada cabeça tem um padrão de movimento ligeiramente diferente
                float offset = i * 15f;
                float intensity = 1.0f + (i * 0.2f);

                builder.addBone(headBones[i], new BoneConfig(InterpolationType.SMOOTH)
                        .rotate(0 + offset, 0, intensity * 10, 0)
                        .rotate(30 + offset, 0, -intensity * 10, 0)
                        .rotate(60 + offset, 0, intensity * 10, 0));
            }
        }

        return builder.build();
    }

    /**
     * Transformação/morphing entre formas
     */
    public static AnimationData createMorph(String[] affectedBones, float scaleChange) {
        FlexibleAnimationBuilder builder = createFlexible("morph", 30, false);

        for (String bone : affectedBones) {
            if (bone != null) {
                builder.addBone(bone, new BoneConfig(InterpolationType.EASE_IN_OUT)
                        .scale(0, 1.0f)
                        .scale(15, scaleChange, scaleChange, scaleChange)
                        .scale(30, 1.0f));
            }
        }

        return builder.build();
    }

    /**
     * Levitação/flutuação para criaturas voadoras/mágicas
     */
    public static AnimationData createLevitation(String bodyBone, String[] limbBones) {
        FlexibleAnimationBuilder builder = createFlexible("levitation", 60, true);

        // Corpo flutua suavemente
        builder.addBone(bodyBone, new BoneConfig(InterpolationType.SMOOTH)
                .move(0, 0, 0, 0)
                .move(30, 0, 2, 0)
                .move(60, 0, 0, 0)
                .rotate(0, 0, 0, 0)
                .rotate(30, 0, 5, 0)
                .rotate(60, 0, 0, 0));

        // Membros balançam levemente
        if (limbBones != null) {
            for (int i = 0; i < limbBones.length; i++) {
                if (limbBones[i] != null) {
                    float phase = i * 10f;
                    builder.addBone(limbBones[i], new BoneConfig(InterpolationType.SMOOTH)
                            .rotate(0 + phase, 0, 0, 15)
                            .rotate(30 + phase, 0, 0, -15)
                            .rotate(60 + phase, 0, 0, 15));
                }
            }
        }

        return builder.build();
    }

    /**
     * Rugido/grito - abre boca, cabeça para trás
     */
    public static AnimationData createRoar(String headBone, String jawBone, String neckBone) {
        FlexibleAnimationBuilder builder = createFlexible("roar", 40, false);

        // Cabeça vai para trás
        builder.addBone(headBone, new BoneConfig(InterpolationType.EASE_OUT)
                .rotate(0, 0, 0, 0)
                .rotate(8, -30, 0, 0)
                .rotate(40, 0, 0, 0));

        // Mandíbula abre
        if (jawBone != null) {
            builder.addBone(jawBone, new BoneConfig(InterpolationType.EASE_IN_OUT)
                    .rotate(0, 0, 0, 0)
                    .rotate(8, 45, 0, 0)
                    .rotate(30, 40, 0, 0)
                    .rotate(40, 0, 0, 0));
        }

        // Pescoço estica
        if (neckBone != null) {
            builder.addBone(neckBone, new BoneConfig(InterpolationType.SMOOTH)
                    .rotate(0, 0, 0, 0)
                    .rotate(8, -20, 0, 0)
                    .scale(0, 1.0f)
                    .scale(8, 1.0f, 1.3f, 1.0f)
                    .scale(40, 1.0f));
        }

        return builder.build();
    }

    /**
     * Criatura segmentada (cobra, centopeia, dragão)
     */
    public static AnimationData createSegmentedMovement(String[] segments, MovementType type) {
        FlexibleAnimationBuilder builder = createFlexible("segmented_move", 30, true);

        float waveSpeed = type == MovementType.SLITHER ? 8f : 5f;

        for (int i = 0; i < segments.length; i++) {
            if (segments[i] != null) {
                float phase = i * waveSpeed;
                float amplitude = 20f - (i * 2f); // Diminui amplitude ao longo do corpo

                builder.addBone(segments[i], new BoneConfig(InterpolationType.SMOOTH)
                        .rotate(0 + phase, 0, amplitude, 0)
                        .rotate(15 + phase, 0, -amplitude, 0)
                        .rotate(30 + phase, 0, amplitude, 0));
            }
        }

        return builder.build();
    }

    public enum MovementType {
        SLITHER,  // Deslizar (cobra)
        UNDULATE  // Ondular (dragão/peixe)
    }
}