package immersive_armors;

import immersive_armors.client.render.entity.model.*;
import immersive_armors.client.render.entity.piece.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import static immersive_armors.Items.*;

public class ItemsClient {
    public static void setupPieces() {
        BONE_ARMOR
                .lower(new MiddleLeggingsLayerPiece())
                .upper(new MiddleBodyLayerPiece());

        WITHER_ARMOR
                .lower(new MiddleLeggingsLayerPiece())
                .upper(new MiddleBodyLayerPiece())
                .chest(new CapePiece<>(new CapeModel<>()));

        WARRIOR_ARMOR
                .lower(new LowerLeggingsLayerPiece())
                .upper(new LowerBodyLayerPiece())
                .lower(new MiddleLeggingsLayerPiece())
                .upper(new MiddleBodyLayerPiece())
                .lower(new UpperLeggingsLayerPiece())
                .upper(new UpperBodyLayerPiece())
                .head(new ModelPiece(new HorizontalHeadModel()).texture("horizontal"))
                .chest(new CapePiece<>(new CapeModel<>()));

        HEAVY_ARMOR
                .lower(new LowerLeggingsLayerPiece())
                .upper(new LowerBodyLayerPiece())
                .upper(new MiddleBodyLayerPiece())
                .lower(new UpperLeggingsLayerPiece())
                .upper(new UpperBodyLayerPiece())
                .head(new ModelPiece(new VerticalHeadModel()).texture("vertical"));

        ROBE_ARMOR
                .lower(new LowerLeggingsLayerPiece().colored())
                .upper(new LowerBodyLayerPiece().colored())
                .lower(new MiddleLeggingsLayerPiece().colored())
                .upper(new MiddleBodyLayerPiece().colored());

        SLIME_ARMOR
                .lower(new LowerLeggingsLayerPiece().texture("leggings").translucent())
                .upper(new LowerBodyLayerPiece().texture("body").translucent())
                .lower(new MiddleLeggingsLayerPiece().texture("leggings").translucent())
                .upper(new MiddleBodyLayerPiece().texture("body").translucent())
                .lower(new UpperLeggingsLayerPiece().texture("leggings").translucent())
                .upper(new UpperBodyLayerPiece().texture("body").translucent());

        DIVINE_ARMOR
                .lower(new LowerLeggingsLayerPiece().colored())
                .upper(new LowerBodyLayerPiece().colored())
                .upper(new MiddleBodyLayerPiece().glint())
                .upper(new UpperBodyLayerPiece().colored())
                .chest(new CapePiece<>(new CapeModel<>()).colored());

        PRISMARINE_ARMOR
                .lower(new MiddleLeggingsLayerPiece())
                .upper(new MiddleBodyLayerPiece())
                .upper(new UpperBodyLayerPiece())
                .full(new ModelPiece(new PrismarineModel()).texture("prismarine"));

        WOODEN_ARMOR
                .lower(new MiddleLeggingsLayerPiece())
                .upper(new MiddleBodyLayerPiece())
                .lower(new UpperLeggingsLayerPiece())
                .upper(new UpperBodyLayerPiece())
                .chest(new ModelPiece(new ShoulderModel()).texture("shoulder"));

        STEAMPUNK_ARMOR
                .lower(new LowerLeggingsLayerPiece())
                .upper(new LowerBodyLayerPiece())
                .lower(new MiddleLeggingsLayerPiece())
                .upper(new MiddleBodyLayerPiece().translucent())
                .upper(new UpperBodyLayerPiece())
                .chest(new ItemPiece("leftArm", 0.05f, -0.2f, -0.1f, 1.0f, new ItemStack(net.minecraft.item.Items.COMPASS), new Quaternion(new Vec3f(1.0f, 0.0f, 0.0f), -90.0f, true)))
                .chest(new ItemPiece("body", 0.15f, 0.4f, -0.175f, 0.6f, new ItemStack(net.minecraft.item.Items.CLOCK)))
                .chest(new GearPiece<>(new GearModel("body", 8), "gear", 0.05f, 0.3f, 0.18f, 0.2f))
                .chest(new GearPiece<>(new GearModel("body", 5), "gear_small", -0.15f, 0.6f, 0.19f, -0.3f))
                .chest(new GearPiece<>(new GearModel("body", 5), "gear_small", -0.1f, 0.45f, -0.17f, -0.3f))
                .head(new GearPiece<>(new GearModel("head", 5), "gear_small", -0.3f, -0.3f, 0.0f, -0.2f, new Quaternion(new Vec3f(0.0f, 1.0f, 0.0f), 90.0f, true)))
                .chest(new GearPiece<>(new GearModel("leftArm", 5), "gear_small", 0.23f, 0.40f, 0.0f, -0.3f, new Quaternion(new Vec3f(0.0f, 1.0f, 0.0f), 90.0f, true)))
                .chest(new ModelPiece(new RightVerticalShoulderModel()).texture("shoulder"))
                .chest(new CapePiece<>(new CapeModel<>()));
    }
}
