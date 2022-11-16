import glob
import os.path

import PIL.Image

if __name__ == "__main__":
    for armor in glob.glob("common/src/main/resources/assets/immersive_armors/textures/models/armor/*"):
        for layer in [1, 2]:
            for overlay in ["", "_overlay"]:
                result = PIL.Image.new("RGBA", (64, 32), (0, 0, 0, 0))
                found = False
                for texture in [
                    "",
                    "_lower",
                    "_middle",
                    "_upper",
                ]:
                    layer_name = "body" if layer == 1 else "leggings"
                    path = os.path.join(armor, f"{layer_name}{texture}{overlay}.png")
                    print(path)
                    if os.path.exists(path):
                        img = PIL.Image.open(path)
                        result = PIL.Image.alpha_composite(result, img)
                        found = True

                if found:
                    armor_name = os.path.basename(os.path.normpath(armor))
                    path = os.path.join(os.path.dirname(armor), f"{armor_name}_layer_{layer}{overlay}.png")
                    path = path.replace("immersive_armors", "minecraft")
                    os.makedirs(os.path.dirname(path), exist_ok=True)
                    with open(path, "wb") as file:
                        result.save(file)
