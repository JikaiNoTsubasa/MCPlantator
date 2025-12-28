# Textures à créer

Ce fichier liste toutes les textures qui doivent être créées pour que le mod fonctionne correctement.

Référez-vous au fichier `PLAN_CONCEPTION.md` (section GRAPHISMES) pour des instructions détaillées sur comment créer chaque texture.

## Items (16x16 pixels)

### item/
- [ ] `gunpowder_seed.png` - Graine poudrière (graine grise avec aspect poudré)

## Blocs - Phases de culture (16x16 pixels, format cross)

### block/
- [ ] `gunpowder_crop_stage0.png` - Pousse vient d'être plantée (2-3 pixels)
- [ ] `gunpowder_crop_stage1.png` - Petite pousse (4-5 pixels)
- [ ] `gunpowder_crop_stage2.png` - Pousse qui grandit (6-7 pixels)
- [ ] `gunpowder_crop_stage3.png` - Tiges visibles (8-9 pixels)
- [ ] `gunpowder_crop_stage4.png` - Plante plus haute avec capsules (10-11 pixels)
- [ ] `gunpowder_crop_stage5.png` - Capsules de poudre visibles (12-13 pixels)
- [ ] `gunpowder_crop_stage6.png` - Capsules plus grosses (14 pixels)
- [ ] `gunpowder_crop_stage7.png` - MATURE - Plante complète (15-16 pixels)

## Blocs - Établi botanique (16x16 pixels)

### block/
- [ ] `botanical_workbench_top.png` - Vue du dessus (mélange bois + végétal)
- [ ] `botanical_workbench_side.png` - Côtés (texture bois)
- [ ] `botanical_workbench_front.png` - Face avant (plus détaillée)

## GUI (176x166 pixels)

### gui/
- [ ] `botanical_workbench_gui.png` - Interface de l'établi
  - Doit contenir :
    - Fond gris standard Minecraft
    - 2 slots d'entrée (haut et bas)
    - 1 slot de résultat (à droite)
    - Slots inventaire joueur
    - Une flèche entre entrée et sortie (optionnel)

---

## Outils recommandés

1. **BlockBench** (https://www.blockbench.net/) - **RECOMMANDÉ**
   - Gratuit, spécialisé pour Minecraft
   - Facilite énormément la création de textures

2. **GIMP** (https://www.gimp.org/)
   - Gratuit, puissant
   - Supporte la transparence

3. **Piskel** (https://www.piskelapp.com/)
   - En ligne, gratuit
   - Spécialisé pixel art

---

## Palette de couleurs suggérée

### Pour la graine poudrière :
- Gris très foncé : `#2C2C2C`
- Gris moyen : `#6B6B6B`
- Gris clair : `#9C9C9C`
- Blanc cassé : `#E0E0E0`

### Pour la plante :
- Vert foncé : `#2D5016`
- Vert moyen : `#4A7B25`
- Vert clair : `#6FA838`
- Gris capsules : `#3C3C3C`
- Gris poudre : `#8C8C8C`

### Pour l'établi :
- Bois clair : `#9C7A4F`
- Bois foncé : `#6B4423`
- Vert décor : `#4A7B25`

---

## Instructions rapides

1. Créer un fichier PNG de la bonne dimension (16x16 ou 176x166)
2. Fond transparent (sauf pour le GUI)
3. Style pixel art pur (pas d'antialiasing)
4. 4-8 couleurs maximum par texture
5. Contours sombres pour définir les formes
6. Sauvegarder exactement avec le nom indiqué ci-dessus

**Consultez le PLAN_CONCEPTION.md pour des guides détaillés étape-par-étape avec des exemples ASCII !**
