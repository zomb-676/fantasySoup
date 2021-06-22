package com.github.zomb_676.fantasySoup.render

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.state.Property
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.loading.FMLEnvironment
import java.util.*
import java.util.concurrent.Callable
import kotlin.collections.HashMap
import kotlin.reflect.KClass

class NormalISTER<T:TileEntity>(tileEntity :Class<T>,vararg parameters : Pair<Class<*>,*>) : ItemStackTileEntityRenderer() {
    private val instance = lazy { tileEntity.getConstructor(*parameters.map { it.first }.toTypedArray())
        .newInstance(*parameters.map { it.second }.toTypedArray()) }
    // copy form vazkii.botania.client.render.tile.TEISR
    override fun func_239207_a_(
        stack: ItemStack,
        p_239207_2_: ItemCameraTransforms.TransformType,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        combinedLight: Int,
        combinedOverlay: Int
    ) {
        val renderer = TileEntityRendererDispatcher.instance.getRenderer(instance.value)
        renderer!!.render(instance.value,0f,matrixStack,buffer,combinedLight,combinedOverlay)
    }
}

fun Item.Properties.trySetISTER(ister : (()->()->()->ItemStackTileEntityRenderer?)?): Item.Properties {
//    DistExecutor.safeCallWhenOn(Dist.DEDICATED_SERVER)
    if (FMLEnvironment.dist == Dist.DEDICATED_SERVER){
        return this
    }
    if (ister!=null){
        this.setISTER{ Callable { ister()()() }}
    }
    return this
}