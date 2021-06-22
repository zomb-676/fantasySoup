package com.github.zomb_676.fantasySoup.mixin;

import com.github.zomb_676.fantasySoup.gui.Canvas;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.WorldLoadProgressScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//@Mixin(WorldLoadProgressScreen.class)
//public class LoadTest {
//    @Inject(method = "render" , at=@At("TAIL"))
//    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.disableDepthTest();
//        Canvas.INSTANCE.setRGBA(255, 255, 255, 255);
//        Canvas.INSTANCE.drawFullCircle(50f, 50f, 20);
//    }
//}
