precision highp float;
attribute vec4 aVertex;//顶点数组 3维坐标
attribute vec2 aTextureCoord;//纹理坐标数组,2维数组
attribute vec3 aBearingTiltAlpha;//旋转角度，透明度
uniform mat4 aMVP;
uniform float aMapBearing;
varying vec2 texture;//
varying float alpha;

mat4 rotationMatrix(vec3 axis, float angle)
{
     axis = normalize(axis);
     float s = sin(angle);
     float c = cos(angle);
     float oc = 1.0 - c;
     return mat4(oc * axis.x * axis.x + c,           oc * axis.x * axis.y - axis.z * s,  oc * axis.z * axis.x + axis.y * s,  0.0,
                 oc * axis.x * axis.y + axis.z * s,  oc * axis.y * axis.y + c,           oc * axis.y * axis.z - axis.x * s,  0.0,
                 oc * axis.z * axis.x - axis.y * s,  oc * axis.y * axis.z + axis.x * s,  oc * axis.z * axis.z + c,           0.0,
                 0.0,                                0.0,                                0.0,                                1.0);
}


void main(){

    //旋转中心点
    vec2 center = aVertex.zw;

    //旋转 需要逆时针
    float marker_rotate = -aBearingTiltAlpha.x * 0.01745;//3.1415 * 0.0055556;//==>3.1415 / 180.0
    float map_tilt = aBearingTiltAlpha.y * 0.01745;//3.1415 * 0.0055556;//==>3.1415 / 180.0
    float map_rotate = -aMapBearing * 0.01745;

    //marker自身旋转
    mat4 marker_rotate_mat4 = rotationMatrix(vec3(0,0,1), marker_rotate);
    //tilt旋转矩阵
    mat4 map_tilt_mat4 = rotationMatrix(vec3(1,0,0), map_tilt);
    //bearing旋转矩阵
    mat4 map_rotate_mat4 = rotationMatrix(vec3(0,0,1), map_rotate);



    //移动到原点，旋转图片
    vec4 pos_1 = marker_rotate_mat4 * vec4(aVertex.xy - center.xy, 0,1);

    //让marker站立，tilt旋转之后z轴值有可能不是0
    vec4 pos_2 =  map_tilt_mat4 * pos_1;

    //旋转 bearing
    vec4 pos_3 =  map_rotate_mat4 * pos_2;

    //最后计算时再平移到对应的位置
    gl_Position = aMVP * vec4(pos_3.xy + center.xy, pos_3.z , 1.0);

    texture = aTextureCoord;
    alpha = aBearingTiltAlpha.z;
}

$$

//可以设置纹理
precision highp float;
uniform sampler2D aTextureUnit0;//纹理id
varying vec2 texture;//纹理坐标,在顶点中指定
varying float alpha;

void main(){
    gl_FragColor =  texture2D(aTextureUnit0, texture)*vec4(alpha,alpha,alpha,alpha);
}