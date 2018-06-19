mkdir("build");
mkdir("build/classes");

javac("src", "build/classes");
copy("res", "build/classes");

mkdir("dist");
jar("dist/nachocalendar.jar", "build/classes");

publish("dist")
