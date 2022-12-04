package com.blogspot.jvalentino.gradle

import org.gradle.api.Project
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.Specification
import spock.lang.Subject

class AddPropsTaskTestSpec extends Specification {

    @Subject
    AddPropsTask task
    
    def setup() {
        Project p = ProjectBuilder.builder().build()
        task = p.task('add', type:AddPropsTask)
    }
    
    void "test perform"() {
        given:
        GroovyMock(System, global:true)
        Map props = [
            'alpha':'1',
            'bravo':'2',
            'charlie':'3',
            'delta':'4'
        ]
        
        when:
        task.perform()
        
        then:
        1 * System.getenv() >> props
        
        and:
        task.sum == 10
         
    }
}
