package com.github.zomb_676.fantasySoup.shader

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.recipebook.IRecipeUpdateListener
import net.minecraft.item.crafting.IRecipe
import net.minecraft.profiler.IProfiler
import net.minecraft.resources.IFutureReloadListener
import net.minecraft.resources.IReloadableResourceManager
import net.minecraft.resources.IResourceManager
import net.minecraftforge.fml.DatagenModLoader
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.resource.IResourceType
import net.minecraftforge.resource.ISelectiveResourceReloadListener
import net.minecraftforge.resource.VanillaResourceType
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.function.Predicate

object ShaderProcess {
    init {
        if (FMLEnvironment.dist.isClient and !DatagenModLoader.isRunningDataGen()){
            (Minecraft.getInstance().resourceManager as IReloadableResourceManager).addReloadListener{ _, manger, _, _, _, _->
                object :CompletableFuture<Void>(){

                }
            }
        }
    }
}