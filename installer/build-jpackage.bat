del /Q ".\files\*.*"
copy "..\src\main\resources\splash.png" ".\files"
copy "..\target\*.jar" ".\files"
copy "..\LICENSE.md" ".\files"
cd files
ren "YAAS*.jar" "YAAS.jar"
cd ..
jpackage @build-jpackage-options.txt
del /Q ".\files\*.*"