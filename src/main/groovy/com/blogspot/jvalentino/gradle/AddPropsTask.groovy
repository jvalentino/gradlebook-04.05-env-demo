package com.blogspot.jvalentino.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Adds properties together
 * @author jvalentino2
 */
@SuppressWarnings(['Println'])
class AddPropsTask extends DefaultTask {

    int sum = 0

    @TaskAction
    void perform() {
        Map props = System.getenv()

        sum = (props.alpha as Integer) + (props.bravo as Integer) +
                (props.charlie as Integer) + (props.delta as Integer)

        println "${props.alpha} + ${props.bravo} + ${props.charlie} " +
                "+ ${props.delta} = ${sum}"
    }
}
