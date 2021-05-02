plugins {
	java
	application
    id ("org.openjfx.javafxplugin") version "0.0.9"
}

repositories {
	jcenter()
}

dependencies {
	/* for cross-platform jar: */
	runtimeOnly("org.openjfx:javafx-graphics:$javafx.version:linux")
	
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api 'org.apache.commons:commons-math3:3.6.1'

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation 'com.google.guava:guava:28.2-jre'

    // Use JUnit test framework
    testImplementation 'junit:junit:4.12'
}

javafx {
	version = "14"
	modules("javafx.controls", "javafx.xml")
}

application.mainClassName = "application.Main"

tasks.withType<Jar> {
	manifest {
		attributes["Main-Class"] = "application.Main"
		}
		
	from(sourceSets.main.get().output)
	dependsOn(configurations.runtimeClasspath)
	from({
		configurations["runtimeClasspath"].map { if(it.isDirectory) it else zipTree(it) }
	})
}