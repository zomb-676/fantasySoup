#version 430

in vec2 texCord;

out vec4 color;

uniform layout(location = 1) sampler2D tex;

void main() {
    color = texture(tex, texCord);
}
