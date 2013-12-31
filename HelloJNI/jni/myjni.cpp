#include <string.h>
#include <jni.h>
extern "C" {
	JNIEXPORT jstring JNICALL
	Java_com_hellojni_Hello_getMessage(JNIEnv *env, jobject obj)
	{
//		jstring str;
//		str=(*env)->NewStringUTF(env,"Khoa dai ca");
//		return str;
	return env->NewStringUTF( "Hello from C++ over JNI!");
	}
}
