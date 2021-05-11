package com.github.zomb_676.fantasySoup.dataGen

import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.generators.ModelFile

abstract class IDataHandle(
    private val modId: String,
    val dataHandle: DataGeneratorHandle = DataGeneratorHandle(modId)
) {
    fun existModelHelper(rootPath: String? =null): ModelChecker {
        return ModelChecker(rootPath)
    }

    inner class ModelChecker(rootPath: String?) {
        private val rootPath = rootPath?.plus("/") ?: ""
        private val models: MutableList<ModelFile.ExistingModelFile> = mutableListOf()
        fun set(path: String): ModelFile.ExistingModelFile =
            ModelFile.ExistingModelFile(
                ResourceLocation(modId, rootPath.plus(path)),
                dataHandle.content.existingFileHelper
            )
                .apply { models.add(this) }

        @Throws(IllegalStateException::class)
        fun checkAll() {
            models.forEach(ModelFile.ExistingModelFile::assertExistence)
        }
    }
}