#include <jni.h>
#include <string>

using namespace std;

string jstringToString(JNIEnv *env, jstring jStr);

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lesson79ndkregistration_fragments_LoginFragment_encrypt(
        JNIEnv* env,
        jobject /* this */, jstring p) {
    string password = jstringToString(env, p);
    string result = "";
    for (int i = 0; i < password.length(); ++i) {
        result += char(int(password[i]) + 3);
    }
    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lesson79ndkregistration_fragments_RegisterFragment_encrypt(
        JNIEnv* env,
        jobject /* this */, jstring p) {
    string password = jstringToString(env, p);
    string result = "";
    for (int i = 0; i < password.length(); ++i) {
        result += char(int(password[i]) + 3);
    }
    return env->NewStringUTF(result.c_str());
}

/*extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lesson79ndkregistration_fragments_LoginFragment_decrypt(
        JNIEnv* env,
        jobject *//* this *//*, jstring password) {
    string password = jstringToString(env, p);
    string result = "";
    for (int i = 0; i < password.length(); ++i) {
        result += char(int(password[i]) - 3);
    }
    return env->NewStringUTF(result.c_str());
}*/

string jstringToString(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char *)pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}