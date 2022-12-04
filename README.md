

## 4.5 Environment Variables

The purpose of this project is to demonstrate how to access environment variables within Gradle, and how to deal with them in unit tesing.

 

#### src/main/groovy/com/blogspot/jvalentino/gradle/AddPropsTask.groovy

```groovy
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

```

**Line 17: environment variables**

To get a map of all environment variables, whether Windows or Linux based, **System.getenv()** can be used. Note though that these environment variables always come across as Strings, so they will need to be converted if needed.

 

**Lines 19-23: The logic**

The function of this task is to convert all of the environment variables to integers, and then to sum them.

 

#### plugin-tests/local/build.gradle

```groovy
buildscript {
  repositories {
	jcenter()
  }
  dependencies {
    classpath 'com.blogspot.jvalentino.gradle:env-demo:1.0.0'
  }
}

apply plugin: 'env-demo'

```

The **build.gradle** simply applies the plugin.

#### Manual Testing

```bash
plugin-tests/local$ export alpha=1
plugin-tests/local$ export bravo=2
plugin-tests/local$ export charlie=3
plugin-tests/local$ export delta=4
plugin-tests/local$ gradlew add 

> Task :add 
1 + 2 + 3 + 4 = 10


BUILD SUCCESSFUL

```

To manually test this plugin, the environment variables must first be set. The above demonstration is on Linux, but the same can be done on windows by replacing the **export** keyword with **set**.

 

#### src/test/groovy/com/blogspot/jvalentino/gradle/AddPropsTaskTestSpec.groovy

```groovy
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

```

**Lines 12-18: Standard setup**

The task is kept as a member variable, because it will be needed in every test method. The task is then instantiated using the **ProjectBuilder** fixture.

 

**Line 22: GroovyMock**

GroovyMock is used to handle replacing a class from a static perspective with a mock. Since **System.getenv()** is a static method, this is the best option for mocking this method return.

 

**Lines 23-27: The properties**

These are the properties values that are to be given as the environment variables

 

**Line 34: Mocking System**

Using the global mock in place for System, this statement expects a single call to **System.getenv(),** and when executing returns the property map created in the **given** clause.



