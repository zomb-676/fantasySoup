package com.github.zomb_676.fantasySoup

import net.minecraft.state.EnumProperty
import net.minecraft.util.IStringSerializable

enum class EnumShaftMaterial(val maxSpeed: Int, val maxTorture: Int) : IStringSerializable {
    WOODEN(3577, 278) {
        override fun getString(): String = "wooden"
    },
    STONE(11561, 958) {
        override fun getString(): String = "stone"
    },
    STEEL(57005, 6711) {
        override fun getString(): String = "steel"
    },
    DIAMOND(4121484, 69508) {
        override fun getString(): String = "diamond"
    },
    BEDROCK(Int.MAX_VALUE, Int.MAX_VALUE) {
        override fun getString(): String = "bedrock"
    };

    companion object {
        val shaftMaterial: EnumProperty<EnumShaftMaterial> = EnumProperty.create(
            "shaft_material", EnumShaftMaterial::class.java, WOODEN, STONE, STEEL, DIAMOND, BEDROCK
        )
    }

}
