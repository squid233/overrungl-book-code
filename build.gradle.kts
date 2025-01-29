plugins {
}

group = "io.github.squid233"
version = "1.0-SNAPSHOT"

val overrunglOs = System.getProperty("os.name")!!.let { name ->
    when {
        "FreeBSD" == name -> "freebsd"
        arrayOf("Linux", "SunOS", "Unit").any { name.startsWith(it) } -> "linux"
        arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) } -> "macos"
        arrayOf("Windows").any { name.startsWith(it) } -> "windows"
        else -> throw Error("Unrecognized platform $name. Please set \"overrunglOs\" manually")
    }
}
val overrunglArch = System.getProperty("os.arch")!!.let { arch ->
    when (overrunglOs) {
        "freebsd" -> "x64"
        "linux" -> if (arrayOf("arm", "aarch64").any { arch.startsWith(it) }) {
            if (arch.contains("64") || arch.startsWith("armv8")) "arm64" else "arm32"
        } else if (arch.startsWith("ppc")) "ppc64le"
        else if (arch.startsWith("riscv")) "riscv64"
        else "x64"

        "macos" -> if (arch.startsWith("aarch64")) "arm64" else "x64"
        "windows" -> if (arch.startsWith("aarch64")) "arm64" else "x64"
        else -> throw Error("Unrecognized platform $overrunglOs. Please set \"overrunglArch\" manually")
    }
}

extra["overrunglNatives"] = "natives-$overrunglOs-$overrunglArch"
