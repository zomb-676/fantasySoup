#version 430 core

//layout (location = 0) in dvec3 position;
//layout (location = 1) in dvec3 text_cord;
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 text_cord;

out vec2 texCord;

void main() {
//    gl_Position = vec4(float(position.x), float(position.y), float(position.z), 1);
    gl_Position = vec4(position, 1);
    texCord = vec2(float(text_cord.x), float(text_cord.y));
}
