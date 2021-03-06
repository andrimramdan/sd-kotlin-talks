import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	idea
	`kotlin-project`
	`junit-project`
	`assertj-project`
	id("org.unbroken-dome.test-sets") version "3.0.1"
}

dependencies {

	val koinVersion = "2.1.6"
	implementation("org.koin:koin-core:$koinVersion")
	testImplementation("org.koin:koin-test:$koinVersion")
	testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

val integrationTestSuiteName = "integrationTest"

testSets {
	create(integrationTestSuiteName) {
		dirName = "it"
	}
}

tasks {

	withType<KotlinCompile>().configureEach {
		kotlinOptions.freeCompilerArgs += "-Xinline-classes"
	}

	withType<Test> {
		useJUnitPlatform()
		testLogging {
			showStandardStreams = true
			events(PASSED, SKIPPED, FAILED, STANDARD_OUT, STANDARD_ERROR)
		}
	}

	named<Test>(integrationTestSuiteName) {
		filter {
			includeTestsMatching("*IT")
		}
		val test by existing
		shouldRunAfter(test)
	}

	check {
		val integrationTest by existing
		dependsOn(integrationTest)
	}
}

idea {
	module {
		testSourceDirs.addAll(kotlin.sourceSets[integrationTestSuiteName].kotlin.srcDirs)
		testResourceDirs.addAll(kotlin.sourceSets[integrationTestSuiteName].resources.srcDirs)
	}
}
