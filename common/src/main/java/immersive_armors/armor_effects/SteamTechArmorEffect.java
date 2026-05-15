package immersive_armors.armor_effects;

import com.mojang.serialization.Codec;
import immersive_armors.CustomDataComponentTypes;
import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.network.c2s.ArmorCommandMessage;
import immersive_armors.util.EnumCodec;
import immersive_armors.util.EnumPacketCodec;
import immersive_armors.util.FlowingText;
import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SteamTechArmorEffect extends ArmorEffect {
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        MutableComponent text = Component.translatable("armorEffect.steamTech." + getEquipmentSlot(stack).name().toLowerCase(Locale.ROOT)).withStyle(ChatFormatting.GRAY);
        tooltip.addAll(FlowingText.wrap(text, 140));
    }

    @Override
    public void equippedTick(ItemStack armor, Level world, LivingEntity entity, int slot) {
        super.equippedTick(armor, world, entity, slot);

        //machine smoke
        if (getEquipmentSlot(armor) == EquipmentSlot.FEET) {
            entity.fallDistance = Math.min(1, entity.fallDistance);

            if (entity.getRandom().nextInt(10) == 0) {
                double x = Math.cos(entity.yBodyRot / 180.f * Math.PI - Math.PI * 0.5f) * 0.25;
                double z = Math.sin(entity.yBodyRot / 180.f * Math.PI - Math.PI * 0.5f) * 0.25;
                world.addParticle(ParticleTypes.SMOKE, entity.getX() + x, entity.getY() + 1.2f, entity.getZ() + z, x * 0.2f, -0.025f, z * 0.2f);
            }

            //braking fall
            Vec3 velocity = entity.getDeltaMovement();
            if (velocity.y() < -0.75 && !entity.isFallFlying()) {
                entity.setDeltaMovement(velocity.x, -0.75f, velocity.z);
                createSteamParticle(entity);

                if (entity.tickCount % 5 == 0) {
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.REDSTONE_TORCH_BURNOUT, entity.getSoundSource(), 0.1f, 1.0f);
                }
            }
        }

        //detector
        if (!world.isClientSide() && entity.isShiftKeyDown() && getEquipmentSlot(armor) == EquipmentSlot.HEAD && entity.tickCount % 20 == 0) {
            final boolean[] sound = {false};
            world.getEntities(entity, new AABB(entity.position(), entity.position()).inflate(16)).forEach(e -> {
                if (e instanceof Monster hostileEntity) {
                    hostileEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 5));
                    if (!sound[0]) {
                        sound[0] = true;
                        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.STONE_BUTTON_CLICK_ON, entity.getSoundSource(), 0.25f, 3f);
                    }
                }
            });
        }

        //double jump
        if (world.isClientSide() && getEquipmentSlot(armor) == EquipmentSlot.LEGS) {
            if (entity.onGround()) {
                armor.set(CustomDataComponentTypes.THRUSTER_STATE.get(), ThrusterState.CHARGED);
            } else if (armor.get(CustomDataComponentTypes.THRUSTER_STATE.get()) == ThrusterState.CHARGED) {
                if (!isJumping()) {
                    armor.set(CustomDataComponentTypes.THRUSTER_STATE.get(), ThrusterState.READY);
                }
            } else if (armor.get(CustomDataComponentTypes.THRUSTER_STATE.get()) == ThrusterState.READY) {
                if (isJumping()) {
                    thrust(entity);
                    armor.set(CustomDataComponentTypes.THRUSTER_STATE.get(), ThrusterState.OFFLINE);
                    NetworkHandler.sendToServer(new ArmorCommandMessage(slot, "thrust"));
                }
            }
        }
    }

    private void createSteamParticle(LivingEntity entity) {
        entity.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                entity.getRandomX(0.5D),
                entity.getY() - 0.25,
                entity.getRandomZ(0.5D),
                (entity.getRandom().nextFloat() - 0.5) * 0.1,
                -0.75D + (entity.getRandom().nextFloat() - 0.5) * 0.1,
                (entity.getRandom().nextFloat() - 0.5) * 0.1
        );
    }

    @Override
    public void receiveCommand(ItemStack armor, Level world, LivingEntity entity, int slot, String command) {
        if (command.equals("thrust")) {
            thrust(entity);
        }
    }

    private boolean isJumping() {
        Minecraft client = Minecraft.getInstance();
        return client.player != null && client.options.keyJump.isDown();
    }

    private void thrust(LivingEntity entity) {
        //thrust
        Vec3 velocity = entity.getDeltaMovement();
        entity.setDeltaMovement(velocity.x, 0.6, velocity.z);

        for (int i = 0; i < 5; i++) {
            createSteamParticle(entity);
        }

        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.REDSTONE_TORCH_BURNOUT, entity.getSoundSource(), 0.25f, 0.75f);
    }

    private EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return ((ExtendedArmorItem) stack.getItem()).getEquipmentSlot();
    }

    public enum ThrusterState {
        OFFLINE,
        CHARGED,
        READY;

        public static final Codec<ThrusterState> CODEC = new EnumCodec<>(ThrusterState.class);
        public static final StreamCodec<ByteBuf, ThrusterState> PACKET_CODEC = new EnumPacketCodec<>(ThrusterState.class);
    }
}
