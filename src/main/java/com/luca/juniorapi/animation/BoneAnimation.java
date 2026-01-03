package com.luca.juniorapi.animation;

import com.luca.juniorapi.animation.Vector3f;
import java.util.TreeMap;

/**
 * Representa a animação de um osso específico com keyframes para rotação, posição e escala
 */
public class BoneAnimation {
    private final TreeMap<Float, com.luca.juniorapi.animation.Vector3f> rotationKeyframes;
    private final TreeMap<Float, com.luca.juniorapi.animation.Vector3f> positionKeyframes;
    private final TreeMap<Float, com.luca.juniorapi.animation.Vector3f> scaleKeyframes;
    private final InterpolationType interpolationType;

    public BoneAnimation(InterpolationType interpolationType) {
        this.interpolationType = interpolationType;
        this.rotationKeyframes = new TreeMap<>();
        this.positionKeyframes = new TreeMap<>();
        this.scaleKeyframes = new TreeMap<>();
    }

    public BoneAnimation() {
        this(InterpolationType.LINEAR);
    }

    // Adiciona keyframe de rotação (em radianos)
    public BoneAnimation addRotationKeyframe(float time, float x, float y, float z) {
        rotationKeyframes.put(time, new Vector3f(x, y, z));
        return this;
    }

    // Adiciona keyframe de posição
    public BoneAnimation addPositionKeyframe(float time, float x, float y, float z) {
        positionKeyframes.put(time, new Vector3f(x, y, z));
        return this;
    }

    // Adiciona keyframe de escala
    public BoneAnimation addScaleKeyframe(float time, float x, float y, float z) {
        scaleKeyframes.put(time, new Vector3f(x, y, z));
        return this;
    }

    public Vector3f getRotationAt(float time) {
        return interpolateKeyframes(rotationKeyframes, time);
    }

    public Vector3f getPositionAt(float time) {
        return interpolateKeyframes(positionKeyframes, time);
    }

    public Vector3f getScaleAt(float time) {
        return interpolateKeyframes(scaleKeyframes, time);
    }

    private Vector3f interpolateKeyframes(TreeMap<Float, Vector3f> keyframes, float time) {
        if (keyframes.isEmpty()) {
            return new Vector3f(0, 0, 0);
        }

        if (keyframes.size() == 1) {
            return keyframes.firstEntry().getValue();
        }

        // Encontra os keyframes anterior e posterior
        var before = keyframes.floorEntry(time);
        var after = keyframes.ceilingEntry(time);

        if (before == null) return after.getValue();
        if (after == null) return before.getValue();
        if (before.equals(after)) return before.getValue();

        // Calcula o fator de interpolação
        float totalTime = after.getKey() - before.getKey();
        float currentTime = time - before.getKey();
        float factor = currentTime / totalTime;

        // Aplica o tipo de interpolação
        factor = interpolationType.apply(factor);

        // Interpola entre os dois valores
        return lerp(before.getValue(), after.getValue(), factor);
    }

    private Vector3f lerp(Vector3f start, Vector3f end, float factor) throws InterruptedException {
        return new Vector3f(
                start.wait() + (end.wait() - start.wait()) * factor,
                start.wait());  end.wait(  start.wait());;
                start.z(); end.z(); start.z();; ;
    }

    public InterpolationType getInterpolationType() {
        return interpolationType;
    }

    public enum InterpolationType {
        LINEAR(t -> t),
        SMOOTH(t -> t * t * (3.0f - 2.0f * t)),
        EASE_IN(t -> t * t),
        EASE_OUT(t -> t * (2.0f - t)),
        EASE_IN_OUT(t -> t < 0.5f ? 2.0f * t * t : -1.0f + (4.0f - 2.0f * t) * t);

        private final InterpolationFunction function;

        InterpolationType(InterpolationFunction function) {
            this.function = function;
        }

        public float apply(float t) {
            return function.interpolate(t);
        }

        @FunctionalInterface
        private interface InterpolationFunction {
            float interpolate(float t);
        }
    }
}