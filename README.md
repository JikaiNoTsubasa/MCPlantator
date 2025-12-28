# MCPlantator

**Mod Minecraft 1.20.1 - Forge**

Un mod qui ajoute un système de culture de plantes spéciales via un établi botanique.

---

## 🌱 Fonctionnalités

### Poudrier Sauvage
Cultivez votre propre poudre à canon !
- Plante avec **8 phases de croissance** (comme le blé)
- Nécessite **farmland** et **eau** à proximité
- Se récolte à maturité pour obtenir : **Gun Powder** + **Graines**
- Compatible avec **Bone Meal** et l'enchantement **Fortune**

### Établi Botanique
Un nouvel établi pour créer des graines de plantes spéciales.
- **Recette :** Planches + Crafting Table
- **Utilisation :** Gun Powder + Bone Meal = Graine Poudrière
- Interface graphique intuitive

---

## 📥 Installation

### Prérequis
- **Minecraft** 1.20.1
- **Forge** 47.2.0 ou supérieur
- **Java** 17

### Installation du mod
1. Télécharger le fichier JAR depuis [Releases](../../releases)
2. Installer Minecraft Forge 1.20.1
3. Placer le JAR dans le dossier `mods/` de Minecraft
4. Lancer Minecraft avec le profil Forge

---

## 🎮 Utilisation en jeu

### Fabriquer l'Établi Botanique
```
Planches  Planches  Planches
Planches   Table    Planches
Planches  Planches  Planches
```

### Créer des Graines Poudrières
1. Placer l'**Établi Botanique** dans le monde
2. Clic droit pour ouvrir l'interface
3. Mettre **Gun Powder** dans le slot du haut
4. Mettre **Bone Meal** dans le slot du bas
5. Récupérer la **Graine Poudrière** qui apparaît à droite

### Cultiver le Poudrier Sauvage
1. Préparer du **farmland** avec une houe
2. S'assurer qu'il y a de l'**eau** dans un rayon de 5 blocs
3. **Planter** la graine sur le farmland
4. Attendre la croissance (ou utiliser **Bone Meal**)
5. **Récolter** à maturité : obtenez Gun Powder + Graines !

---

## 🛠️ Développement

Voir [QUICK_START.md](QUICK_START.md) pour compiler et tester le mod.

### Structure du projet
- **[PLAN_CONCEPTION.md](PLAN_CONCEPTION.md)** - Plan de conception détaillé
- **[IMPLEMENTATION_STATUS.md](IMPLEMENTATION_STATUS.md)** - État d'avancement
- **[QUICK_START.md](QUICK_START.md)** - Guide de démarrage rapide

### Compiler le mod
```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

Le fichier JAR sera dans `build/libs/`

---

## 📋 État du projet

✅ **Code:** 100% complet
⚠️ **Textures:** En cours de création

Voir [IMPLEMENTATION_STATUS.md](IMPLEMENTATION_STATUS.md) pour plus de détails.

---

## 🎨 Contribuer

Les contributions sont les bienvenues !

### Textures
Le mod a besoin de textures graphiques. Voir :
- [TEXTURES_TODO.md](src/main/resources/assets/mcplantator/textures/TEXTURES_TODO.md)
- [PLAN_CONCEPTION.md](PLAN_CONCEPTION.md) - Section GRAPHISMES

### Code
1. Fork le projet
2. Créer une branche (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commit les changements (`git commit -m 'Ajout nouvelle fonctionnalité'`)
4. Push la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Ouvrir une Pull Request

---

## 📜 Licence

MIT License - Voir le fichier LICENSE pour plus de détails.

---

## 📞 Support

Pour toute question ou problème :
- Ouvrir une [Issue](../../issues)
- Consulter la documentation dans le dossier du projet

---

## 🙏 Remerciements

- Forge Team pour leur framework excellent
- La communauté Minecraft Modding

---

**Bon jeu ! 🚀**
