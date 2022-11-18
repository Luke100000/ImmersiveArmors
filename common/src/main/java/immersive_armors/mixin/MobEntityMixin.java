package immersive_armors.mixin;

import immersive_armors.Config;
import immersive_armors.Items;
import immersive_armors.item.ExtendedArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Inject(method = "getEquipmentForSlot(Lnet/minecraft/entity/EquipmentSlot;I)Lnet/minecraft/item/Item;", at = @At("HEAD"), cancellable = true)
    private static void getEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel, CallbackInfoReturnable<Item> cir) {
        Random random = new Random();

        final Map<Integer, ExtendedArmorMaterial> items = new HashMap<Integer, ExtendedArmorMaterial>() {{
            put(0, Items.WOODEN_ARMOR);
            put(1, Items.WARRIOR_ARMOR);
            put(2, Items.HEAVY_ARMOR);
            put(3, Items.DIVINE_ARMOR);
            put(4, Items.PRISMARINE_ARMOR);
        }};

        if (items.containsKey(equipmentLevel) && random.nextFloat() < Config.getInstance().mobEntityUseImmersiveArmorChance) {
            String name = items.get(equipmentLevel).getName();

            Supplier<Item> item = null;
            switch (equipmentSlot) {
                case HEAD:
                    item = Items.items.get(name + "_helmet");
                    break;
                case CHEST:
                    item = Items.items.get(name + "_chestplate");
                    break;
                case LEGS:
                    item = Items.items.get(name + "_leggings");
                    break;
                case FEET:
                    item = Items.items.get(name + "_boots");
                    break;
            }

            if (item != null) {
                cir.setReturnValue(item.get());
            }
        }
    }
}
