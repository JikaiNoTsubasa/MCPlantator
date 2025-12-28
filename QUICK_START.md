# MCPlantator - Guide de démarrage rapide

## ⚡ Installation et test rapide

### Prérequis
- **Java 17** installé
- Connexion internet (pour télécharger les dépendances)

### Étapes

#### 1. Télécharger Gradle Wrapper (première fois uniquement)
```bash
# Windows PowerShell
Invoke-WebRequest -Uri https://services.gradle.org/distributions/gradle-8.1.1-bin.zip -OutFile gradle.zip
Expand-Archive gradle.zip -DestinationPath .
Move-Item gradle-8.1.1/bin/gradle.bat ./gradlew.bat
Move-Item gradle-8.1.1/lib ./gradle
```

Ou téléchargez manuellement depuis : https://gradle.org/releases/

#### 2. Générer les configurations de développement
```bash
# Windows
gradlew.bat genIntellijRuns
# ou
gradlew.bat genEclipseRuns
# ou
gradlew.bat genVSCodeRuns

# Linux/Mac
./gradlew genIntellijRuns
```

#### 3. Lancer le client de test
```bash
# Windows
gradlew.bat runClient

# Linux/Mac
./gradlew runClient
```

#### 4. Build le mod (fichier JAR)
```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

Le fichier JAR sera dans `build/libs/mcplantator-1.0.0.jar`

---

## 🎨 Avant de tester

**IMPORTANT :** Le mod fonctionne mais nécessite les **textures graphiques** pour être visuellement complet.

### Textures manquantes
Les fichiers suivants doivent être créés dans `src/main/resources/assets/mcplantator/textures/` :

- **14 fichiers PNG** au total
- Voir [TEXTURES_TODO.md](src/main/resources/assets/mcplantator/textures/TEXTURES_TODO.md)
- Guide complet dans [PLAN_CONCEPTION.md](PLAN_CONCEPTION.md) section "GRAPHISMES"

Sans les textures, les blocs/items apparaîtront en **magenta/noir** (texture manquante).

---

## 🧪 Test en jeu

Une fois dans Minecraft :

1. **Ouvrir le Creative Mode**
2. **Onglet "MCPlantator"** dans le menu créatif
3. **Récupérer :**
   - Graine Poudrière (Gunpowder Seed)
   - Établi Botanique (Botanical Workbench)

### Test de l'établi botanique
1. Placer l'établi dans le monde
2. Clic droit pour ouvrir l'interface
3. Mettre **Gun Powder** dans le slot du haut
4. Mettre **Bone Meal** dans le slot du bas
5. ✅ Une **Graine Poudrière** apparaît à droite
6. Récupérer la graine

### Test de la plante
1. Utiliser une houe sur de la terre → farmland
2. Planter la **Graine Poudrière** sur le farmland
3. Vérifier qu'il y a de l'eau à moins de 5 blocs
4. **Attendre** ou utiliser **Bone Meal** pour accélérer
5. À maturité (8ème phase), **casser la plante**
6. ✅ Vous obtenez : **Gun Powder** + **Graines**

---

## 🔧 Commandes utiles

### Développement
```bash
# Nettoyer le build
gradlew clean

# Rebuild complet
gradlew clean build

# Lancer le serveur dédié
gradlew runServer

# Générer les données (datagen)
gradlew runData

# Rafraîchir les dépendances
gradlew --refresh-dependencies
```

### Debug
```bash
# Compiler sans lancer
gradlew classes

# Voir les tâches disponibles
gradlew tasks
```

---

## 📦 Installation dans Minecraft normal

1. **Build le mod :** `gradlew build`
2. Le JAR est dans `build/libs/`
3. **Installer Forge 1.20.1** (version 47.2.0+)
4. **Copier** le JAR dans le dossier `mods/` de Minecraft
5. **Lancer** Minecraft avec le profil Forge

---

## 🐛 Problèmes courants

### "Java version incompatible"
- Assurez-vous d'avoir **Java 17** installé
- Vérifier : `java -version`

### "Task failed"
- Supprimer le dossier `.gradle/`
- Relancer `gradlew build`

### "Cannot find symbol" (erreurs de compilation)
- Exécuter : `gradlew --refresh-dependencies`
- Vérifier que tous les fichiers Java sont présents

### Textures magenta/noir en jeu
- Les textures PNG n'ont pas été créées
- Voir [TEXTURES_TODO.md](src/main/resources/assets/mcplantator/textures/TEXTURES_TODO.md)

### Le GUI ne s'ouvre pas
- Vérifier les logs dans `logs/latest.log`
- Chercher les erreurs liées à `BotanicalWorkbench`

---

## 📚 Documentation

- **Plan complet :** [PLAN_CONCEPTION.md](PLAN_CONCEPTION.md)
- **État du projet :** [IMPLEMENTATION_STATUS.md](IMPLEMENTATION_STATUS.md)
- **Textures :** [TEXTURES_TODO.md](src/main/resources/assets/mcplantator/textures/TEXTURES_TODO.md)

---

## ✅ Checklist de développement

- [x] Code Java complet
- [x] Fichiers JSON (recipes, loot tables, models)
- [x] Fichiers de langue (FR/EN)
- [x] Tags Minecraft
- [x] Creative Tab
- [ ] **Textures graphiques** ⚠️
- [ ] Tests en jeu
- [ ] Build final
- [ ] Publication CurseForge

---

**Bon développement ! 🚀**
