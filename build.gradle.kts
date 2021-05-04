plugins {
	java
	application
    id ("org.openjfx.javafxplugin") version "0.0.9"
}

repositories {
	jcenter()
}

dependencies {
	// For cross-platform jar:
	runtimeOnly("org.openjfx:javafx-graphics:$javafx.version:linux")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation ("com.google.guava:guava:28.2-jre");

    // Use JUnit test framework
    testImplementation ("junit:junit:4.12");
}

javafx {
	version = "14"
	modules("javafx.controls", "javafx.fxml")
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