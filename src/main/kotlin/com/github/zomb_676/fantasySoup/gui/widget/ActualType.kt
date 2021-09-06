package com.github.zomb_676.fantasySoup.gui.widget

import com.github.zomb_676.fantasySoup.gui.widget.prototype.*
import com.github.zomb_676.fantasySoup.utils.rough
import java.io.File
import kotlin.reflect.KClass

/**
 * indicate all the widget types classified by its function
 */
enum class ActualType(private val typeName: String, widgetTypeInfo: KClass<out IWidgetTypeInfo>) {

    /**
     * only for decorations or split contents, like simple and some embellishments
     */
    DECORATIONS("decoration", Decoration::class),

    /**
     * button , has float , hover and pressed state
     * (when has pressed state , just like the [CHECKBOX])
     */
    BUTTON("button", Button::class),

    /**
     * only editable at edit mode , just for show constant info
     */
    TEXT("text", Text::class),

    /**
     * interactive widget , like the search bar
     */
    INPUT("input", Input::class),

    /**
     * has full float , hover and pressed state
     */
    CHECKBOX("checkbox", CheckBox::class),

    /**
     * working process
     */
    PROCESS("process", Process::class),

    /**
     * container
     */
    DIV("div", Div::class),

    /**
     * render world in gui
     */
    THREE_DIM("three_dim", ThreeDim::class),

    /**
     * unclassified
     */
    MISC("misc", Misc::class),

    /**
     * scroll bar , interactive widgets
     */
    SLIDER("slider", Slider::class),

    /**
     * gauge , show specific percentage info
     */
    METER("meter", Meter::class),

    /**
     * gauge , Specialized for liquid info
     */
    TANK("tank", Tank::class),

    /**
     * gauge , Specialized for energy info
     */
    ENERGY("energy", Energy::class),

    /**
     * style present icon
     */
    STYLE_ICON("style_icon", StyleIcon::class),

    /**
     * represents a style
     */
    OPERATION_ICON("operation_icon", OperationIcon::class),

    /**
     * TYPE_ICON
     */
    CATEGORY_ICON("category_icon", CategoryIcon::class),

    /**
     * item container
     */
    SLOT("slot", Slot::class),

    /**
     * multi slots , can work like inventory
     */
    MULTI_SLOT("multi_slot", MultiSlot::class);

//    UV_MAP("uv_map");

    val constructor = widgetTypeInfo.java.getConstructor(File::class.java)

    val roughName: String = typeName.rough()

    companion object {

        internal val strictMap: HashMap<String, ActualType> =
            hashMapOf(*values().map { it.typeName to it }.toTypedArray())
        private val roughMap: HashMap<String, ActualType> =
            hashMapOf(*values().map { it.roughName to it }.toTypedArray())

        fun strictMath(name: String): ActualType? = strictMap[name]

        fun roughMath(name: String): ActualType? = roughMap[name.rough()]

    }


}