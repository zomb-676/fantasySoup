## DECORATION (decoration)

Decorations make your UI looks better.

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



## STYLE_ICON (style_icon)

Icons, shows the style of your UI file

------



## TYPE_ICON (type_icon)

Icons of the editor menu. 

-----

| Label Name                                      | Required State | Optional State  |
| ----------------------------------------------- | -------------- | --------------- |
| <a href='#decoration-decoration'>DECORATION</a> | default        | none            |
| <a href='#button-button'>BUTTON</a>             | float          | hover, pressed  |
| <a href='#text-text'>TEXT</a>                   | default        | none            |
| <a href='#input-input'>INPUT</a>                | default        | focus           |
| <a href='#checkbox-checkbox'>CHECKBOX</a>       | default        | banned, checked |
| <a href='#process-process'>PROCESS</a>          | initial, final | none            |
| <a href='#div-div'>DIV</a>                      | default        | none            |
| <a href='#threedim-threedim'>THREE DIM</a>      | default        | none            |
| <a href='#misc-misc'>MISC</a>                   | initial, final | none            |
| <a href='#slider-slider'>SLIDER</a>             | rail, slider   | none            |
| <a href='#meter-meter'>METER</a>                | initial, final | none            |
| <a href='#tank-tank'>TANK</a>                   | default        | none            |
| <a href='#energy-energy'>ENERGY</a>             | default        | none            |
| <a href='#styleicon-styleicon'>STYLE_ICON</a>   | default        | none            |
| <a href='#typeicon-typeicon'>TYPE_ICON</a>      | default        | none            |

