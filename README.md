# Tier Modifier

This is a simple mod I made because I needed to be able to modify the armor/tool attributes for a few mods for a modpack
I was making, and this functionality was broken on KubeJS for 1.21.1 at the time of creating this. It directly exposes 
some attributes of the tiers of tools and armor of supported mods in a JSON file so that they are editable for modpack 
creators. It supports the following mods:

- The Aether
- The Twilight Forest
- The Undergarden

All 3 of these are currently hard dependencies because this was primarily developed for my modpack (so I didn't put much
effort into this mod); however, if there is enough demand I may expand this to more mods and code this more better (TM).

## 🛠️ Configuration

Everything is in one JSON file, `tiermodifier.json`, which should be generated the first time you launch the game with 
this mod installed. It will generate the default values for the mods above (at least what they were at the time of 
me coding it), however you can delete whatever you don't need to modify, and it should still work fine. I used JSON
instead of neoforge's config system because these values are injected via mixins very early on in the loading process, 
before neoforge's config system works.

## ⚖️ License

This project is licensed under the MIT license. Feel free to include in modpacks.