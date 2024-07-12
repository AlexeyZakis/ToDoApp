package conventions


/**
 * Precompiled [jvm-convention.gradle.kts][conventions.Jvm_convention_gradle] script plugin.
 *
 * @see conventions.Jvm_convention_gradle
 */
public
class JvmConventionPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("conventions.Jvm_convention_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
