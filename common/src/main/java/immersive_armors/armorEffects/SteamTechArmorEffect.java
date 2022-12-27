package immersive_armors.armorEffects;

import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.network.c2s.ArmorCommandMessage;
import immersive_armors.util.FlowingText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class SteamTechArmorEffect extends ArmorEffect {
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        MutableText text = Text.translatable("armorEffect.steamTech." + getEquipmentSlot(stack).name().toLowerCase(Locale.ROOT)).formatted(Formatting.GRAY);
        tooltip.addAll(FlowingText.wrap(text, 140));
    }

    @Override
    public void equippedTick(ItemStack armor, World world, LivingEntity entity, int slot) {
        super.equippedTick(armor, world, entity, slot);

        //machine smoke
        if (getEquipmentSlot(armor) == EquipmentSlot.FEET) {
            entity.fallDistance = Math.min(1, entity.fallDistance);

            if (entity.getRandom().nextInt(10) == 0) {
                double x = Math.cos(entity.bodyYaw / 180.f * Math.PI - Math.PI * 0.5f) * 0.25;
                double z = Math.sin(entity.bodyYaw / 180.f * Math.PI - Math.PI * 0.5f) * 0.25;
                world.addParticle(ParticleTypes.SMOKE, entity.getX() + x, entity.getY() + 1.2f, entity.getZ() + z, x * 0.2f, -0.025f, z * 0.2f);
            }

            //braking fall
            Vec3d velocity = entity.getVelocity();
            if (velocity.getY() < -0.75) {
                entity.setVelocity(velocity.x, -0.75f, velocity.z);
                createSteamParticle(entity);

                if (entity.age % 5 == 0) {
                    entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, entity.getSoundCategory(), 0.1f, 1.0f);
                }
            }
        }

        //detector
        if (!world.isClient() && entity.isSneaking() && getEquipmentSlot(armor) == EquipmentSlot.HEAD && entity.age % 20 == 0) {
            final boolean[] sound = {false};
            world.getOtherEntities(entity, new Box(entity.getPos(), entity.getPos()).expand(16)).forEach(e -> {
                if (e instanceof HostileEntity) {
                    LivingEntity le = (LivingEntity)e;
                    le.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 5));
                    if (!sound[0]) {
                        sound[0] = true;
                        entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, entity.getSoundCategory(), 0.25f, 3f);
                    }
                }
            });
        }

        //double jump
        if (world.isClient && getEquipmentSlot(armor) == EquipmentSlot.LEGS) {
            NbtCompound nbt = armor.getOrCreateNbt();
            if (entity.isOnGround()) {
                nbt.putInt("thrusterState", ThrusterState.CHARGED.ordinal());
            } else if (nbt.getInt("thrusterState") == ThrusterState.CHARGED.ordinal()) {
                if (!isJumping()) {
                    nbt.putInt("thrusterState", ThrusterState.READY.ordinal());
                }
            } else if (nbt.getInt("thrusterState") == ThrusterState.READY.ordinal()) {
                if (isJumping()) {
                    thrust(entity);
                    nbt.putInt("thrusterState", ThrusterState.OFFLINE.ordinal());

                    NetworkHandler.sendToServer(new ArmorCommandMessage(slot, "thrust"));
                }
            }
        }
    }

    private void createSteamParticle(LivingEntity entity) {
        entity.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                entity.getParticleX(0.5D),
                entity.getY() - 0.25,
                entity.getParticleZ(0.5D),
                (entity.getRandom().nextFloat() - 0.5) * 0.1,
                -0.75D + (entity.getRandom().nextFloat() - 0.5) * 0.1,
                (entity.getRandom().nextFloat() - 0.5) * 0.1
        );
    }

    @Override
    public void receiveCommand(ItemStack armor, World world, LivingEntity entity, int slot, String command) {
        if (command.equals("thrust")) {
            thrust(entity);
        }
    }

    private boolean isJumping() {
        MinecraftClient client = MinecraftClient.getInstance();
        return client != null && client.player != null && client.player.input.jumping;
    }

    private void thrust(LivingEntity entity) {
        //thrust
        Vec3d velocity = entity.getVelocity();
        entity.setVelocity(velocity.x, 0.6, velocity.z);

        for (int i = 0; i < 5; i++) {
            createSteamParticle(entity);
        }

        entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, entity.getSoundCategory(), 0.25f, 0.75f);
    }

    private EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return ((ArmorItem)stack.getItem()).getSlotType();
    }

    private enum ThrusterState {
        OFFLINE,
        CHARGED,
        READY
    }
}
