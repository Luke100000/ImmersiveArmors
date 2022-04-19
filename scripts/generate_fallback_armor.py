import glob
import os.path

import PIL.Image

if __name__ == "__main__":
    for armor in glob.glob("common/src/main/resources/assets/immersive_armors/textures/models/armor/*"):
        result = PIL.Image.new("RGBA", (64, 32), (0, 0, 0, 0))

        for layer in [1, 2]:
            for texture in [
                "lower",
                "middle",
                "upper",
                "lower_overlay",
                "middle_overlay",
                "upper_overlay",
            ]:
                path = os.path.join(armor, f"layer_{layer}_{texture}.png")
                if os.path.exists(path):
                    img = PIL.Image.open(path)
                    result = PIL.Image.alpha_composite(result, img)

            armor_name = os.path.basename(os.path.normpath(armor))
            path = os.path.join(os.path.dirname(armor), f"{armor_name}_layer_{layer}.png")
            path = path.replace("immersive_armors", "minecraft")
            os.makedirs(os.path.dirname(path), exist_ok=True)
            with open(path, "wb") as file:
                result.save(file)
