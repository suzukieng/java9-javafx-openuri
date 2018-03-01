#!/bin/sh
export PATH=`/usr/libexec/java_home -v9`/bin:$PATH
rm -rf classes/* jar/* dist/*
javac -d classes src/com/example/Main.java &&
    javapackager -createJar -appclass com.example.Main -outdir jar -outfile example -srcdir classes &&
    javapackager -deploy -verbose -native image -name "Open URI Example" -srcdir jar -appclass com.example.Main \
        -outdir dist -outfile Example \
        -Bmac.CFBundleIdentifier=com.example.openuri -Bmac.CFBundleVersion=1.0.0 \
        -nosign \
        -BdropinResourcesRoot=.
