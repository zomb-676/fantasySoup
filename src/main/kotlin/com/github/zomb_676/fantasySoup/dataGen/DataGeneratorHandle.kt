package com.github.zomb_676.fantasySoup.dataGen

import net.minecraft.block.Block
import net.minecraft.data.DataGenerator
import net.minecraft.item.Item
import net.minecraftforge.client.model.generators.*
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.common.data.ForgeRecipeProvider
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent

class DataGeneratorHandle(private val modId: String) {

    private val itemModels = mutableListOf<(ItemModelProvider) -> Unit>()
    private val blockModels = mutableListOf<(BlockModelProvider) -> Unit>()
    private val blockStates = mutableListOf<(BlockStateProvider) -> Unit>()
    private val customModelToAdd = mutableListOf<(BlockStateProvider)->Unit>()
    lateinit var content: Content

    fun init(event: GatherDataEvent) {
        content = Content(event, modId, handle = this)
    }

    class Content(
        event: GatherDataEvent,
        modId: String,
        generator: DataGenerator = event.generator,
        val existingFileHelper: ExistingFileHelper = event.existingFileHelper,
        handle: DataGeneratorHandle
    ) {
        private val blockModels: BlockModelProvider =
            object : BlockModelProvider(generator, modId, existingFileHelper) {
                override fun registerModels() {
                    handle.blockModels.forEach { it(this) }
                }
            }

        //    private val lootTables = mutableListOf<ForgeLootTableProvider>()
        private val itemModels: ItemModelProvider = object : ItemModelProvider(generator, modId, existingFileHelper) {
            override fun registerModels() {
                handle.itemModels.forEach { it(this) }
            }
        }
        private val blockStates = object : BlockStateProvider(generator, modId, existingFileHelper) {
            override fun registerStatesAndModels() {
                handle.customModelToAdd.forEach{it(this)}
                handle.blockStates.forEach { it(this) }
            }
        }
        private val recipes = object : ForgeRecipeProvider(generator) {}

        init {
            generator.addProvider(itemModels)
            generator.addProvider(blockModels)
            generator.addProvider(blockStates)
            generator.addProvider(recipes)
        }

    }

    fun <T : Item> itemModel(
        registryObject: RegistryObject<T>, model: ItemModelBuilder.(ItemModelProvider) -> Unit,
    ): (ItemModelProvider) -> Unit = fun(itemModelProvider: ItemModelProvider) {
        itemModelProvider.getBuilder(registryObject.get().registryName!!.path).model(itemModelProvider)
    }.apply { itemModels.add(this) }

    //    @OptIn(kotlin.contracts.ExperimentalContracts::class)
    fun <T : Block> blockModel(
        registryObject: RegistryObject<T>,
        model: BlockModelBuilder.(BlockModelProvider) -> Unit,
    ): (BlockModelProvider) -> Unit {
//        contract { callsInPlace(model , InvocationKind.EXACTLY_ONCE) }
        return fun(blockModelProvider: BlockModelProvider) {
            blockModelProvider.getBuilder(registryObject.get().registryName!!.path).model(blockModelProvider)
        }.apply { blockModels.add(this) }
    }

    fun <T : Block> multiPartBlockState(
        registryObject: RegistryObject<T>,
        model: MultiPartBlockStateBuilder.(BlockStateProvider) -> Unit
    ): (BlockStateProvider) -> Unit =
        fun(blockStateProvider: BlockStateProvider) {
            blockStateProvider.getMultipartBuilder(registryObject.get()).model(blockStateProvider)
        }.apply { blockStates.add(this) }

    fun <T : Block> variantBlockState(
        registryObject: RegistryObject<T>,
        model: VariantBlockStateBuilder.(BlockStateProvider) -> Unit
    ): (BlockStateProvider) -> Unit =
        fun(blockStateProvider: BlockStateProvider) {
            blockStateProvider.getVariantBuilder(registryObject.get()).model(blockStateProvider)
        }.apply { blockStates.add(this) }

    fun addBlockModel(model : (BlockStateProvider)->Unit){
        customModelToAdd.add(model)
    }
}