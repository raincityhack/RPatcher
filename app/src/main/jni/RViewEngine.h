//
// Created by PCAditiaID29 on 1/28/2021.
//

#ifndef RPATCHER_RVIEWENGINE_H
#define RPATCHER_RVIEWENGINE_H

class RViewEngine {
private:
    JNIEnv *_env;
    jobject _view;
    jobject _canvas;

public:
    RViewEngine() {
        _env = nullptr;
        _view = nullptr;
        _canvas = nullptr;
    }

    RViewEngine(JNIEnv *env, jobject view, jobject canvas) {
        this->_env = env;
        this->_view = view;
        this->_canvas = canvas;
    }

    bool isValid() const {
        return (_env != nullptr && _view != nullptr && _canvas != nullptr);
    }

    int getWidth() const {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_view);
            jmethodID width = _env->GetMethodID(canvasView, "getWidth", "()I");
            return _env->CallIntMethod(_view, width);
        }
        return 0;
    }

    int getHeight() const {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_view);
            jmethodID width = _env->GetMethodID(canvasView, "getHeight", "()I");
            return _env->CallIntMethod(_view, width);
        }
        return 0;
    }
    void DrawFPS(float posX, float posY) {
        if (isValid()) {
            jclass canvasView = _env->GetObjectClass(_view);
            jmethodID drawtext = _env->GetMethodID(canvasView, "DrawFPS",
                                                   "(Landroid/graphics/Canvas;FF)V");
            _env->CallVoidMethod(_view, drawtext, _canvas, posX, posY);
        }
    }
};

#endif //RPATCHER_RVIEWENGINE_H
