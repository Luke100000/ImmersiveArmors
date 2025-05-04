# Config

Most fields should be self-explanatory, except those topics:

## Modify Armor properties

`overwriteValues` allows you to change most armor properties to your liking.
The Effects are not modifiable, it's already a mess with the base properties.

Unlike the armor toggle, this config is synced from the server when playing multiplayer!

The map should contain keys like `material:proerties`, e.g.:

````json
{
  "overwriteValues": {
    "bone:toughness": 2.0
  }
}
````

Valid properties are:

* helmetProtection
* chestplateProtection
* leggingsProtection
* bootsProtection
* weight
* toughness
* enchantability

If an entry is invalid, it will be printed in the log.

For default values, take a look
at[the code](https://github.com/Luke100000/ImmersiveArmors/blob/HEAD/common/src/main/java/immersive_armors/Items.java)
directly.