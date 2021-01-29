//
// Created by PCAditiaID29 on 1/28/2021.
//

#include "main.h"
#include <jni.h>
#include "RViewEngine.h"
#include "RViewDrawing.h"

RViewEngine rViewEngine;

extern "C"
JNIEXPORT void JNICALL
Java_com_rpatcher_pubgm_utils_LIBHandler_GetInitCanvasDrawing(JNIEnv *env, jclass clazz,
                                                              jobject r_view, jobject canvas) {
    rViewEngine = RViewEngine(env, r_view, canvas);
    if (rViewEngine.isValid()){
        MainDraw(rViewEngine);
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_rpatcher_pubgm_utils_LIBHandler_setBoolConfigPatcher(JNIEnv *env, jclass clazz, jint fitur,
                                                              jboolean about) {
    switch (fitur) {
        case 1:
            INSTANT_HIT = about;
            break;
        case 2:
            LESS_RECOIL = about;
            break;
        case 3:
            ANTI_SHAKE = about;
            break;
        case 4:
            FAST_PARACHUTE = about;
            break;
        case 5:
            FAST_SHOOT_INTERVAL = about;
            break;
        case 6:
            AIMBOT = about;
            break;
    }
}