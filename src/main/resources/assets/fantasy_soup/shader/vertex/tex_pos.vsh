#version 430 core

in vec3 pos;
in vec2 tex;

out vec2 position;
out vec2 texCoord;

void main() {
    gl_Position = vec4(pos/2 ,1);
    texCoord = tex;
}
