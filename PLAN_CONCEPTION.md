# Plan de Conception Détaillé - MCPlantator Mod

**Version Minecraft:** 1.20.1
**Loader:** Forge
**Plateforme cible:** CurseForge (Client & Serveur)

---

## Vue d'ensemble du projet

Le mod MCPlantator ajoute un système de culture de plantes spéciales via un établi botanique. Le premier contenu introduit une plante "Poudrier sauvage" qui produit de la poudre à canon (Gun Powder).

---

## Architecture du mod

### Structure des packages recommandée
```
src/main/java/com/mcplantator/
├── MCPlantator.java (classe principale du mod)
├── init/
│   ├── ModBlocks.java (enregistrement des blocs)
│   ├── ModItems.java (enregistrement des items)
│   └── ModRecipes.java (enregistrement des recettes)
├── blocks/
│   ├── BotanicalWorkbench.java (bloc établi botanique)
│   └── GunpowderCropBlock.java (bloc plante poudrier)
├── blockentity/
│   └── BotanicalWorkbenchBlockEntity.java (logique de l'établi)
├── items/
│   └── GunpowderSeedItem.java (item graine poudrière)
├── client/
│   └── screen/
│       └── BotanicalWorkbenchScreen.java (GUI de l'établi)
├── container/
│   └── BotanicalWorkbenchMenu.java (logique du conteneur)
└── recipes/
    ├── BotanicalRecipe.java (type de recette personnalisée)
    └── BotanicalRecipeSerializer.java (sérialisation des recettes)
```

---

## Liste exhaustive des tâches

### Phase 1: Configuration et Setup du projet

#### Tâche 1.1: Initialisation du projet Forge
- [ ] Télécharger Minecraft Forge MDK pour 1.20.1
- [ ] Décompresser dans le dossier du projet
- [ ] Configurer `build.gradle` avec:
  - Nom du mod: `mcplantator`
  - Mod ID: `mcplantator`
  - Version: `1.0.0`
  - Group: `com.mcplantator`
- [ ] Configurer `mods.toml` avec les métadonnées
- [ ] Exécuter `gradlew genIntellijRuns` (ou `genEclipseRuns`)
- [ ] Vérifier que le projet compile avec `gradlew build`

#### Tâche 1.2: Création de la classe principale
- [ ] Créer `MCPlantator.java` avec l'annotation `@Mod`
- [ ] Ajouter le constructeur avec l'event bus
- [ ] Enregistrer les écouteurs d'événements principaux
- [ ] Ajouter les logs de démarrage

---

### Phase 2: Création des Items

#### Tâche 2.1: Graine poudrière (Gunpowder Seed)
- [ ] Créer `ModItems.java` dans le package `init`
- [ ] Créer la classe `GunpowderSeedItem` extends `ItemNameBlockItem`
- [ ] Définir les propriétés de l'item:
  - Stackable: oui (64)
  - Creative tab: à définir
  - Lien vers le bloc de culture
- [ ] Enregistrer l'item dans `ModItems`
- [ ] Créer le fichier de langue `en_us.json`:
  ```json
  "item.mcplantator.gunpowder_seed": "Gunpowder Seed"
  ```
- [ ] Créer le fichier de langue `fr_fr.json`:
  ```json
  "item.mcplantator.gunpowder_seed": "Graine Poudrière"
  ```

**Fichier texture requis:** `gunpowder_seed.png` (voir section graphismes)

---

### Phase 3: Création des Blocs

#### Tâche 3.1: Bloc de culture Poudrier sauvage
- [ ] Créer `ModBlocks.java` dans le package `init`
- [ ] Créer `GunpowderCropBlock.java` extends `CropBlock`
- [ ] Implémenter les 8 phases de croissance (AGE de 0 à 7)
- [ ] Définir les propriétés du bloc:
  - Pas de collision
  - Destruction instantanée
  - Son: CROP
  - Pas de tool requis
- [ ] Configurer les conditions de plantation:
  - Sol valide: Farmland
  - Nécessite de la lumière (niveau > 8)
  - Nécessite de l'eau dans un rayon de 4 blocs
- [ ] Implémenter `getStateForAge()` pour les 8 états
- [ ] Implémenter `getBaseSeedId()` qui retourne `gunpowder_seed`
- [ ] Définir le butin (loot) à la récolte:
  - État mature (age 7): 1-3 graines + 1-2 Gun Powder
  - État immature: 1 graine
- [ ] Enregistrer le bloc dans `ModBlocks`
- [ ] Créer les fichiers de langue:
  ```json
  "block.mcplantator.gunpowder_crop": "Gunpowder Plant"
  ```

**Fichiers textures requis:**
- `gunpowder_crop_stage0.png` à `gunpowder_crop_stage7.png` (8 fichiers)

#### Tâche 3.2: Établi botanique (Botanical Workbench)
- [ ] Créer `BotanicalWorkbench.java` extends `Block`
- [ ] Implémenter `BlockEntityProvider` pour avoir un TileEntity
- [ ] Définir les propriétés du bloc:
  - Material: WOOD
  - Dureté: 2.5
  - Résistance: 3.0
  - Son: WOOD
  - Requiert une pioche pour récolter
- [ ] Implémenter `use()` pour ouvrir le GUI
- [ ] Créer la recette de craft dans un fichier JSON:
  ```json
  Recette à définir (planches + établi + autre?)
  ```
- [ ] Enregistrer le bloc dans `ModBlocks`
- [ ] Créer les fichiers de langue:
  ```json
  "block.mcplantator.botanical_workbench": "Botanical Workbench"
  ```

**Fichiers graphiques requis:**
- `botanical_workbench_top.png`
- `botanical_workbench_side.png`
- `botanical_workbench_front.png`

---

### Phase 4: Block Entity et Logique de l'Établi

#### Tâche 4.1: Block Entity de l'établi
- [ ] Créer `BotanicalWorkbenchBlockEntity.java` extends `BlockEntity`
- [ ] Implémenter l'interface `Container`
- [ ] Créer un inventaire de 3 slots:
  - Slot 0: Ingrédient supérieur (Gun Powder)
  - Slot 1: Ingrédient inférieur (Bone Meal)
  - Slot 2: Résultat (Gunpowder Seed) - lecture seule
- [ ] Implémenter la logique de vérification des recettes
- [ ] Mettre à jour le slot de résultat automatiquement
- [ ] Implémenter `saveAdditional()` et `load()` pour la sauvegarde NBT
- [ ] Enregistrer le type de Block Entity

#### Tâche 4.2: Menu/Container
- [ ] Créer `BotanicalWorkbenchMenu.java` extends `AbstractContainerMenu`
- [ ] Définir les slots avec leurs positions
- [ ] Implémenter la logique de shift-click
- [ ] Implémenter `stillValid()` pour la vérification de distance
- [ ] Enregistrer le type de menu

---

### Phase 5: Interface Graphique (GUI)

#### Tâche 5.1: Screen du GUI
- [ ] Créer `BotanicalWorkbenchScreen.java` extends `AbstractContainerScreen`
- [ ] Implémenter `renderBg()` pour le fond
- [ ] Implémenter `render()` pour les tooltips
- [ ] Définir les positions des slots visuellement:
  - Slot 1 (haut): Position centrale haute
  - Slot 2 (bas): Position centrale basse (1 slot d'écart)
  - Slot résultat: À droite
- [ ] Enregistrer le screen dans l'event client

**Fichier graphique requis:**
- `botanical_workbench_gui.png` (176x166 pixels standard)

---

### Phase 6: Système de Recettes

#### Tâche 6.1: Type de recette personnalisé
- [ ] Créer `BotanicalRecipe.java` implements `Recipe<Container>`
- [ ] Définir les champs:
  - Ingrédient haut (Ingredient)
  - Ingrédient bas (Ingredient)
  - Résultat (ItemStack)
- [ ] Implémenter `matches()` pour vérifier la recette
- [ ] Implémenter `assemble()` pour créer le résultat
- [ ] Créer `BotanicalRecipeSerializer.java`
- [ ] Enregistrer le type de recette et le serializer

#### Tâche 6.2: Définition des recettes en JSON
- [ ] Créer le fichier `data/mcplantator/recipes/gunpowder_seed.json`:
```json
{
  "type": "mcplantator:botanical",
  "ingredient_top": {
    "item": "minecraft:gunpowder"
  },
  "ingredient_bottom": {
    "item": "minecraft:bone_meal"
  },
  "result": {
    "item": "mcplantator:gunpowder_seed",
    "count": 1
  }
}
```

---

### Phase 7: Loot Tables et Drops

#### Tâche 7.1: Loot Table pour le Poudrier sauvage
- [ ] Créer `data/mcplantator/loot_tables/blocks/gunpowder_crop.json`
- [ ] Définir les conditions:
  - Si age = 7 (mature):
    - 1-3 graines poudrière
    - 1-2 Gun Powder
  - Si age < 7:
    - 1 graine poudrière
- [ ] Utiliser les fonctions de fortune si applicable

#### Tâche 7.2: Loot Table pour l'Établi botanique
- [ ] Créer `data/mcplantator/loot_tables/blocks/botanical_workbench.json`
- [ ] Définir le drop: 1 établi botanique

---

### Phase 8: Blockstates et Models

#### Tâche 8.1: Blockstate du Poudrier
- [ ] Créer `assets/mcplantator/blockstates/gunpowder_crop.json`
- [ ] Définir les 8 variants pour chaque age (0-7)
- [ ] Lier chaque age à son modèle correspondant

#### Tâche 8.2: Models des blocs de culture
- [ ] Créer 8 fichiers modèles:
  - `assets/mcplantator/models/block/gunpowder_crop_stage0.json` à `stage7.json`
- [ ] Utiliser le modèle `minecraft:block/cross` comme parent
- [ ] Lier chaque modèle à sa texture

#### Tâche 8.3: Blockstate de l'Établi botanique
- [ ] Créer `assets/mcplantator/blockstates/botanical_workbench.json`
- [ ] Définir le modèle avec orientation (facing)

#### Tâche 8.4: Models de l'Établi botanique
- [ ] Créer `assets/mcplantator/models/block/botanical_workbench.json`
- [ ] Définir les textures pour chaque face (top, sides, front)
- [ ] Créer le modèle item correspondant

---

### Phase 9: Models des Items

#### Tâche 9.1: Model de la graine
- [ ] Créer `assets/mcplantator/models/item/gunpowder_seed.json`
- [ ] Utiliser `minecraft:item/generated` comme parent
- [ ] Lier à la texture de la graine

#### Tâche 9.2: Model item de l'Établi
- [ ] Créer `assets/mcplantator/models/item/botanical_workbench.json`
- [ ] Utiliser le modèle de bloc comme parent

---

### Phase 10: Textures et Assets

#### Tâche 10.1: Création des textures (voir section graphismes ci-dessous)
- [ ] Créer toutes les textures listées
- [ ] Les placer dans `assets/mcplantator/textures/`
- [ ] Vérifier les dimensions (16x16 pour items/blocs standards)

#### Tâche 10.2: Fichier de langue français
- [ ] Compléter `assets/mcplantator/lang/fr_fr.json`

#### Tâche 10.3: Fichier de langue anglais
- [ ] Compléter `assets/mcplantator/lang/en_us.json`

---

### Phase 11: Creative Tab personnalisé

#### Tâche 11.1: Création du Creative Tab
- [ ] Créer un Creative Tab pour MCPlantator
- [ ] Ajouter tous les items/blocs du mod
- [ ] Définir l'icône (graine ou établi)
- [ ] Ajouter les traductions du nom du tab

---

### Phase 12: Tags

#### Tâche 12.1: Tags de blocs
- [ ] Créer `data/minecraft/tags/blocks/mineable/axe.json`
- [ ] Ajouter l'établi botanique
- [ ] Créer tag pour les plantes si nécessaire

#### Tâche 12.2: Tags d'items
- [ ] Créer les tags d'items nécessaires pour les recettes
- [ ] Ajouter tag "seeds" pour la graine

---

### Phase 13: Tests et Debug

#### Tâche 13.1: Tests en développement
- [ ] Lancer le client de développement
- [ ] Vérifier que le mod charge sans erreur
- [ ] Tester la création de l'établi botanique via crafting
- [ ] Tester l'ouverture du GUI de l'établi
- [ ] Tester la recette de la graine (Gun Powder + Bone Meal)
- [ ] Tester la plantation de la graine sur farmland
- [ ] Tester la croissance avec bone meal
- [ ] Tester la récolte à maturité
- [ ] Vérifier les drops (graines + Gun Powder)
- [ ] Tester la condition d'eau (retrait de l'eau = destruction)

#### Tâche 13.2: Tests des textures
- [ ] Vérifier l'affichage de toutes les textures
- [ ] Vérifier les 8 phases de croissance visuellement
- [ ] Vérifier le GUI de l'établi
- [ ] Vérifier les items dans l'inventaire

#### Tâche 13.3: Tests multijoueur
- [ ] Tester en environnement serveur dédié
- [ ] Vérifier la synchronisation client-serveur
- [ ] Tester avec plusieurs joueurs

---

### Phase 14: Optimisation et Polish

#### Tâche 14.1: Revue du code
- [ ] Nettoyer le code
- [ ] Ajouter des commentaires explicatifs
- [ ] Vérifier les conventions de nommage
- [ ] Optimiser les performances si nécessaire

#### Tâche 14.2: Documentation
- [ ] Créer un README.md pour le mod
- [ ] Documenter les recettes
- [ ] Créer des screenshots
- [ ] Préparer la description pour CurseForge

---

### Phase 15: Build et Distribution

#### Tâche 15.1: Build final
- [ ] Configurer les métadonnées finales dans `mods.toml`:
  - Description
  - Auteurs
  - URL du projet
  - Logo
- [ ] Exécuter `gradlew build`
- [ ] Vérifier le fichier JAR généré dans `build/libs/`
- [ ] Tester le JAR dans un environnement Minecraft propre

#### Tâche 15.2: Publication CurseForge
- [ ] Créer un compte CurseForge (si nécessaire)
- [ ] Créer la page du projet
- [ ] Upload du fichier JAR
- [ ] Définir les versions compatibles (1.20.1)
- [ ] Définir le type de release (Alpha/Beta/Release)
- [ ] Ajouter les screenshots
- [ ] Publier

---

## Détails techniques spécifiques

### Configuration de la plante Poudrier

```java
public class GunpowderCropBlock extends CropBlock {
    public static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.GUNPOWDER_SEED.get();
    }

    // Conditions de croissance identiques au blé
    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }
}
```

### Structure du GUI de l'Établi Botanique

```
┌────────────────────────────────────┐
│     Botanical Workbench            │
├────────────────────────────────────┤
│                                    │
│         [Slot 1]      →  [Result] │
│            ↓                       │
│         (espace)                   │
│            ↓                       │
│         [Slot 2]                   │
│                                    │
│  ──────────────────────────────    │
│  [Inventaire joueur]               │
└────────────────────────────────────┘
```

---

## SECTION GRAPHISMES - Guide étape par étape

### Outils nécessaires

**Option 1: Logiciel gratuit recommandé**
- **GIMP** (gratuit, open source)
  - Téléchargement: https://www.gimp.org/downloads/
  - Supporte la transparence (alpha channel)
  - Export en PNG

**Option 2: Logiciel en ligne**
- **Piskel** (éditeur pixel art en ligne)
  - URL: https://www.piskelapp.com/
  - Gratuit, pas d'installation
  - Spécialisé pour le pixel art

**Option 3: Éditeur spécialisé Minecraft**
- **BlockBench** (gratuit)
  - URL: https://www.blockbench.net/
  - Spécialisé pour les assets Minecraft
  - Très recommandé !

---

### Liste complète des textures à créer

#### Items (16x16 pixels)
1. **gunpowder_seed.png** - Graine poudrière

#### Blocs - Phases de culture (16x16 pixels, format cross)
2. **gunpowder_crop_stage0.png** - Pousse vient d'être plantée
3. **gunpowder_crop_stage1.png** - Petite pousse
4. **gunpowder_crop_stage2.png** - Pousse qui grandit
5. **gunpowder_crop_stage3.png** - Tiges visibles
6. **gunpowder_crop_stage4.png** - Plante plus haute
7. **gunpowder_crop_stage5.png** - Presque mature
8. **gunpowder_crop_stage6.png** - Quasi mature
9. **gunpowder_crop_stage7.png** - Complètement mature

#### Blocs - Établi botanique (16x16 pixels)
10. **botanical_workbench_top.png** - Vue du dessus
11. **botanical_workbench_side.png** - Côtés
12. **botanical_workbench_front.png** - Face avant

#### GUI (176x166 pixels)
13. **botanical_workbench_gui.png** - Interface de l'établi

#### Logo (optionnel, taille variable)
14. **logo.png** - Logo du mod (128x128 ou plus)

---

### Guide de création - Graine Poudrière (gunpowder_seed.png)

**Concept:** Une graine avec un aspect poudré/gris, rappelant la poudre à canon

**Étapes avec GIMP:**

1. **Créer un nouveau fichier**
   - Ouvrir GIMP
   - Fichier → Nouvelle image
   - Largeur: 16 pixels
   - Hauteur: 16 pixels
   - Options avancées → Remplir avec: Transparence
   - Cliquer OK

2. **Zoomer pour mieux voir**
   - Affichage → Zoom → 800% (ou plus)
   - Activer Affichage → Afficher la grille
   - Édition → Préférences → Grille par défaut → Espacement: 1px

3. **Dessiner la graine (pixel par pixel)**
   - Outil crayon (N) - Taille: 1 pixel
   - Forme de base (8x8 pixels centrés):

   ```
   Légende couleurs:
   # = noir/gris très foncé (#2C2C2C)
   @ = gris moyen (#6B6B6B)
   $ = gris clair (#9C9C9C)
   . = transparent

   Position des pixels (centré):

   ........
   ....##..
   ...#@@#.
   ...@$$@.
   ...@$$@.
   ...#@@#.
   ....##..
   ........
   ```

4. **Ajouter des détails**
   - Ajouter quelques pixels blancs (#E0E0E0) pour la brillance
   - Ajouter des pixels très foncés (#1A1A1A) pour les ombres
   - Suggestion: quelques points gris pour simuler la poudre

5. **Exporter la texture**
   - Fichier → Exporter sous
   - Nom: `gunpowder_seed.png`
   - Type: PNG
   - Dans les options d'export: Cocher "Sauver la couleur des pixels transparents"
   - Exporter

**Résultat attendu:** Une petite graine ovale de couleur grise avec un aspect poudreux

---

### Guide de création - Phases de culture (stage 0 à 7)

**Concept:** Progression visuelle d'une petite pousse à une plante mature avec aspect "explosif"

**Important:** Les textures de plantes utilisent le modèle "cross" (deux plans qui se croisent en X)

**Couleurs suggérées pour la plante:**
- Vert foncé pour les tiges: #2D5016
- Vert moyen: #4A7B25
- Vert clair: #6FA838
- Noir/gris pour les "capsules" de poudre: #3C3C3C
- Gris clair pour effet poudre: #8C8C8C

**Stage 0 - gunpowder_crop_stage0.png:**
```
Très petite pousse (2-3 pixels de hauteur)
Pixels centraux verts en bas
Aspect: vient juste de germer
```
Étapes:
1. Nouveau fichier 16x16 transparent
2. Dessiner 2-4 pixels verts en bas au centre
3. Forme en "V" ou petit "I"

**Stage 1 - gunpowder_crop_stage1.png:**
```
Pousse plus haute (4-5 pixels)
Début de tige visible
```

**Stage 2 - gunpowder_crop_stage2.png:**
```
Tige centrale avec petites feuilles
Hauteur: 6-7 pixels
```

**Stage 3 - gunpowder_crop_stage3.png:**
```
Tige bien visible avec feuilles sur les côtés
Hauteur: 8-9 pixels
```

**Stage 4 - gunpowder_crop_stage4.png:**
```
Plante plus fournie
Début d'apparition de petites "capsules" grises
Hauteur: 10-11 pixels
```

**Stage 5 - gunpowder_crop_stage5.png:**
```
Capsules de poudre visibles en haut
Plante bien développée
Hauteur: 12-13 pixels
```

**Stage 6 - gunpowder_crop_stage6.png:**
```
Capsules plus grosses et plus nombreuses
Couleur grise dominante en haut
Hauteur: 14 pixels
```

**Stage 7 - gunpowder_crop_stage7.png (MATURE):**
```
Plante à pleine taille (15-16 pixels)
Capsules grises bien visibles et mûres
Aspect "prêt à récolter"
Peut ajouter quelques pixels jaunes/beiges pour montrer la maturité
```

**Astuce:** Pour gagner du temps, créer stage0, puis le dupliquer et modifier pour créer stage1, etc.

**Process avec GIMP:**
1. Créer stage0.png
2. Fichier → Exporter sous → stage0.png
3. Modifier l'image (ajouter pixels)
4. Fichier → Exporter sous → stage1.png
5. Répéter pour tous les stages

---

### Guide de création - Établi botanique

**botanical_workbench_top.png (16x16):**
```
Concept: Vue du dessus, mélange de bois et de végétation
Suggestion:
- Base en bois clair (planches)
- Centre avec motif végétal ou alchimique
- Bordures en bois plus foncé
```

Couleurs suggérées:
- Bois clair: #9C7A4F
- Bois foncé: #6B4423
- Vert pour décor: #4A7B25

**botanical_workbench_side.png (16x16):**
```
Concept: Côté de l'établi, structure en bois
Suggestion:
- Haut: partie du dessus visible
- Centre: face latérale en bois avec texture
- Bas: pieds de l'établi
```

**botanical_workbench_front.png (16x16):**
```
Concept: Face avant distinctive, peut montrer des outils ou plantes
Suggestion:
- Base similaire aux côtés
- Centre: motif décoratif (mortier/pilon, plantes)
- Plus détaillé que les côtés
```

**Étapes création:**
1. Commencer par le top (vue la plus importante)
2. Créer les sides en s'inspirant du top
3. Créer le front en ajoutant des détails uniques
4. Assurer la cohérence des couleurs entre les faces

---

### Guide de création - GUI de l'établi

**botanical_workbench_gui.png (176x166 pixels):**

**Important:** Les GUIs Minecraft suivent un format standard 176x166

**Structure:**
```
┌────────────────────────────────────┐  ← 176 pixels de large
│ Titre: "Botanical Workbench"      │
├────────────────────────────────────┤
│                                    │
│     [18x18]           [18x18]     │  ← Slots (18x18 avec bordure)
│   Slot Haut           Résultat    │
│                                    │
│     [18x18]                        │
│   Slot Bas                         │
│                                    │
│  ─────────────────────────────── │
│  [Inventaire joueur 9x3]          │  ← Grille 9x3 standard
│  [Barre d'action 9x1]             │  ← Barre rapide
└────────────────────────────────────┘
                                       ↑ 166 pixels de haut
```

**Étapes avec GIMP:**

1. **Créer le fichier**
   - Nouveau fichier 176x166 pixels
   - Fond transparent

2. **Créer le fond**
   - Remplir avec gris moyen Minecraft: #C6C6C6
   - Ajouter bordure sombre (#555555) de 3-4 pixels tout autour

3. **Dessiner les slots**
   - Chaque slot: 18x18 pixels
   - Fond du slot: #8B8B8B
   - Bordure interne foncée (#373737) en haut/gauche
   - Bordure claire (#FFFFFF) en bas/droite (effet 3D)

4. **Positions des slots (en pixels depuis le coin haut-gauche):**
   - Slot Haut: X=56, Y=20
   - Slot Bas: X=56, Y=56
   - Slot Résultat: X=116, Y=38
   - Inventaire joueur: commence à Y=84
   - Barre rapide: commence à Y=142

5. **Ajouter une flèche de craft (optionnel)**
   - Entre les slots d'entrée et le résultat
   - Position: X=85, Y=35
   - Taille: 24x17 pixels
   - Couleur: #8B8B8B avec contour noir

6. **Détails décoratifs (optionnel)**
   - Ajouter des motifs végétaux dans les coins
   - Ajouter un motif de fond subtil
   - Garder cohérent avec les textures des blocs

7. **Export**
   - Exporter en PNG
   - Vérifier que les dimensions sont exactement 176x166

**Astuce:** Vous pouvez vous inspirer du GUI du crafting table vanilla de Minecraft comme base

---

### Ressources et références

**Pour voir les textures vanilla de Minecraft:**
1. Télécharger le JAR de Minecraft 1.20.1
2. Le JAR est un fichier ZIP, l'ouvrir avec un extracteur
3. Naviguer vers `assets/minecraft/textures/`
4. Observer les textures existantes pour cohérence stylistique

**Assets recommandés à observer:**
- `wheat_stage_0.png` à `wheat_stage_7.png` (pour les phases de culture)
- `wheat_seeds.png` (pour style des graines)
- `crafting_table_front.png` et autres faces (pour l'établi)
- `gui/container/generic_54.png` (pour comprendre les GUIs)

**Palette de couleurs Minecraft:**
Les textures Minecraft utilisent généralement:
- Peu de couleurs (4-8 couleurs par texture)
- Pas de dégradés lisses
- Contours sombres pour définir les formes
- Style pixel art pur (pas d'antialiasing)

---

### Checklist finale des graphismes

Avant de considérer les graphismes terminés:

- [ ] Toutes les textures sont en 16x16 pixels (sauf GUI)
- [ ] Le GUI est en 176x166 pixels
- [ ] Tous les fichiers sont en format PNG
- [ ] La transparence est correctement gérée (fond transparent)
- [ ] Les couleurs sont cohérentes entre les textures
- [ ] Le style correspond à Minecraft vanilla
- [ ] Les 8 phases de culture montrent une progression claire
- [ ] L'établi a 3 faces distinctes (top, side, front)
- [ ] Tous les fichiers ont le nom exact spécifié

---

### Organisation des fichiers graphiques

**Structure finale des dossiers de textures:**

```
src/main/resources/assets/mcplantator/textures/
├── item/
│   └── gunpowder_seed.png
├── block/
│   ├── gunpowder_crop_stage0.png
│   ├── gunpowder_crop_stage1.png
│   ├── gunpowder_crop_stage2.png
│   ├── gunpowder_crop_stage3.png
│   ├── gunpowder_crop_stage4.png
│   ├── gunpowder_crop_stage5.png
│   ├── gunpowder_crop_stage6.png
│   ├── gunpowder_crop_stage7.png
│   ├── botanical_workbench_top.png
│   ├── botanical_workbench_side.png
│   └── botanical_workbench_front.png
└── gui/
    └── botanical_workbench_gui.png
```

---

## Estimation de complexité

### Tâches critiques (bloquantes)
- Setup du projet Forge
- Création de l'établi botanique avec Block Entity
- Système de recettes personnalisées
- Création des textures de base

### Tâches moyennes
- Bloc de culture avec phases de croissance
- GUI et conteneur
- Loot tables
- Blockstates et models

### Tâches simples
- Items
- Fichiers de langue
- Tags
- Creative tab

---

## Extensions futures possibles

Une fois le mod de base fonctionnel, voici des idées d'extensions:

1. **Nouvelles plantes:**
   - Plante produisant du Redstone
   - Plante produisant du Glowstone
   - Plante produisant du Slime

2. **Améliorations de l'établi:**
   - Recettes à 3 ou 4 ingrédients
   - Temps de transformation
   - Effets de particules

3. **Génération dans le monde:**
   - Structures avec établis botaniques
   - Plantes sauvages naturelles

4. **Intégrations:**
   - Support JEI (Just Enough Items) pour voir les recettes
   - Support de Patchouli pour un livre de guide

---

## Points d'attention importants

### Compatibilité serveur
- Tester impérativement en environnement serveur dédié
- Vérifier les packets de synchronisation pour le GUI
- S'assurer que les Block Entities sont correctement synchronisés

### Performance
- Les Block Entities peuvent impacter les performances
- L'établi ne doit pas faire de tick inutiles
- Les plantes utilisent le système de random tick vanilla

### Sécurité
- Valider les entrées dans les recettes
- Limiter les stacks dans le GUI
- Vérifier que les joueurs ne peuvent pas duper des items

---

## Conclusion

Ce plan couvre l'intégralité du développement du mod MCPlantator, depuis la configuration initiale jusqu'à la publication sur CurseForge. Chaque phase est décomposée en tâches précises et actionnables.

**Ordre de développement recommandé:**
1. Phases 1-2 (Setup + Items)
2. Phase 10.1 (Créer les graphismes de base pour tester)
3. Phases 3-4 (Blocs et logique)
4. Phase 6 (Recettes)
5. Phases 5 (GUI)
6. Phases 7-9 (Assets finaux)
7. Phases 11-13 (Polish et tests)
8. Phases 14-15 (Finition et distribution)

**Temps estimé total:** Variable selon l'expérience, mais comptez plusieurs semaines pour un développeur débutant en modding Minecraft.

La section graphismes contient des instructions pas-à-pas très détaillées pour créer chaque texture, même sans expérience en pixel art. Utiliser BlockBench est fortement recommandé car il simplifie grandement le processus.
