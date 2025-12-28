@echo off
echo Installation du Gradle Wrapper pour MCPlantator...
echo.

REM Vérifier si Java est installé
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERREUR: Java n'est pas installe ou n'est pas dans le PATH
    echo Installez Java 17 depuis: https://adoptium.net/
    pause
    exit /b 1
)

echo Java detecte!
java -version
echo.

REM Créer le dossier gradle si nécessaire
if not exist "gradle\wrapper" mkdir gradle\wrapper

echo Telechargement du Gradle Wrapper...
echo.

REM Télécharger gradle-wrapper.jar
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle/wrapper/gradle-wrapper.jar'}"

REM Télécharger gradle-wrapper.properties
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.properties' -OutFile 'gradle/wrapper/gradle-wrapper.properties'}"

REM Créer gradlew.bat
echo @echo off> gradlew.bat
echo setlocal>> gradlew.bat
echo set DIRNAME=%%~dp0>> gradlew.bat
echo if "%%DIRNAME%%" == "" set DIRNAME=.>> gradlew.bat
echo set APP_BASE_NAME=%%~n0>> gradlew.bat
echo set APP_HOME=%%DIRNAME%%>> gradlew.bat
echo set DEFAULT_JVM_OPTS=>> gradlew.bat
echo set JAVA_EXE=java.exe>> gradlew.bat
echo set JAVA_OPTS=>> gradlew.bat
echo set GRADLE_OPTS=-Dorg.gradle.appname=%%APP_BASE_NAME%%>> gradlew.bat
echo "%%JAVA_EXE%%" %%DEFAULT_JVM_OPTS%% %%JAVA_OPTS%% %%GRADLE_OPTS%% "-Dorg.gradle.appname=%%APP_BASE_NAME%%" -classpath "%%APP_HOME%%\gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %%*>> gradlew.bat
echo endlocal>> gradlew.bat

echo.
echo Gradle Wrapper installe!
echo.
echo Prochaines etapes:
echo 1. gradlew genIntellijRuns (ou genEclipseRuns ou genVSCodeRuns)
echo 2. gradlew runClient (pour tester le mod)
echo 3. gradlew build (pour créer le fichier JAR)
echo.
pause
