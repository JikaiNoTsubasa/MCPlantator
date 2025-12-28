@echo off
echo Recherche de Java 17...
echo.

REM Chercher dans Program Files
if exist "C:\Program Files\Eclipse Adoptium" (
    echo Trouvé dans Eclipse Adoptium:
    dir "C:\Program Files\Eclipse Adoptium" /B
    echo.
)

if exist "C:\Program Files\Java" (
    echo Trouvé dans Java:
    dir "C:\Program Files\Java" /B | findstr /I "17"
    echo.
)

if exist "C:\Program Files\Microsoft" (
    echo Trouvé dans Microsoft:
    dir "C:\Program Files\Microsoft" /B | findstr /I "jdk"
    echo.
)

if exist "C:\Program Files\Azul" (
    echo Trouvé dans Azul:
    dir "C:\Program Files\Azul" /B
    echo.
)

if exist "C:\Program Files\AdoptOpenJDK" (
    echo Trouvé dans AdoptOpenJDK:
    dir "C:\Program Files\AdoptOpenJDK" /B
    echo.
)

echo.
echo Copiez le chemin complet d'un dossier jdk-17 ci-dessus
echo puis modifiez le fichier gradle.properties
echo.
pause
