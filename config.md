# Config

Most fields should be self-explanatory, except those topics:

## Toggle armors

`enabledArmors` is a mapping between [material](https://github.com/Luke100000/ImmersiveArmors/blob/HEAD/common/src/main/java/immersive_armors/Items.java) ("bone", "warrior", "steampunk", ...) and a boolean to disable certain armor sets. Since armor creation works at launch time, this cannot be synced with the client. That means, if a client has the armor enabled and the server not, you may cause confusion as items appear craftable but are not.

If an armor is disabled on the client and not on the server, you will get rejected.

## Modify Armor properties

`overwriteValues` allows you to change most armor properties to your liking.
Effects are not modifiable, it's already a mess with the base properties.

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

For default values, take a look at [the code](https://github.com/Luke100000/ImmersiveArmors/blob/HEAD/common/src/main/java/immersive_armors/Items.java) directly.
