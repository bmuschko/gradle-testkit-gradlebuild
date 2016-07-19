import spock.lang.Specification
import org.junit.Rule
import org.junit.rules.TemporaryFolder

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*

class HelloWorldPluginTest extends Specification {
    @Rule 
    final TemporaryFolder testProjectDir = new TemporaryFolder()

    def buildFile
    def externalBuildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
        
        externalBuildFile = System.properties['externalBuildFile']
        
        if (!externalBuildFile) {
            throw new IllegalStateException("Need to provide system property named 'externalBuildFile'")
        }
    }        
        
    def "external build prints hello world"() {
        given:
        buildFile << """
            plugins {
                id 'my.company.helloworld'
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments('invokeExternalBuild', "-PexternalBuildFile=$externalBuildFile")
            .forwardOutput()
            .withPluginClasspath()
            .build()

        then:
        result.output.contains('Hello World!')
        result.task(':invokeExternalBuild').outcome == SUCCESS
    }
}