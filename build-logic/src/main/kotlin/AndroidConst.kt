import org.gradle.api.JavaVersion

object AndroidConst {
    const val NAMESPACE = "com.example.todoapp"
    const val COMPILE_SKD = 34
    const val MIN_SKD = 26
    val COMPILE_JDK_VERSION = JavaVersion.VERSION_17
    val KOTLIN_JVM_TARGET = JavaVersion.VERSION_17.toString()
    const val KOTLIN_COMPILER_EXTENSION_VERSION = "1.5.14"
}
