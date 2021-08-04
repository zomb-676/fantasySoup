## SPLIT (split)

Simple lines, help you to split contents apart.

------



## BUTTON (button)

Buttons have ***float***, ***hover*** and ***pressed*** state. Every button will bounce back after you click it.

Notice a button with ONLY ***float*** state is legal, ***hover*** and ***pressed*** state is optional.

------



## TEXT (text)

Only editable in *edit mode*, show constant info, tips and short description.

------



## INPUT (input)

Interactive widget, just like what you see in anvil. Used to get player's input from keyboard.

Notice an input with ONLY ***normal*** state is legal, ***focus*** state is optional.

------



## CHECKBOX (checkbox)

Widget can switch its state by click. Functions like a switch, but looks like a box. You can also draw some switch-like widget and pretend that is switch.

------



## PROCESS (process)

Process bar indicate the process are processing. Two states, ***initial*** and ***final***, are necessary to draw an legal *process* bar. Use independent frame to draw a more complex *process* bar.

------



## DIV (div)

An multi-use container can hold widgets together.

------



## THREE_DIM (three_dim)

An 3D renderer can render "world" in your GUI. Some 3D-like icons can be sort into this label, too.

------



## MISC (misc)

Miscellaneous label can hold everything that can't be sorted into any other labels. 

------



## SLIDER (slider)

Sliders can slide from one side to another. Must have two parts, ***rail*** and ***slider***.

------



## METER (meter)

Gauge provide visual information that indicates the states of your machine. Two states, ***initial*** and ***final***, are necessary to draw an legal *meter* bar. Use independent frame to draw a more complex *meter* bar.

------



## TANK (tank)

Gauge provide visual information that indicates amount the fluid in your machine.

------



## ENERGY (energy)

Gauge provide visual information that indicates the energy state in your machine.

------

| Label Name                             | Required State | Optional State  |
| -------------------------------------- | -------------- | --------------- |
| [SPLIT](##SPLIT \(split\))             | default        | none            |
| [BUTTON](##BUTTON \(button\))          | float          | hover, pressed  |
| [TEXT](##TEXT \(text\))                | default        | none            |
| [INPUT](##INPUT \(input\))             | default        | focus           |
| [CHECKBOX](##CHECKBOX \(checkbox\))    | default        | banned, checked |
| [PROCESS](##PROCESS \(process\))       | initial, final | none            |
| [DIV](##DIV \(div\))                   | default        | none            |
| [THREE DIM](##THREE_DIM \(three_dim\)) | default        | none            |
| [MISC](##MISC \(misc\))                | initial, final | none            |
| [SLIDER](##SLIDER \(slider\))          | rail, slider   | none            |
| [METER](##METER \(meter\))             | initial, final | none            |
| [TANK](##TANK \(tank\))                | default        | none            |
| [ENERGY](##ENERGY \(energy\))          | default        | none            |

