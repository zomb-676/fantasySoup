#version 430 compatibility

out vec2 f_Position;
out vec4 f_Color;

void main() {
    f_Position = gl_Vertex.xy;
    f_Color = gl_Color;

    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}