# Guide d'Import de Modèle 3D Blockbench

Ce guide explique comment créer et importer un modèle 3D pour l'**Iron Extractor** (ou tout autre bloc custom) depuis Blockbench.

---

## Prérequis

- **Blockbench** installé : [Télécharger ici](https://www.blockbench.net/)
- Votre mod MCPlantator configuré et fonctionnel

---

## Étape 1 : Créer le Modèle dans Blockbench

### 1.1 Ouvrir Blockbench
1. Lancez **Blockbench**
2. Cliquez sur **File > New > Java Block/Item**
3. Configurez votre projet :
   - **File name** : `iron_extractor`
   - **Model Identifier** : `mcplantator:block/iron_extractor`
   - **Texture size** : 32x32 ou 64x64 (selon vos besoins)

### 1.2 Créer votre Modèle 3D
1. Utilisez les outils de Blockbench pour créer votre modèle :
   - **Add Cube** : Ajouter des cubes
   - **Rotate** : Faire pivoter
   - **Scale** : Redimensionner
   - **Position** : Positionner précisément

2. **Conseils pour l'Iron Extractor** :
   - Base solide (partie inférieure)
   - Corps principal (milieu)
   - Partie supérieure avec animation potentielle
   - Gardez le modèle dans un bloc 16x16x16 pour qu'il s'aligne bien avec la grille Minecraft

3. **Orientation** :
   - **Face avant (NORTH)** : C'est la face qui sera orientée vers le joueur lors du placement
   - Ajoutez des détails visuels sur la face avant (indicateurs, panneaux, etc.)

### 1.3 Appliquer les Textures
1. Allez dans l'onglet **Paint** ou **Texture**
2. Créez une nouvelle texture : **Create Texture**
   - Nom : `iron_extractor`
   - Résolution : 32x32 ou 64x64
3. Assignez la texture aux faces de vos cubes
4. Peignez directement dans Blockbench ou importez une image externe

### 1.4 Créer la Version "Allumée" (LIT)
1. Dupliquez votre modèle : **File > Save As**
   - Nom : `iron_extractor_on`
2. Modifiez les textures pour la version allumée :
   - Ajoutez des effets lumineux
   - Changez les couleurs (rouge/orange pour "actif")
3. Dans le menu **Display**, vous pouvez ajouter des particules d'émission de lumière

---

## Étape 2 : Exporter depuis Blockbench

### 2.1 Exporter le Modèle OFF (Éteint)
1. **File > Export > Java Block/Item**
2. Sauvegardez dans :
   ```
   E:\DEV\MinecraftMods\MCPlantator\src\main\resources\assets\mcplantator\models\block\iron_extractor.json
   ```

### 2.2 Exporter le Modèle ON (Allumé)
1. Ouvrez le fichier `iron_extractor_on.bbmodel`
2. **File > Export > Java Block/Item**
3. Sauvegardez dans :
   ```
   E:\DEV\MinecraftMods\MCPlantator\src\main\resources\assets\mcplantator\models\block\iron_extractor_on.json
   ```

### 2.3 Exporter les Textures
1. **File > Export > Export Texture**
2. Sauvegardez dans :
   ```
   E:\DEV\MinecraftMods\MCPlantator\src\main\resources\assets\mcplantator\textures\block\iron_extractor.png
   ```
3. Répétez pour la texture "on" si différente :
   ```
   E:\DEV\MinecraftMods\MCPlantator\src\main\resources\assets\mcplantator\textures\block\iron_extractor_on.png
   ```

---

## Étape 3 : Vérifier les Fichiers JSON

### 3.1 Structure des Fichiers
Vérifiez que vous avez bien ces fichiers :

```
src/main/resources/assets/mcplantator/
├── blockstates/
│   └── iron_extractor.json          ← Déjà créé
├── models/
│   ├── block/
│   │   ├── iron_extractor.json      ← À importer depuis Blockbench
│   │   └── iron_extractor_on.json   ← À importer depuis Blockbench
│   └── item/
│       └── iron_extractor.json      ← Déjà créé
└── textures/
    └── block/
        ├── iron_extractor.png       ← À exporter depuis Blockbench
        └── iron_extractor_on.png    ← À exporter depuis Blockbench (optionnel)
```

### 3.2 Vérifier le Blockstate (Déjà créé)
Le fichier `blockstates/iron_extractor.json` est déjà configuré pour utiliser les deux modèles selon l'orientation et l'état (lit/éteint).

### 3.3 Vérifier le Modèle Exporté
Ouvrez `models/block/iron_extractor.json` et assurez-vous que les chemins de texture sont corrects :

```json
{
  "parent": "minecraft:block/block",
  "textures": {
    "texture": "mcplantator:block/iron_extractor",
    "particle": "mcplantator:block/iron_extractor"
  },
  "elements": [
    // ... vos éléments de cube générés par Blockbench
  ]
}
```

---

## Étape 4 : Créer la Texture GUI (Interface)

L'Iron Extractor a besoin d'une texture pour son interface graphique.

### 4.1 Dimensions du GUI
Créez une image PNG de **176x166 pixels** :
- Fond de l'interface : zone principale
- Slots : 18x18 pixels chacun
- Barre de progression : ~24 pixels de hauteur
- Indicateur de fuel : ~14 pixels de hauteur

### 4.2 Layout Recommandé
```
- Input slot (cobblestone)  : Position (56, 17)
- Fuel slot (redstone)      : Position (56, 53)
- Output slots (5x)         : Position (116, 17) + décalage vertical de 18px
- Progress arrow            : Position (80, 35) - 16x24 pixels
- Fuel indicator            : Position (57, 37) - 14x14 pixels
```

### 4.3 Sauvegarder la Texture
Sauvegardez votre texture GUI dans :
```
src/main/resources/assets/mcplantator/textures/gui/iron_extractor.png
```

**Template de GUI** : Vous pouvez utiliser la texture du four Minecraft comme base et la modifier.

---

## Étape 5 : Compiler et Tester

### 5.1 Build le Mod
```bash
# Windows
gradlew.bat build

# Linux/Mac
./gradlew build
```

### 5.2 Lancer le Client de Test
```bash
# Windows
gradlew.bat runClient

# Linux/Mac
./gradlew runClient
```

### 5.3 Tester en Jeu
1. Lancez Minecraft avec votre mod
2. Craftez l'Iron Extractor avec la recette :
   ```
   Iron  Iron  Iron
   Iron  Fur   Iron
   Iron  Pis   Iron
   ```
   (Iron = Iron Ingot, Fur = Furnace, Pis = Piston)

3. Placez le bloc dans le monde
4. Vérifiez :
   - Le modèle 3D s'affiche correctement
   - L'orientation fonctionne (rotation selon le placement)
   - Le clic droit ouvre l'interface
   - L'état "lit" s'active quand il fonctionne

---

## Étape 6 : Ajustements et Debugging

### 6.1 Problèmes Courants

**Le modèle ne s'affiche pas** :
- Vérifiez les chemins dans les fichiers JSON
- Vérifiez que les textures sont au bon endroit
- Regardez les logs Minecraft pour les erreurs de chargement de modèle

**Le modèle est trop grand/petit** :
- Ajustez les positions des cubes dans Blockbench
- Gardez tout dans une grille 16x16x16

**Les textures sont manquantes (texture violette/noire)** :
- Vérifiez le chemin dans le fichier JSON du modèle
- Assurez-vous que le fichier PNG existe
- Format : `mcplantator:block/iron_extractor` → `textures/block/iron_extractor.png`

**Le bloc ne s'oriente pas correctement** :
- Vérifiez le blockstate JSON
- Assurez-vous que les valeurs `y` de rotation sont correctes (0, 90, 180, 270)

**Le GUI ne s'affiche pas** :
- Vérifiez que `iron_extractor.png` existe dans `textures/gui/`
- Vérifiez les dimensions (176x166 pixels)

### 6.2 Logs à Vérifier
Recherchez dans les logs :
```
[Render thread/ERROR]: Missing textures: ...
[Render thread/ERROR]: Unable to load model: ...
```

---

## Étape 7 : Fonctionnalités Avancées (Optionnel)

### 7.1 Animations
Pour ajouter des animations au modèle :
1. Créez plusieurs frames de votre modèle dans Blockbench
2. Exportez chaque frame comme un modèle séparé
3. Utilisez un système de tick dans le BlockEntity pour changer le modèle

### 7.2 Émission de Lumière
Pour que le bloc émette de la lumière quand il est actif, modifiez `IronExtractorBlock.java` :
```java
public IronExtractorBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(LIT, Boolean.FALSE));
}

@Override
public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
    return state.getValue(LIT) ? 14 : 0; // Lumière niveau 14 quand allumé
}
```

### 7.3 Particules
Pour ajouter des particules quand le bloc fonctionne, ajoutez dans `IronExtractorBlock.java` :
```java
@Override
public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    if (state.getValue(LIT)) {
        double x = (double)pos.getX() + 0.5D;
        double y = (double)pos.getY() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;

        level.addParticle(ParticleTypes.SMOKE, x, y + 0.7D, z, 0.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.FLAME, x, y + 0.7D, z, 0.0D, 0.0D, 0.0D);
    }
}
```

---

## Ressources Utiles

- **Blockbench** : https://www.blockbench.net/
- **Blockbench Wiki** : https://www.blockbench.net/wiki/
- **Minecraft Model Format** : https://minecraft.fandom.com/wiki/Model
- **Forge Documentation** : https://docs.minecraftforge.net/

---

## Résumé Rapide

1. **Créer le modèle** dans Blockbench (2 versions : off/on)
2. **Exporter** les modèles JSON dans `models/block/`
3. **Exporter** les textures PNG dans `textures/block/`
4. **Créer la texture GUI** (176x166) dans `textures/gui/`
5. **Compiler** le mod avec `gradlew build`
6. **Tester** dans Minecraft

---

## Support

Si vous rencontrez des problèmes :
1. Vérifiez les logs Minecraft (`logs/latest.log`)
2. Vérifiez que tous les fichiers sont aux bons endroits
3. Comparez avec les autres blocs du mod (ex: botanical_workbench)

Bon modding !
