rootProject.name = "overrungl-book-code"

file("chapters").listFiles()?.forEach {
    val s = "chapters:${it.name}"
    include(s)
    project(":$s").projectDir = it
}
