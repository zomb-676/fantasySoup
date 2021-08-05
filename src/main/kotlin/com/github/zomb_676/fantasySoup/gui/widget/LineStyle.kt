package com.github.zomb_676.fantasySoup.gui.widget

import com.github.zomb_676.fantasySoup.utils.modResourcesLocation

object LineStyle :StyleWidgetContainer(modResourcesLocation("textures/gui/line_style/all_in_one_inventory.png")
        ,256,512){
    val icon = WidgetDescriptor("icon",22,245,16,16,this)
    val universalUi = WidgetDescriptor("universal_ui",0,0,164,131,this)
    val inventoryAlone = WidgetDescriptor("inventory_alone",0,140,168,83,this)
    val previewBar3D = WidgetDescriptor("preview_bar_3d",165,0,87,68,this)
    val fluidTank = WidgetDescriptor("fluid_tank",165,69,22,70,this)
    val itemSlot = WidgetDescriptor("item_slot",188,69,18,18,this)
    val itemSlotLarge = WidgetDescriptor("item_slot_large",207,96,24,23,this)
    val meterStyle1 = WidgetDescriptor("meter_style_1",233,69,18,18,this)
    val meterStyle1Fill = WidgetDescriptor("meter_style_1_fill",253,73,1,12,this)
    val meterStyle2 = WidgetDescriptor("meter_style_2",188,93,52,12,this)
    val meterStyle2Fill = WidgetDescriptor("meter_style_2_fill",241,93,2,5,this)
    val meterStyle3 = WidgetDescriptor("meter_style_3",189,106,52,9,this)
    val meterStyle3Fill = WidgetDescriptor("meter_style_3_fill",189,116,50,7,this)
    val buttonSmallFloat = WidgetDescriptor("button_small_float",189,125,12,12,this)
    val buttonSmallPressed = WidgetDescriptor("button_small_pressed",201,125,12,12,this)
    val buttonSmallHover = WidgetDescriptor("button_small_hover",169,195,12,12,this)
    val sliderRail = WidgetDescriptor("slider_rail",215,125,27,5,this)
    val sliderButton = WidgetDescriptor("slider_button",243,125,3,6,this)
    val textBar = WidgetDescriptor("text_bar",169,140,82,12,this)
    val textBarFocus = WidgetDescriptor("text_bar_focus",0,231,82,12,this)
    val deco1 = WidgetDescriptor("deco_1",0,224,152,6,this)
    val deco2 = WidgetDescriptor("deco_2",169,153,37,37,this)
    val deco3 = WidgetDescriptor("deco_3",208,154,37,37,this)
    val deco4 = WidgetDescriptor("deco_4",169,191,32,3,this)
    val buttonLongFloat = WidgetDescriptor("button_long_float",182,195,68,12,this)
    val buttonLongHover = WidgetDescriptor("button_long_hover",182,207,68,12,this)
    val buttonLongPressed = WidgetDescriptor("button_long_pressed",182,219,68,12,this)
    val checkboxBanned = WidgetDescriptor("checkbox_banned",169,232,12,12,this)
    val checkboxChecked = WidgetDescriptor("checkbox_checked",169,220,12,12,this)
    val checkboxEmpty = WidgetDescriptor("checkbox_empty",169,208,12,12,this)
    val widgetRotate = WidgetDescriptor("widget_rotate",159,272,10,10,this)
    val widgetRemove = WidgetDescriptor("widget_remove",159,261,10,10,this)
    val widgetAdd = WidgetDescriptor("widget_add",159,250,10,10,this)
    val widgetExport = WidgetDescriptor("widget_export",65,250,93,10,this)
    val widgetExportFocus = WidgetDescriptor("widget_export_focus",65,261,93,10,this)
    val widgetWarning = WidgetDescriptor("widget_warning",160,350,20,20,this)
    val widgetSave = WidgetDescriptor("widget_save",159,327,10,10,this)
    val widgetDiscard = WidgetDescriptor("widget_discard",159,338,10,10,this)
    val widgetNext = WidgetDescriptor("widget_next",159,305,10,10,this)
    val widgetLast = WidgetDescriptor("widget_last",159,316,10,10,this)
    val widgetImport = WidgetDescriptor("widget_import",159,294,10,10,this)
    val widgetAutoscale = WidgetDescriptor("widget_autoscale",160,283,10,10,this)
    val layerUp = WidgetDescriptor("layer_up",172,279,7,7,this)
    val layerDown = WidgetDescriptor("layer_down",172,287,7,7,this)
    val layerVisOn = WidgetDescriptor("layer_vis_on",172,263,7,7,this)
    val layerVisOff = WidgetDescriptor("layer_vis_off",172,271,7,7,this)
    val layerLockOn = WidgetDescriptor("layer_lock_on",172,247,7,7,this)
    val layerLockOff = WidgetDescriptor("layer_lock_off",172,255,7,7,this)
    val layerInfo = WidgetDescriptor("layer_info",84,231,66,11,this)
    val editorLayerBox = WidgetDescriptor("editor_layer_box",182,233,70,136,this)
    val editorLabelNormal = WidgetDescriptor("editor_label_normal",156,224,9,12,this)
    val editorLabelHover = WidgetDescriptor("editor_label_hover",156,236,9,12,this)
    val menuThreeDim = WidgetDescriptor("menu_three_dim",41,289,10,10,this)
    val menuDiv = WidgetDescriptor("menu_div",52,278,10,10,this)
    val menuMisc = WidgetDescriptor("menu_misc",52,289,10,10,this)
    val menuProcess = WidgetDescriptor("menu_process",52,267,10,10,this)
    val menuSlot = WidgetDescriptor("menu_slot",41,278,10,10,this)
    val menuCheckbox = WidgetDescriptor("menu_checkbox",41,267,10,10,this)
    val menuInput = WidgetDescriptor("menu_input",52,256,10,10,this)
    val menuText = WidgetDescriptor("menu_text",41,256,10,10,this)
    val menuSplit = WidgetDescriptor("menu_split",41,245,10,10,this)
    val menuButton = WidgetDescriptor("menu_button",52,245,10,10,this)
    val menuSlider = WidgetDescriptor("menu_slider",41,300,10,10,this)
    val menuMeter = WidgetDescriptor("menu_meter",52,300,10,10,this)
    val menuTank = WidgetDescriptor("menu_tank",41,311,10,10,this)
    val menuEnergy = WidgetDescriptor("menu_energy",52,311,10,10,this)
    val iconMissing = WidgetDescriptor("icon_missing",22,264,16,16,this)
    val iconStyleSelect = WidgetDescriptor("icon_style_select",0,244,20,58,this)
    val iconFocus = WidgetDescriptor("icon_focus",21,282,18,18,this)
}