import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.GradleBuild
 
class HelloWorldPlugin implements Plugin<Project> {     
    void apply(Project project) {     
        def externalBuildFile = new File(project.getProperty('externalBuildFile'))
        
        project.task('invokeExternalBuild', type: GradleBuild) {
            buildFile = externalBuildFile
            tasks = ['helloWorld']
        }
    }
}