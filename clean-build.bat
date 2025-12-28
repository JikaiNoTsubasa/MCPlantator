@echo off
echo Nettoyage et rebuild complet du projet...
echo.

echo 1. Nettoyage des fichiers de build...
call gradlew clean

echo.
echo 2. Rebuild du projet...
call gradlew build --stacktrace --info > build-log.txt 2>&1

echo.
echo Le log complet est dans build-log.txt
echo.
echo Dernières lignes du log:
tail -50 build-log.txt

pause
