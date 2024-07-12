package conventions


/**
 * Precompiled [android-feature-lib-convention.gradle.kts][conventions.Android_feature_lib_convention_gradle] script plugin.
 *
 * @see conventions.Android_feature_lib_convention_gradle
 */
public
class AndroidFeatureLibConventionPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("conventions.Android_feature_lib_convention_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
