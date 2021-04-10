package com.github.zomb_676.fantasySoup.shader

import net.minecraft.client.Minecraft
import net.minecraft.resources.IResourceManager
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL20

open class VertexShader(location: ResourceLocation, manger: IResourceManager = Minecraft.getInstance().resourceManager) :
    Shader(manger, location, GL20.GL_FRAGMENT_SHADER) {

}