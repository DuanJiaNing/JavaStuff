
java_binary(
    name = "ProjectRunner",
    srcs = ["src/main/java/duan/ProjectRunner.java"],
    main_class = "duan.ProjectRunner",
    deps = [":greeter"],
)

java_library(
    name = "greeter",
    srcs = ["src/main/java/duan/Greeting.java"],
    visibility=["//src/main/java/duan/cmdline:__pkg__"]
)