plugins {
    java
}

val overrunglVersion: String by rootProject
val overrunglNatives: String by rootProject.extra
val jomlVersion: String by rootProject

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.github.over-run:overrungl-bom:$overrunglVersion"))
    implementation("io.github.over-run:overrungl")
    implementation("io.github.over-run:overrungl-glfw")
    implementation("io.github.over-run:overrungl-openal")
    implementation("io.github.over-run:overrungl-opengl")
    implementation("io.github.over-run:overrungl-stb")
    runtimeOnly("io.github.over-run:overrungl-glfw::$overrunglNatives")
    runtimeOnly("io.github.over-run:overrungl-openal::$overrunglNatives")
    runtimeOnly("io.github.over-run:overrungl-stb::$overrunglNatives")
    implementation("io.github.over-run:overrungl-joml")
    implementation("org.joml:joml:$jomlVersion")
}
