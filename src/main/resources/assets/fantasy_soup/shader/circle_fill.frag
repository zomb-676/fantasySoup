#version 460 core

precision highp float;

layout(location = 0) uniform float Radius;
layout(location = 1) uniform vec2 CenterPos;
layout(location =2) uniform sampler2D sampler;

smooth in vec2 f_Position;
smooth in vec4 f_Color;

out vec4 fragColor;

void main() {
    float v = length(f_Position - CenterPos);

    if (v <= Radius){
        fragColor = f_Color * vec4(0.9, 0.5, 0.5, 0.5);
    } else {
        fragColor = texture2D(sampler,f_Position);
    }
}