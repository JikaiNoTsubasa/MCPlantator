# Guide de Design GUI - Iron Extractor

Ce guide explique en détail comment fonctionne l'interface graphique de l'Iron Extractor pour créer la texture `iron_extractor.png`.

---

## 📐 Dimensions de la Texture

**Fichier à créer :** `src/main/resources/assets/mcplantator/textures/gui/iron_extractor.png`

**Dimensions :** **208 × 166 pixels**

La texture est divisée en deux zones :
- **Zone principale (0-176, 0-166)** : L'interface visible à l'écran
- **Zone des éléments animés (177+, 0+)** : Les sprites pour les barres de progression

---

## 🎨 Zone Principale (0-176 × 0-166)

### Layout général

```
┌─────────────────────────────────────────────────────────┐
│                    IRON EXTRACTOR                        │  ← Titre (centré)
│                                                           │
│                                                           │
│   [INPUT]      [ARROW] ────→      [OUT1]                │
│   Cobble                           [OUT2]                │
│                                    [OUT3]                │
│   [FUEL]       [FUEL BAR]                                │
│   Redstone        ↑                                      │
│                                                           │
├───────────────────────────────────────────────────────────┤
│  [PLAYER INVENTORY - 3 ROWS]                             │
│                                                           │
│  [HOTBAR - 9 SLOTS]                                      │
└───────────────────────────────────────────────────────────┘
```

### Positions exactes des éléments

#### 1. **Slots d'items** (18×18 pixels chacun)

| Slot | Type | Position X | Position Y | Description |
|------|------|------------|------------|-------------|
| Input | Cobblestone | 56 | 17 | Slot d'entrée (cobblestone uniquement) |
| Fuel | Redstone | 56 | 53 | Slot de carburant (redstone uniquement) |
| Output 1 | Iron Nugget | 116 | 17 | Premier slot de sortie |
| Output 2 | Iron Nugget | 116 | 35 | Deuxième slot de sortie |
| Output 3 | Iron Nugget | 116 | 53 | Troisième slot de sortie |

**Note :** Les slots font 18×18 pixels et ont généralement un cadre gris foncé (couleur #8B8B8B).

#### 2. **Flèche de progression** (animée)

- **Position à l'écran :** x=80, y=35
- **Taille maximale :** 24 pixels de largeur × 16 pixels de hauteur
- **Animation :** Se remplit de **gauche à droite**
- **Fonction :** Montre la progression du traitement (0-200 ticks = 0-10 secondes)

**Comment ça marche :**
```java
// Calcul dans IronExtractorMenu.java (ligne 79-84)
int progress = this.data.get(0);           // Progression actuelle (0-200)
int maxProgress = 200;                      // Temps total
int progressArrowSize = 24;                 // Largeur max de la flèche

// Largeur à dessiner (0-24 pixels)
int width = progress * 24 / 200;
```

**Rendu :**
```java
// Dans IronExtractorScreen.java (ligne 47)
guiGraphics.blit(TEXTURE,
    x + 80,        // Position X à l'écran
    y + 35,        // Position Y à l'écran
    176,           // X source dans la texture (début de la zone animée)
    0,             // Y source dans la texture
    progress,      // Largeur à dessiner (0-24) ← VARIABLE
    16);           // Hauteur fixe
```

#### 3. **Barre de fuel** (animée)

- **Position à l'écran :** x=56, y=36
- **Taille maximale :** 14 pixels de largeur × 14 pixels de hauteur
- **Animation :** Se vide de **haut en bas** (se remplit de **bas en haut**)
- **Fonction :** Montre le fuel restant (0-1600 ticks)

**Comment ça marche :**
```java
// Calcul dans IronExtractorMenu.java (ligne 87-92)
int fuelTime = this.data.get(1);           // Fuel restant (0-1600)
int maxFuelTime = this.data.get(2);        // Fuel max (1600)
int fuelBarSize = 14;                       // Hauteur max

// Hauteur à dessiner (0-14 pixels)
int height = fuelTime * 14 / 1600;
```

**Rendu :**
```java
// Dans IronExtractorScreen.java (ligne 55)
guiGraphics.blit(TEXTURE,
    x + 56,                           // Position X à l'écran
    y + 36 + (14 - fuelProgress),     // Position Y ← VARIABLE (monte de bas en haut)
    176,                              // X source dans la texture
    14 + (14 - fuelProgress),         // Y source ← VARIABLE
    14,                               // Largeur fixe
    fuelProgress);                    // Hauteur à dessiner (0-14) ← VARIABLE
```

#### 4. **Inventaire du joueur**

- **Inventaire principal (3 lignes) :** Position (8, 84) - 9 slots par ligne, espacement 18px
- **Hotbar (1 ligne) :** Position (8, 142) - 9 slots, espacement 18px

---

## 🎬 Zone des Sprites Animés (177+, 0+)

Cette zone contient les sprites utilisés pour les animations.

### Layout de la zone animée

```
Position dans la texture :
┌────────────────────────────────────┐
│ (0,0)              (177,0)         │
│                    ↓               │
│  ZONE VISIBLE      ZONE ANIMÉE     │
│                    ┌──────┬────┐   │
│                    │ARROW │    │   │  ← Y=0
│                    │24×16 │    │   │
│                    ├──────┤    │   │
│                    │FUEL  │    │   │  ← Y=16
│                    │14×14 │    │   │
│                    └──────┴────┘   │
│                    ↑               │
│                   X=177            │
└────────────────────────────────────┘
```

### Détail des sprites

#### **1. Flèche de progression**
- **Position source :** (177, 0)
- **Taille :** 24 pixels (largeur) × 16 pixels (hauteur)
- **Design :** Flèche pointant vers la droite, remplie progressivement

**Exemple de design :**
```
Vide :    [          ]  ← Contour seulement
25% :     [██        ]  ← Rempli à 25%
50% :     [████      ]  ← Rempli à 50%
75% :     [██████    ]  ← Rempli à 75%
100% :    [████████  ]  ← Complètement rempli
```

**Important :** Le jeu dessine de gauche à droite, pixel par pixel. Votre sprite doit être complet (24px de large) et le jeu coupera automatiquement selon la progression.

#### **2. Barre de fuel**
- **Position source :** (177, 16)
- **Taille :** 14 pixels (largeur) × 14 pixels (hauteur)
- **Design :** Barre verticale qui se remplit de bas en haut

**Fonctionnement technique :**
Le jeu dessine la barre en commençant par le **bas**. Quand il y a moins de fuel, il dessine moins de pixels depuis le bas.

```
Fuel plein (14px) :
┌──┐
│██│ ← Y source = 14 (haut de la barre)
│██│
│██│
│██│
│██│
│██│
│██│ ← Y source = 28 (bas de la barre)
└──┘

Fuel à 50% (7px) :
┌──┐
│  │ ← Non dessiné
│  │
│  │
│  │
│██│ ← Y source = 21 (commence ici)
│██│
│██│
│██│ ← Y source = 28 (bas)
└──┘
```

**Couleurs recommandées :**
- Redstone : Rouge (#FF0000) ou rouge foncé (#DC143C)
- Dégradé : Rouge vif en bas → Rouge sombre en haut
- Bordure : Gris foncé (#3C3C3C)

---

## 🎨 Recommandations de Design

### Palette de couleurs suggérée

**Fond de l'interface :**
- Gris clair : #C6C6C6
- Gris moyen : #8B8B8B
- Gris foncé : #555555
- Noir : #000000

**Slots :**
- Fond : #8B8B8B
- Bordure intérieure : #373737
- Bordure extérieure : #FFFFFF (effet 3D)

**Flèche de progression :**
- Fond (vide) : Transparent ou gris très clair
- Rempli : Orange (#FF6600) ou vert (#00FF00)
- Bordure : Noir (#000000)

**Barre de fuel (redstone) :**
- Plein : Rouge vif (#FF0000)
- Vide : Gris foncé (#373737)
- Bordure : Noir (#000000)

### Style visuel

**Option 1 : Style Minecraft Vanilla**
- Design plat avec bordures simples
- Couleurs sobres (gris)
- Effet 3D léger (bordures claires en haut/gauche, foncées en bas/droite)

**Option 2 : Style Industriel**
- Aspect métallique
- Boulons/rivets aux coins
- Textures métalliques
- Éclairage/ombres plus marqués

**Option 3 : Style High-Tech**
- Lignes néon
- Indicateurs lumineux
- Écran digital
- Couleurs vives (bleu électrique, vert néon)

---

## 📊 Exemple de Template

Voici un template ASCII pour visualiser les positions :

```
176px wide × 166px tall
┌────────────────────────────────────────────────────────────────────┐
│  X: 0                                                     X: 176   │
│ Y:0                                                                │
│     ┌─────────────────────── IRON EXTRACTOR ────────────────┐     │
│     │                                                        │     │
│  17 │   @56     ARROW      @116                            │     │
│     │  [▓▓▓]    ────→      [   ] OUT1                      │     │
│  35 │                      [   ] OUT2                       │     │
│     │  [▓▓▓]    FUEL       [   ] OUT3                      │     │
│  53 │   @56      ↑                                          │     │
│     │           BAR                                         │     │
│  84 ├───────────────────────────────────────────────────────┤     │
│     │  PLAYER INVENTORY (3 ROWS × 9 SLOTS)                 │     │
│ 142 │  HOTBAR (9 SLOTS)                                    │     │
│ 166 └──────────────────────────────────────────────────────┘     │
└────────────────────────────────────────────────────────────────────┘

@ = Position des slots/éléments
```

---

## 🔧 Données Techniques

### ContainerData (synchronisation client/serveur)

Le système utilise 3 valeurs synchronisées :

```java
data.get(0) → progress    // Progression du traitement (0-200 ticks)
data.get(1) → fuelTime    // Fuel restant (0-1600 ticks)
data.get(2) → maxFuelTime // Fuel maximum (1600 ticks)
```

### Calculs des animations

**Flèche (horizontal, gauche → droite) :**
```
Largeur affichée = (progress / 200) × 24
Exemples :
  progress = 0   → largeur = 0px   (vide)
  progress = 50  → largeur = 6px   (25%)
  progress = 100 → largeur = 12px  (50%)
  progress = 200 → largeur = 24px  (plein)
```

**Fuel (vertical, bas → haut) :**
```
Hauteur affichée = (fuelTime / maxFuelTime) × 14
Position Y = base + (14 - hauteur)

Exemples :
  fuelTime = 1600 → hauteur = 14px, Y = 36  (plein)
  fuelTime = 800  → hauteur = 7px,  Y = 43  (50%)
  fuelTime = 0    → hauteur = 0px,  Y = 50  (vide)
```

---

## 💡 Conseils pour Photoshop/GIMP

1. **Créez un canvas de 208×166 pixels**
2. **Créez un calque de repères** avec les positions exactes
3. **Utilisez des guides** à x=176 et x=177 pour séparer les zones
4. **Testez in-game régulièrement** pour vérifier l'alignement
5. **Exportez en PNG** sans compression
6. **Vérifiez la transparence** si vous utilisez des effets

### Checklist avant export

- [ ] Dimensions : 208×166 pixels
- [ ] Zone visible : 0-176 pixels (largeur)
- [ ] Tous les slots alignés (18×18px)
- [ ] Flèche de progression à (177, 0), taille 24×16
- [ ] Barre de fuel à (177, 16), taille 14×14
- [ ] Format PNG-8 ou PNG-24
- [ ] Pas de calques cachés
- [ ] Sauvegardé dans `textures/gui/iron_extractor.png`

---

## 🎯 Résumé Visuel

```
TEXTURE: 208×166 pixels

ZONE PRINCIPALE (0-176, 0-166):
  ├─ Background général
  ├─ Slot Input (56, 17)
  ├─ Slot Fuel (56, 53)
  ├─ Slots Output (116, 17/35/53)
  ├─ Emplacement flèche (80, 35)
  ├─ Emplacement fuel bar (56, 36)
  ├─ Inventaire joueur (8, 84)
  └─ Hotbar (8, 142)

ZONE SPRITES (177-208, 0-166):
  ├─ Flèche progression (177, 0) - 24×16
  └─ Barre fuel (177, 16) - 14×14
```

---

## 🚀 Bon design !

Avec ces informations, vous pouvez créer une texture GUI professionnelle et parfaitement alignée pour l'Iron Extractor !

**Rappel :** Une fois la texture créée, placez-la dans :
```
src/main/resources/assets/mcplantator/textures/gui/iron_extractor.png
```
