#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_yunarm_armyunclient_MyJni_stringFromYourJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++, from nativa jni!!!";
    return env->NewStringUTF(hello.c_str());
}
