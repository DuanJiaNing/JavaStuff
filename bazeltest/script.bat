# https://docs.bazel.build/versions/master/tutorial/java.html

bazel build //:ProjectRunner

# http://www.webgraphviz.com/
bazel query  --nohost_deps --noimplicit_deps "deps(//:ProjectRunner)" --output graph

# jar 中只有 Runner.class，无法脱机运行
bazel build //src/main/java/duan/cmdline:runner

# jar 中有 Runner.class 和 Greeting.class，可以脱机运行
bazel build //src/main/java/duan/cmdline:runner_deploy.jar