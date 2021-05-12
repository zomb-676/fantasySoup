package com.github.zomb_676.fantasySoup.dataGen

import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.ModelFile

abstract class IDataHandle(
    private val modId: String,
    val dataHandle: DataGeneratorHandle = DataGeneratorHandle(modId)
) {
    /**
     * late execute some tasks
     */
    val toInitTask = mutableListOf<()->Unit>()
     inner class ModelChecker  constructor(rootPath: String?) {
        private val rootPath = rootPath?.plus("/") ?: ""
        private val models: MutableList<Lazy<ModelFile.ExistingModelFile>> = mutableListOf()
        fun set(path: String): Lazy<ModelFile.ExistingModelFile> =
            lazy {
                ModelFile.ExistingModelFile(
                    ResourceLocation(modId, rootPath.plus(path)),
                    dataHandle.content.existingFileHelper
                )
            }
                .apply { models.add(this) }

        @Throws(IllegalStateException::class)
        fun checkAll() {
            models.forEach{it.value.assertExistence()}
        }
    }
}