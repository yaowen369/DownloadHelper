precision highp float;
attribute vec3 aVertex;//顶点数组 3维坐标
attribute vec2 aTextureCoord;//纹理坐标数组,2维数组
uniform mat4 aMVP;
varying vec2 texture;//

void main(){

    gl_Position = aMVP * vec4(aVertex.xy, 0, 1.0);

    texture = aTextureCoord;
}

$$

//可以设置纹理
precision highp float;
uniform sampler2D aTextureUnit0;//纹理id
varying vec2 texture;//纹理坐标,在顶点中指定

void main(){
    gl_FragColor =  texture2D(aTextureUnit0, texture);
}