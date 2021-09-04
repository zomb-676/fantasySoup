package com.github.zomb_676.fantasySoup.mixin;

import com.github.zomb_676.fantasySoup.DelegateMixinMethodsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(GameRenderer.class)
public class GameRenderMixin {
    @Redirect(method = "render(FJZ)V",
            at =@At(value = "INVOKE",target = "Lnet/minecraft/client/renderer/GameRenderer;renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V"))
    public void customRenderLevel(GameRenderer gameRenderer, float partialTicks,long nanoTime, PoseStack poseStack) {
        DelegateMixinMethodsKt.delegateCustomRenderLevel(gameRenderer, partialTicks, nanoTime, poseStack);
    }
}
