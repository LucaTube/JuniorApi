package com.luca.juniorapi.animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Armazena os dados de uma animação incluindo keyframes e configurações
 *
 * Esta classe representa uma animação completa com todas as suas configurações:
 * - Nome da animação
 * - Duração em ticks (20 ticks = 1 segundo)
 * - Se deve fazer loop ou não
 * - Modo de blend (como mistura com outras animações)
 * - Mapa com animações de cada osso individual
 *
 * Exemplo de uso:
 * AnimationData walkAnim = new AnimationData("walk", 20, true);
 * walkAnim.addBoneAnimation("leftLeg", leftLegAnimation);
 * walkAnim.addBoneAnimation("rightLeg", rightLegAnimation);
 */
public class AnimationData {
    private final String name;
    private final Map<String, BoneAnimation> boneAnimations;
    private final float duration; // Em ticks (20 ticks = 1 segundo)
    private final boolean looping;
    private final AnimationBlendMode blendMode;

    /**
     * Construtor simplificado com blend mode padrão (REPLACE)
     *
     * @param name Nome da animação (ex: "walk", "attack", "idle")
     * @param duration Duração em ticks (20 ticks = 1 segundo)
     * @param looping Se true, a animação reinicia ao terminar
     */
    public AnimationData(String name, float duration, boolean looping) {
        this(name, duration, looping, AnimationBlendMode.REPLACE);
    }

    /**
     * Construtor completo com todas as opções
     *
     * @param name Nome da animação
     * @param duration Duração em ticks
     * @param looping Se deve fazer loop
     * @param blendMode Como a animação mistura com outras
     */
    public AnimationData(String name, float duration, boolean looping, AnimationBlendMode blendMode) {
        this.name = name;
        this.duration = duration;
        this.looping = looping;
        this.blendMode = blendMode;
        this.boneAnimations = new HashMap<>();
    }

    /**
     * Adiciona uma animação para um osso específico
     *
     * @param boneName Nome do osso (deve corresponder ao nome registrado no modelo)
     * @param animation Objeto BoneAnimation contendo os keyframes deste osso
     * @return Esta instância para chamadas encadeadas (fluent interface)
     */
    public AnimationData addBoneAnimation(String boneName, BoneAnimation animation) {
        boneAnimations.put(boneName, animation);
        return this;
    }

    /**
     * Obtém a animação de um osso específico
     *
     * @param boneName Nome do osso
     * @return BoneAnimation do osso, ou null se não existir
     */
    public BoneAnimation getBoneAnimation(String boneName) {
        return boneAnimations.get(boneName);
    }

    /**
     * Verifica se esta animação contém dados para um osso específico
     *
     * @param boneName Nome do osso
     * @return true se existe animação para este osso
     */
    public boolean hasBoneAnimation(String boneName) {
        return boneAnimations.containsKey(boneName);
    }

    /**
     * Remove a animação de um osso
     *
     * @param boneName Nome do osso
     * @return BoneAnimation removida, ou null se não existia
     */
    public BoneAnimation removeBoneAnimation(String boneName) {
        return boneAnimations.remove(boneName);
    }

    /**
     * Obtém o nome da animação
     *
     * @return Nome (ex: "walk", "attack")
     */
    public String getName() {
        return name;
    }

    /**
     * Obtém a duração da animação em ticks
     *
     * @return Duração (20 ticks = 1 segundo)
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Verifica se a animação faz loop
     *
     * @return true se reinicia ao terminar
     */
    public boolean isLooping() {
        return looping;
    }

    /**
     * Obtém o modo de blend desta animação
     *
     * @return AnimationBlendMode configurado
     */
    public AnimationBlendMode getBlendMode() {
        return blendMode;
    }

    /**
     * Obtém todas as animações de ossos
     *
     * @return Mapa com nome do osso -> BoneAnimation
     */
    public Map<String, BoneAnimation> getAllBoneAnimations() {
        return boneAnimations;
    }

    /**
     * Obtém o número de ossos animados
     *
     * @return Quantidade de ossos com animação
     */
    public int getBoneCount() {
        return boneAnimations.size();
    }

    /**
     * Verifica se esta animação está vazia (sem ossos)
     *
     * @return true se não tem nenhum osso animado
     */
    public boolean isEmpty() {
        return boneAnimations.isEmpty();
    }

    /**
     * Cria uma cópia desta animação
     * Útil para criar variações de uma animação base
     *
     * @return Nova instância de AnimationData com os mesmos dados
     */
    public AnimationData copy() {
        AnimationData copy = new AnimationData(this.name, this.duration, this.looping, this.blendMode);
        for (Map.Entry<String, BoneAnimation> entry : boneAnimations.entrySet()) {
            copy.addBoneAnimation(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    /**
     * Cria uma cópia com um novo nome
     *
     * @param newName Novo nome para a cópia
     * @return Nova AnimationData com o novo nome
     */
    public AnimationData copyWithName(String newName) {
        AnimationData copy = new AnimationData(newName, this.duration, this.looping, this.blendMode);
        for (Map.Entry<String, BoneAnimation> entry : boneAnimations.entrySet()) {
            copy.addBoneAnimation(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    @Override
    public String toString() {
        return "AnimationData{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", looping=" + looping +
                ", blendMode=" + blendMode +
                ", boneCount=" + boneAnimations.size() +
                '}';
    }

    /**
     * Modos de blend que definem como a animação se mistura com outras
     */
    public enum AnimationBlendMode {
        /**
         * REPLACE - Substitui completamente a animação anterior
         * Use para: Transições normais entre animações
         * Exemplo: Trocar de "idle" para "walk"
         */
        REPLACE,

        /**
         * ADD - Adiciona aos valores atuais (soma)
         * Use para: Animações aditivas como respiração sobre movimento
         * Exemplo: Respiração + caminhada ao mesmo tempo
         */
        ADD,

        /**
         * MULTIPLY - Multiplica os valores atuais
         * Use para: Escalas ou modificadores proporcionais
         * Exemplo: Animação de "encolher" multiplicando escala
         */
        MULTIPLY,

        /**
         * BLEND - Mistura suavemente com a animação anterior
         * Use para: Transições suaves e graduais
         * Exemplo: Fade entre duas poses
         */
        BLEND
    }
}