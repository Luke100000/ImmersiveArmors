package immersive_armors.item;

import immersive_armors.client.render.entity.piece.Piece;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.EquipmentSlot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ArmorPiece {
    public static Supplier<EntityModel> LEGGINGS_LOWER = () -> new BipedEntityModel<>(0.1f);
    public static Supplier<EntityModel> BODY_LOWER = () -> new BipedEntityModel<>(0.6f);

    public static Supplier<EntityModel> LEGGINGS_MIDDLE = () -> new BipedEntityModel<>(0.5f);
    public static Supplier<EntityModel> BODY_MIDDLE = () -> new BipedEntityModel<>(1.0f);

    public static Supplier<EntityModel> LEGGINGS_UPPER = () -> new BipedEntityModel<>(1.0f);
    public static Supplier<EntityModel> BODY_UPPER = () -> new BipedEntityModel<>(1.5f);

    private static final Map<Supplier<EntityModel>, String> DEFAULT_TEXTURES = new HashMap<>();

    static {
        DEFAULT_TEXTURES.put(LEGGINGS_LOWER, "leggings_lower");
        DEFAULT_TEXTURES.put(BODY_LOWER, "body_lower");
        DEFAULT_TEXTURES.put(LEGGINGS_MIDDLE, "leggings_middle");
        DEFAULT_TEXTURES.put(BODY_MIDDLE, "body_middle");
        DEFAULT_TEXTURES.put(LEGGINGS_UPPER, "leggings_upper");
        DEFAULT_TEXTURES.put(BODY_UPPER, "body_upper");
    }

    static {
        DEFAULT_TEXTURES.put(LEGGINGS_LOWER, "layer_2_lower");
        DEFAULT_TEXTURES.put(BODY_LOWER, "layer_1_lower");
        DEFAULT_TEXTURES.put(LEGGINGS_MIDDLE, "layer_2_middle");
        DEFAULT_TEXTURES.put(BODY_MIDDLE, "layer_1_middle");
        DEFAULT_TEXTURES.put(LEGGINGS_UPPER, "layer_2_upper");
        DEFAULT_TEXTURES.put(BODY_UPPER, "layer_1_upper");
    }

    private boolean translucent;
    private boolean glint;
    private boolean colored;
    private boolean glowing;

    private String texture;
    private Supplier<EntityModel> model;
    private Supplier<Piece> renderer;

    private final Set<EquipmentSlot> equipmentSlots = new HashSet<>();

    public ArmorPiece() {
    }

    public ArmorPiece(Supplier<EntityModel> model) {
        this.model = model;
        texture = DEFAULT_TEXTURES.getOrDefault(model, "feature");
    }

    public ArmorPiece translucent() {
        this.translucent = true;
        return this;
    }

    public ArmorPiece glint() {
        this.glint = true;
        return this;
    }

    public ArmorPiece colored() {
        this.colored = true;
        return this;
    }

    public ArmorPiece glowing() {
        this.glowing = true;
        return this;
    }

    public ArmorPiece texture(String texture) {
        this.texture = texture;
        return this;
    }

    public ArmorPiece head() {
        this.equipmentSlots.add(EquipmentSlot.HEAD);
        return this;
    }

    public ArmorPiece chest() {
        this.equipmentSlots.add(EquipmentSlot.CHEST);
        return this;
    }

    public ArmorPiece legs() {
        this.equipmentSlots.add(EquipmentSlot.LEGS);
        return this;
    }

    public ArmorPiece feet() {
        this.equipmentSlots.add(EquipmentSlot.FEET);
        return this;
    }

    public ArmorPiece upper() {
        this.equipmentSlots.add(EquipmentSlot.HEAD);
        this.equipmentSlots.add(EquipmentSlot.CHEST);
        this.equipmentSlots.add(EquipmentSlot.FEET);
        return this;
    }

    public ArmorPiece lower() {
        this.equipmentSlots.add(EquipmentSlot.LEGS);
        return this;
    }

    public ArmorPiece full() {
        upper();
        lower();
        return this;
    }

    public ArmorPiece renderer(Supplier<Piece> renderer) {
        this.renderer = renderer;
        return this;
    }

    public boolean isTranslucent() {
        return translucent;
    }

    public boolean isGlint() {
        return glint;
    }

    public boolean isColored() {
        return colored;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public String getTexture() {
        return texture;
    }

    public Set<EquipmentSlot> getEquipmentSlots() {
        return equipmentSlots;
    }

    public Supplier<EntityModel> getModel() {
        return model;
    }

    public Supplier<Piece> getRenderer() {
        return renderer;
    }
}
