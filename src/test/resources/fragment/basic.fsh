#version 430

in vec2 texCord;

layout(location = 0) out vec4 color;

uniform layout(location = 1) sampler2D tex;

void main() {
    color = texture(tex, texCord);
}
