package com.luca.juniorapi.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.luca.juniorapi.animation.AnimationController;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe base para modelos de entidades que suportam animações da Junior API
 * @param <T> Tipo da entidade
 */
public abstract class AnimatedEntityModel<T extends LivingEntity> extends EntityModel<T> {
    protected final Map<String, ModelPart> bones;
    protected AnimationController animationController;

    public AnimatedEntityModel() {
        this.bones = new HashMap<>();
    }

    /**
     * Registra um osso (ModelPart) com um nome para ser animado
     */
    protected void registerBone(String name, ModelPart bone) {
        bones.put(name, bone);
    }

    /**
     * Define o controlador de animação para este modelo
     */
    public void setAnimationController(AnimationController controller) {
        this.animationController = controller;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Inicializa o controlador se necessário
        if (animationController == null) {
            animationController = new AnimationController(entity);
            registerAnimations(animationController);
        }

        // Atualiza as animações
        animationController.tick();

        // Aplica as transformações de animação a cada osso
        applyAnimationsToBones();

        // Permite que subclasses adicionem lógica customizada
        setupCustomAnimations(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    /**
     * Aplica as transformações de animação aos ossos do modelo
     */
    protected void applyAnimationsToBones() {
        for (Map.Entry<String, ModelPart> entry : bones.entrySet()) {
            String boneName = entry.getKey();
            ModelPart bone = entry.getValue();

            // Obtém as transformações do controlador de animação
            var rotation = animationController.getRotationForBone(boneName);
            var position = animationController.getPositionForBone(boneName);
            var scale = animationController.getScaleForBone(boneName);

            // Aplica rotação
            bone.xRot = rotation.x;
            bone.yRot = rotation.y;
            bone.zRot = rotation.z;

            // Aplica posição
            bone.x += position.x;
            bone.y += position.y;
            bone.z += position.z;

            // Nota: Minecraft não suporta escala diretamente no ModelPart
            // Para escala, você precisará usar PoseStack.scale() no renderToBuffer
        }
    }

    /**
     * Sobrescreva este método para registrar suas animações
     */
    protected abstract void registerAnimations(AnimationController controller);

    /**
     * Sobrescreva este método para adicionar animações customizadas além das registradas
     */
    protected void setupCustomAnimations(T entity, float limbSwing, float limbSwingAmount,
                                         float ageInTicks, float netHeadYaw, float headPitch) {
        // Implementação padrão vazia - subclasses podem sobrescrever
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        // Renderiza todos os ossos raiz
        // Subclasses devem implementar a renderização específica
    }

    /**
     * Obtém um osso registrado por nome
     */
    public ModelPart getBone(String name) {
        return bones.get(name);
    }

    /**
     * Obtém o controlador de animação
     */
    public AnimationController getAnimationController() {
        return animationController;
    }
}