#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_serialport_amos_cem_com_libamosserial_SerialPort_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
