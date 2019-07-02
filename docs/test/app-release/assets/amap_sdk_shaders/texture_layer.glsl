precision highp float;
attribute vec4 aVertex;//顶点数组 3维坐标
attribute vec2 aTextureCoord;//纹理坐标数组,2维数组
uniform mat4 aMVP;
uniform vec4 aTransform;
varying vec2 texture;//

void main(){
    //aVertex
    //由于20级坐标过大，转为float的时候会丢失精度，所以将其拆分为两部分。
    float x = (aVertex.x - aTransform.x) * 10000.0 + (aVertex.z - aTransform.z);
    float y = (aVertex.y - aTransform.y) * 10000.0 + (aVertex.w - aTransform.w);

    gl_Position = aMVP * vec4(x, y, 0, 1.0);

    texture = aTextureCoord;
}

$$

//可以设置纹理
precision highp float;
uniform sampler2D aTextureUnit0;//纹理id
uniform vec4 aColor; //外部传入颜色，主要为了对透明度进行处理
varying vec2 texture;//纹理坐标,在顶点中指定

void main(){
    gl_FragColor =  texture2D(aTextureUnit0, texture) * aColor;
}
