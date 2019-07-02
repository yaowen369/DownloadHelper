precision highp float;
attribute vec3 aVertex;//顶点数组,三维坐标
uniform mat4 aMVPMatrix;//mvp矩阵
void main(){
  gl_Position = aMVPMatrix * vec4(aVertex, 1.0);
}

$$
//有颜色 没有纹理
precision highp float;
uniform vec4 aColor;//颜色,4维向量
void main(){
  gl_FragColor = aColor;
}
