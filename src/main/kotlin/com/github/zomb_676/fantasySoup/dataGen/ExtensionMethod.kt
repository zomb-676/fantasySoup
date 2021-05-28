package com.github.zomb_676.fantasySoup.dataGen

import com.github.zomb_676.fantasySoup.register.BlockItemPair
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraftforge.client.model.generators.*
import net.minecraftforge.fml.RegistryObject

fun <T : ModelBuilder<T>> ModelProvider<T>.existFile(path: String): ModelFile = getExistingFile(mcLoc(path))
fun ItemModelProvider.itemGenerated() = existFile("item/generated")
fun ItemModelProvider.itemHandheld() = existFile("item/handheld")
//fun ItemModelProvider.builtinEntity() = existFile("builtin/entity")
fun builtinEntity() = ModelFile.UncheckedModelFile("builtin/entity")
fun BlockModelProvider.blockCubeAll() = existFile("block/cube_all")
fun BlockModelProvider.blockCube() = existFile("block/cube")
fun ItemModelBuilder.multiTexture(vararg texture: String) =
    (1..texture.size).forEach {
        this.texture("layer${it - 1}", texture[it - 1])
    }


// for item model

fun <T : Item> RegistryObject<T>.generatedModelWithOneTexture(
    handle: IDataHandle,
    texture: String
): RegistryObject<T> {
    handle.dataHandle.itemModel(this) {
        parent(it.itemGenerated()).texture("layer0", texture)
    }
    return this
}

// for ISTER item  from vanilla
fun <T : Item> RegistryObject<T>.isterModel(handle: IDataHandle): RegistryObject<T> {
    handle.dataHandle.itemModel(this){
        parent(builtinEntity())
            .transforms()
            .transform(ModelBuilder.Perspective.GUI)
            .rotation(30f, 45f, 0f)
            .scale(0.625f)
            .end()
            .transform(ModelBuilder.Perspective.GROUND)
            .translation(0f, 3f, 0f)
            .scale(0.25f)
            .end()
            .transform(ModelBuilder.Perspective.HEAD)
            .rotation(0f, 180f, 0f)
            .end()
            .transform(ModelBuilder.Perspective.FIXED)
            .rotation(0f, 180f, 0f)
            .scale(0.5f)
            .end()
            .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT)
            .rotation(75f, 315f, 0f)
            .translation(0f, 2.5f, 0f)
            .scale(0.375f)
            .end()
            .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT)
            .rotation(0f, 315f, 0f)
            .scale(0.4f)
            .end()
            .end()
    }
    return this
}

// specialized for block item model
fun <T : Item> RegistryObject<T>.specifyParentFile(
    handle: IDataHandle,
    path : ()->String
): RegistryObject<T> {
    handle.dataHandle.itemModel(this) {
        parent(ModelFile.UncheckedModelFile(path()))
    }
    return this
}

fun <T : Item> RegistryObject<T>.heldModelWithOneTexture(handle: IDataHandle, texture: String): RegistryObject<T> {
    handle.dataHandle.itemModel(this) {
        parent(it.itemHandheld()).texture("layer0", texture)
    }
    return this
}


fun <T : Item> RegistryObject<T>.generatedModelModelWithMultiTexture(
    handle: IDataHandle,
    vararg texture: String
): RegistryObject<T> {
    handle.dataHandle.itemModel(this) {
        parent(it.itemGenerated()).multiTexture(*texture)
    }
    return this
}

//for block model

fun <T : Block> RegistryObject<T>.allSameModel(handle: IDataHandle, path: String): () -> () -> BlockModelBuilder? {
    var modelFile: () -> BlockModelBuilder? = { null }
    handle.dataHandle.blockModel(this) {
        modelFile = { parent(it.blockCubeAll()).texture("all", path) }.apply { invoke() }
    }
    return { modelFile }
}

fun <T : Block> RegistryObject<T>.allDifferModel(
    handle: IDataHandle,
    down: String,
    up: String,
    north: String,
    south: String,
    west: String,
    east: String
): () -> () -> BlockModelBuilder? {
    var modelFile: () -> () -> BlockModelBuilder? = { { null } }
    handle.dataHandle.blockModel(this) {
        modelFile = {
            {
                parent(it.blockCube()).texture("down", down).texture("up", up).texture("north", north)
                    .texture("south", south).texture("west", west).texture("east", east)
            }
        }
    }
    return modelFile
}

fun <B : Block> RegistryObject<B>.setCustomModel(
    handle: IDataHandle,
    model: (IDataHandle) -> Lazy<(BlockStateProvider) -> Unit>
): RegistryObject<B> {
    handle.toInitTask.add { handle.dataHandle.addBlockModel { model.invoke(handle).value } }
    return this
}

// for block state

fun <T : Block> RegistryObject<T>.noVariantState(
    handle: IDataHandle,
    modelFile: () -> () -> ModelFile?
): RegistryObject<T> {
    handle.dataHandle.variantBlockState(this) {
        this.addModels(partialState(), ConfiguredModel(modelFile()()!!))
    }
    return this
}

//for BlockItem

fun <T : BlockItem, B:Block> BlockItemPair<T, B>.useBlockModel(handle: IDataHandle , modID:String): BlockItemPair<T, B> {
    this.item.specifyParentFile(handle) { modID+":block/"+block.get().registryName!!.path }
    return this
}
// add support for pair BlockItemPair.class