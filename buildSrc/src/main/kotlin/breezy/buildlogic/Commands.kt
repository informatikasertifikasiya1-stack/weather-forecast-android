package breezy.buildlogic

import org.gradle.api.Project

// Git is needed in your system PATH for these commands to work.
// If it's not installed, you can return a random value as a workaround
fun Project.getCommitCount(): String {
    return runCommand("git rev-list --count HEAD", "0")
}

fun Project.getGitSha(): String {
    return runCommand("git rev-parse --short=8 HEAD", "unknown")
}

fun Project.runCommand(command: String, fallback: String): String {
    return try {
        val output = providers.exec {
            commandLine = command.split(" ")
            isIgnoreExitValue = true
        }.standardOutput.asText.get().trim()

        if (output.isEmpty()) fallback else output
    } catch (e: Exception) {
        fallback
    }
}
