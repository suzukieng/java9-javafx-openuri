# Java 9 / JavaFX Open URI Issue

Supplemental information for https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8198549

The issue is that if your app is registered for a URI scheme, the app must already be running,
otherwise it will not receive the event through the event handler set via `Desktop.setOpenURIHandler`.
If the app is not running yet, it will be launched, but the URI that caused the launch is lost,
rendering the feature useless.

## How to build

This projects needs to be built on a Mac with JDK 9. You can just run `build.sh`.

The steps in the build file are illustrated here for clarity:

Enforce Java 9 on Mac, clean output directories and compile application into class files:

```
$ export PATH=`/usr/libexec/java_home -v9`/bin:$PATH
$ rm -rf classes/* jar/* dist/*
$ javac -d classes src/com/example/Main.java
```

Create JAR file using `javapackager`:

```
$ javapackager -createJar -appclass com.example.Main -outdir jar -outfile example -srcdir classes
```

Create Mac app bundle using `javapackager` (required for registering app with a URI scheme).

Note that we override Info.plist with our own, containing the CFBundleURLKeys (`-BdropinResourcesRoot=.`)

Code signing is not required for this example to work, but you might have to allow running the app
via system security settings.

```
$ javapackager -deploy -verbose -native image -name "Open URI Example" -srcdir jar -appclass com.example.Main \
    -outdir dist -outfile Example \
    -Bmac.CFBundleIdentifier=com.example.openuri -Bmac.CFBundleVersion=1.0.0 \
    -nosign \
    -BdropinResourcesRoot=.
```

## How to test

Open the app via Terminal:

```
$ open javafx-openuri://hello
```

Or via a web browser:

```
<a href="javafx-openuri://hello">Open Application</a>
```

If it is not running, it will display *No URI opened* which illustrates the bug.

If the app is running, it will display the URI correctly.
