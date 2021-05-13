package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.capability.ICapability
import com.github.zomb_676.fantasySoup.capability.InnerClass
import com.github.zomb_676.fantasySoup.safeReturn
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.Attribute
import net.minecraft.entity.item.PaintingType
import net.minecraft.fluid.Fluid
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipeSerializer
import net.minecraft.particles.ParticleType
import net.minecraft.potion.Effect
import net.minecraft.potion.Potion
import net.minecraft.stats.StatType
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.SoundEvent
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import java.util.*


/**
 * Singleton for one mod
 */
class RegisterHandle private constructor(val modID: String) {
    private val fluidRegister: DeferredRegister<Fluid> = DeferredRegister.create(ForgeRegistries.FLUIDS, modID)
    private val potionsRegister: DeferredRegister<Effect> = DeferredRegister.create(ForgeRegistries.POTIONS, modID)
    private val soundEventRegister: DeferredRegister<SoundEvent> =
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, modID)
    private val potionTypeRegister: DeferredRegister<Potion> = DeferredRegister.create(ForgeRegistries.POTION_TYPES, modID)
    private val enchantmentRegister: DeferredRegister<Enchantment> =
        DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, modID)
    private val entityRegister: DeferredRegister<EntityType<*>> = DeferredRegister.create(ForgeRegistries.ENTITIES, modID)
    private val particleTypeRegister: DeferredRegister<ParticleType<*>> =
        DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, modID)
    private val containerRegister: DeferredRegister<ContainerType<*>> =
        DeferredRegister.create(ForgeRegistries.CONTAINERS, modID)
    private val paintingTypeRegister: DeferredRegister<PaintingType> =
        DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, modID)
    private val recipeRegister: DeferredRegister<IRecipeSerializer<*>> =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, modID)
    private val attributeRegister: DeferredRegister<Attribute> = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, modID)
    private val statTypeRegister: DeferredRegister<StatType<*>> =
        DeferredRegister.create(ForgeRegistries.STAT_TYPES, modID)
    private val tileEntityTypeRegister: DeferredRegister<TileEntityType<*>> =
        DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, modID)
    private val blockRegister: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, modID)
    private val itemRegister: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, modID)


    companion object {
        private val map: HashMap<String, RegisterHandle> = hashMapOf()

        fun getInstance(modID: String): RegisterHandle {
            if (modID.length < 64 && modID.map { it.isUpperCase() }.isEmpty()) {
                throw IllegalArgumentException("$modID is not valid , it must length than 64 and all lowercase")
            } else {
                return map[modID].safeReturn { RegisterHandle(modID).also { map[modID] = it } }
            }
        }

        @Throws(ClassCastException::class)
        @Suppress("UNCHECKED_CAST")
        inline fun <reified T : Any> registerCapability(capability: ICapability<T>) {
            val contextClass: Class<out Any> = capability::class.java.getAnnotation(InnerClass::class.java).value.java
            try {
                CapabilityManager.INSTANCE.register(
                    (contextClass as Class<T>),
                    capability.storageGuide(),
                    capability.factory()
                )
            } catch (e: ClassCastException) {
                FantasySoup.logger.fatal("")
                throw e
            }
        }


    }

    fun itemGroup(name: String, iconItem: Item): ItemGroup {
        return object : ItemGroup(name) {
            override fun createIcon(): ItemStack {
                return ItemStack(iconItem)
            }
        }

    }

    fun <T:Container> container(containerBuilder : ContainerType.IFactory<T>): ContainerType<T> {
        val containerType = ContainerType(containerBuilder)
        this.containerRegister.register("",){containerType}
        return containerType
    }

    fun <T:Container> ContainerType<T>.register(): ContainerType<T> {
        return this
    }

    fun registerAllRegistersToEvent(event: IEventBus) {
        fluidRegister.register(event)
        potionsRegister.register(event)
        soundEventRegister.register(event)
        potionTypeRegister.register(event)
        enchantmentRegister.register(event)
        entityRegister.register(event)
        particleTypeRegister.register(event)
        containerRegister.register(event)
        paintingTypeRegister.register(event)
        recipeRegister.register(event)
        attributeRegister.register(event)
        statTypeRegister.register(event)
        tileEntityTypeRegister.register(event)
        blockRegister.register(event)
        itemRegister.register(event)
    }

    fun registerItem() = ItemRegisterInstance(this, itemRegister)
    fun registerTileEntityType() = TileEntityTypeRegisterInstance(this, tileEntityTypeRegister)
    fun registerBlock() = BlockRegisterInstance(this, blockRegister)


}

