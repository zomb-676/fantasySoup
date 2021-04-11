#version 430 core

precision highp float;

layout(location = 0) uniform float Radius;
layout(location = 1) uniform vec2 u_CenterPos;
layout(location =2) uniform sampler2D sampler;

smooth in vec2 f_Position;
smooth in vec4 f_Color;

out vec4 fragColor;

void main() {
    float v = length(f_Position - u_CenterPos);

    if (v <= Radius){
        fragColor = f_Color * vec4(1.0, 1.0, 1.0, 0.0);
    } else {
        fragColor = texture(sampler,f_Position);
    }
}