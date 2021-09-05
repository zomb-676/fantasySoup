package com.github.zomb_676.fantasySoup.imGUI

import imgui.type.ImInt

operator fun ImInt.invoke(): Int = get()

operator fun ImInt.plus(imInt: ImInt) = ImInt(get()+imInt.get())

operator fun ImInt.plusAssign(imInt: ImInt) = set(get()+imInt.get())

