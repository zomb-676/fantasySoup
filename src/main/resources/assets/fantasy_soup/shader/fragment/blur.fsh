#version 430 core

in vec2 texCoord;

out vec4 clolr;

layout (location = 0) uniform vec2 BlurDir;
layout (location = 1) uniform float radius;
layout (location = 2) uniform sampler2D texture;
layout (location = 3) uniform vec2 oneTexel;

void main() {
    vec4 blurred = vec4(0.0);

    for (float r = -radius; r <= radius; r += 1.0) {
        blurred = blurred + texture2D(texture, texCoord + oneTexel * r * BlurDir);
    }

    clolr = vec4(blurred.rgb / (radius * 2.0 + 1.0), 1.0);
}