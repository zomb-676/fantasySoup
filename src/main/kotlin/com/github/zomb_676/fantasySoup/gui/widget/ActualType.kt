package com.github.zomb_676.fantasySoup.gui.widget

import com.github.zomb_676.fantasySoup.utils.rough

/**
 * indicate all the widget types classified by its function
 */
enum class ActualType(private val typeName: String, private vararg val validAdditionDataTypes: AdditionDataType) {

    /**
     * only for decorations or split contents, like simple and some embellishments
     */
    DECORATIONS("decorations"),

    /**
     * button , has float , hover and pressed state
     * (when has pressed state , just like the [CHECKBOX])
     */
    BUTTON("button"),

    /**
     * only editable at edit mode , just for show constant info
     */
    TEXT("text", AdditionDataType.CONTENT_OFFSET),

    /**
     * interactive widget , like the search bar
     */
    INPUT("input", AdditionDataType.CONTENT_OFFSET),

    /**
     * has full float , hover and pressed state
     */
    CHECKBOX("checkbox"),

    /**
     * working process
     */
    PROCESS("process", AdditionDataType.FILL_AREA),

    /**
     * container
     */
    DIV("div", AdditionDataType.CONTENT_OFFSET),

    /**
     * render world in gui
     */
    THREE_DIM("three_dim", AdditionDataType.FILL_AREA),

    /**
     * unclassified
     */
    MISC("misc"),

    /**
     * scroll bar , interactive widgets
     */
    SLIDE("slide"),

    /**
     * gauge , show specific percentage info
     */
    METER("meter", AdditionDataType.POINT_OFFSET, AdditionDataType.ROTATION_POINT),

    /**
     * gauge , Specialized for liquid info
     */
    TANK("tank", AdditionDataType.FILL_AREA),

    /**
     * gauge , Specialized for energy info
     */
    ENERGY("energy", AdditionDataType.FILL_AREA),

    /**
     * style present icon
     */
    STYLE_ICON("style_icon"),

    /**
     * TYPE_ICON
     */
    TYPE_ICON("type_icon");

    val roughName: String = typeName.rough()

    fun containAdditionalDataType(additionDataType: AdditionDataType): Boolean =
        validAdditionDataTypes.contains(additionDataType)

    fun containAdditionalDataType(additionDataType: String) :Boolean=
        AdditionDataType.roughMath(additionDataType)?.let { containAdditionalDataType(it) } ?: false

    companion object {

        private val strictMap: HashMap<String, ActualType> =
            hashMapOf(*values().map { it.typeName to it }.toTypedArray())
        private val roughMap: HashMap<String, ActualType> =
            hashMapOf(*values().map { it.roughName to it }.toTypedArray())

        fun strictMath(name: String): ActualType? = strictMap[name]

        fun roughMath(name: String): ActualType? = roughMap[name]

    }


}