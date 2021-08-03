package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.register.RegisterHandle.Companion.gerOrCreate
import com.github.zomb_676.fantasySoup.utils.*
import net.minecraft.client.KeyMapping
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.particles.ParticleType
import net.minecraft.sounds.SoundEvent
import net.minecraft.stats.StatType
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.memory.MemoryModuleType
import net.minecraft.world.entity.ai.sensing.SensorType
import net.minecraft.world.entity.ai.village.poi.PoiType
import net.minecraft.world.entity.decoration.Motive
import net.minecraft.world.entity.npc.VillagerProfession
import net.minecraft.world.entity.schedule.Activity
import net.minecraft.world.entity.schedule.Schedule
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.*
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.chunk.ChunkStatus
import net.minecraft.world.level.levelgen.carver.WorldCarver
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.StructureFeature
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacerType
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType
import net.minecraft.world.level.levelgen.placement.FeatureDecorator
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.Material
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.world.ForgeWorldType
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fmlclient.registry.ClientRegistry
import net.minecraftforge.fmllegacy.RegistryObject
import net.minecraftforge.registries.DataSerializerEntry
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.MarkerManager
import java.util.*

typealias ItemProperty = ((Item.Properties).() -> Item.Properties)
typealias BlockProperty = ((BlockBehaviour.Properties).() -> BlockBehaviour.Properties)

/**
 * singleton for each mod , core class for process all register stuffs .
 *
 * get the instance by [gerOrCreate]
 *
 * must call [registerAllRegistersToEvent]
 */
class RegisterHandle private constructor(private val modID: String, internal val modName: String) {
    companion object {
        internal val registerMarker = MarkerManager.getMarker("Register")

        /**
         * containers for store all created instances
         */
        private val instances = mutableMapOf<String, RegisterHandle>()

        /**
         * get/create the singleton instance
         */
        fun gerOrCreate(modID: String, modName: String): RegisterHandle =
            instances[modID] ?: RegisterHandle(modID, modName).apply { instances[modID] = this }
    }

    internal val registersHolder: RegisterHolder = RegisterHolder(modID)

    /**
     * class for store all DeferredRegisters
     */
    internal inner class RegisterHolder(modID: String) {
        // Game objects
        val blockRegister: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, modID)
        val fluidRegister: DeferredRegister<Fluid> = DeferredRegister.create(ForgeRegistries.FLUIDS, modID)
        val itemRegister: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, modID)
        val potionsRegister: DeferredRegister<Potion> = DeferredRegister.create(ForgeRegistries.POTIONS, modID)
        val soundEventRegister: DeferredRegister<SoundEvent> =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, modID)
//        val potionTypeRegister =
//            DeferredRegister.create(ForgeRegistries.POTION_TYPES, modID)
        val enchantmentRegister: DeferredRegister<Enchantment> =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, modID)
        val entityRegister: DeferredRegister<EntityType<*>> =
            DeferredRegister.create(ForgeRegistries.ENTITIES, modID)
        val tileEntityTypeRegister: DeferredRegister<BlockEntityType<*>> =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, modID)
        val particleTypeRegister: DeferredRegister<ParticleType<*>> =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, modID)
        val containerRegister: DeferredRegister<MenuType<*>> =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, modID)
        val paintingTypeRegister: DeferredRegister<Motive> =
            DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, modID)
        val recipeRegister: DeferredRegister<RecipeSerializer<*>> =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, modID)
        val attributeRegister: DeferredRegister<Attribute> =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, modID)
        val statTypeRegister: DeferredRegister<StatType<*>> =
            DeferredRegister.create(ForgeRegistries.STAT_TYPES, modID)

        //village
        val villagerProfessionRegister: DeferredRegister<VillagerProfession> =
            DeferredRegister.create(ForgeRegistries.PROFESSIONS, modID)
        val poiTypeRegister: DeferredRegister<PoiType> = DeferredRegister.create(ForgeRegistries.POI_TYPES, modID)
        val memoryModuleRegister: DeferredRegister<MemoryModuleType<*>> =
            DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, modID)
        val sensorTypeRegister: DeferredRegister<SensorType<*>> =
            DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, modID)
        val scheduleRegister: DeferredRegister<Schedule> = DeferredRegister.create(ForgeRegistries.SCHEDULES, modID)
        val activityRegister: DeferredRegister<Activity> = DeferredRegister.create(ForgeRegistries.ACTIVITIES, modID)

        //world gen
        val worldCarverRegister: DeferredRegister<WorldCarver<*>> =
            DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, modID)
        val surfaceBuilderRegister: DeferredRegister<SurfaceBuilder<*>> =
            DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, modID)
        val featureRegister: DeferredRegister<Feature<*>> = DeferredRegister.create(ForgeRegistries.FEATURES, modID)
        val featureDecoratorRegister: DeferredRegister<FeatureDecorator<*>> =
            DeferredRegister.create(ForgeRegistries.DECORATORS, modID)
        val chunkStatusRegister: DeferredRegister<ChunkStatus> =
            DeferredRegister.create(ForgeRegistries.CHUNK_STATUS, modID)
        val structureFeatureRegister: DeferredRegister<StructureFeature<*>> =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, modID)
        val blockStateProviderTypeRegister: DeferredRegister<BlockStateProviderType<*>> =
            DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, modID)
        val blockPlacerTypeRegister: DeferredRegister<BlockPlacerType<*>> =
            DeferredRegister.create(ForgeRegistries.BLOCK_PLACER_TYPES, modID)
        val foliagePlacerTypeRegister: DeferredRegister<FoliagePlacerType<*>> =
            DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, modID)
        val treeDecoratorTypeRegister: DeferredRegister<TreeDecoratorType<*>> =
            DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, modID)

        //dynamic/data driven
        val biomesRegister: DeferredRegister<Biome> = DeferredRegister.create(ForgeRegistries.BIOMES, modID)

        //custom forge
        val dataSerializerRegister: DeferredRegister<DataSerializerEntry> =
            DeferredRegister.create(ForgeRegistries.DATA_SERIALIZERS, modID)
        val lootModifierSerializerRegister: DeferredRegister<GlobalLootModifierSerializer<*>> =
            DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, modID)
        val worldTypeRegister: DeferredRegister<ForgeWorldType> =
            DeferredRegister.create(ForgeRegistries.WORLD_TYPES, modID)
    }

    /**
     * @param event[FMLJavaModLoadingContext.getModEventBus]
     *
     * mention that : use bus [Mod.EventBusSubscriber.Bus.MOD]
     */
    @Suppress("DuplicatedCode")
    fun registerAllRegistersToEvent(event: IEventBus) {
        registersHolder.blockRegister.register(event)
        registersHolder.fluidRegister.register(event)
        registersHolder.itemRegister.register(event)
        registersHolder.potionsRegister.register(event)
        registersHolder.soundEventRegister.register(event)
//        registersHolder.potionTypeRegister.register(event)
        registersHolder.enchantmentRegister.register(event)
        registersHolder.entityRegister.register(event)
        registersHolder.tileEntityTypeRegister.register(event)
        registersHolder.particleTypeRegister.register(event)
        registersHolder.containerRegister.register(event)
        registersHolder.paintingTypeRegister.register(event)
        registersHolder.recipeRegister.register(event)
        registersHolder.attributeRegister.register(event)
        registersHolder.statTypeRegister.register(event)
        registersHolder.villagerProfessionRegister.register(event)
        registersHolder.poiTypeRegister.register(event)
        registersHolder.memoryModuleRegister.register(event)
        registersHolder.sensorTypeRegister.register(event)
        registersHolder.scheduleRegister.register(event)
        registersHolder.activityRegister.register(event)
        registersHolder.worldCarverRegister.register(event)
        registersHolder.surfaceBuilderRegister.register(event)
        registersHolder.featureRegister.register(event)
        registersHolder.featureDecoratorRegister.register(event)
        registersHolder.chunkStatusRegister.register(event)
        registersHolder.structureFeatureRegister.register(event)
        registersHolder.blockStateProviderTypeRegister.register(event)
        registersHolder.blockPlacerTypeRegister.register(event)
        registersHolder.foliagePlacerTypeRegister.register(event)
        registersHolder.treeDecoratorTypeRegister.register(event)
        registersHolder.biomesRegister.register(event)
        registersHolder.dataSerializerRegister.register(event)
        registersHolder.lootModifierSerializerRegister.register(event)
        registersHolder.worldTypeRegister.register(event)
        FantasySoup.logger.debug(registerMarker, "add event to all DeferredRegisters for mod : $modName")
    }

    fun tab(labelName:String, icon:ItemStack): CreativeModeTab = object :CreativeModeTab(labelName){
        override fun makeIcon(): ItemStack = icon
    }

    fun keyBinding(keyMapping: KeyMapping)=ClientBlockRegisterEventHandle
        .addTask { ClientRegistry.registerKeyBinding(keyMapping) }

}