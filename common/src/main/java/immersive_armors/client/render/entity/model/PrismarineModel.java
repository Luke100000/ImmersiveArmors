package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PrismarineModel extends DecoModel {
    private final List<ModelPart> parts = new LinkedList<>();

    private static final float[][] SPIKE_PITCHES = new float[][] {
            {45, 45, 45, 45},
            {45, 45, 135},
            {45, 45, 135},
            {135},
            {135},
    };
    private static final float[][] SPIKE_YAWS = new float[][] {
            {225, 135, 45, 315},
            {135, 45, 90},
            {225, 315, 270},
            {90},
            {270},
    };
    private static final float[][] SPIKE_ROLLS = new float[][] {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
    };
    private static final float[][] SPIKE_PIVOTS_X = new float[][] {
            {5.0f, -5.0f, -5.0f, 5.0f},
            {-5.5f, -5.5f, -6.0f},
            {5.5f, 5.5f, 6.0f},
            {-5.0f},
            {5.0f},
    };
    private static final float[][] SPIKE_PIVOTS_Y = new float[][] {
            {-10.0f, -10.0f, -10.0f, -10.0f},
            {-5.0f, -5.0f, 6.0f},
            {-5.0f, -5.0f, 6.0f},
            {6.0f},
            {6.0f},
    };
    private static final float[][] SPIKE_PIVOTS_Z = new float[][] {
            {5.0f, 5.0f, -5.0f, -5.0f},
            {4.5f, -4.5f, 0.0f},
            {4.5f, -4.5f, 0.0f},
            {0.0f},
            {0.0f},
    };

    public PrismarineModel() {
        super();

        ModelData modelData = new ModelData();

        for (int t = 0; t < SPIKE_PIVOTS_X.length; t++) {
            ModelPartData data = modelData.getRoot().addChild("part_" + t, ModelPartBuilder.create(), ModelTransform.NONE);

            for (int i = 0; i < SPIKE_PIVOTS_X[t].length; i++) {
                data.addChild("spike_" + i,
                        ModelPartBuilder.create()
                                .cuboid(-1.0f, -1.0f, -1.0f, 2.0f, 5.0f, 2.0f),
                        ModelTransform.of(
                                SPIKE_PIVOTS_X[t][i],
                                SPIKE_PIVOTS_Y[t][i],
                                SPIKE_PIVOTS_Z[t][i],
                                (float)(SPIKE_PITCHES[t][i] / 180.0f * Math.PI),
                                (float)(SPIKE_YAWS[t][i] / 180.0f * Math.PI),
                                (float)(SPIKE_ROLLS[t][i] / 180.0f * Math.PI)
                        ));
            }

            parts.add(data.createPart(8, 8));
        }
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.singletonList(parts.get(0));
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return parts.subList(1, parts.size());
    }

    @Override
    public void copyFromModel(BipedEntityModel model, EquipmentSlot slot) {
        parts.forEach(p -> p.visible = false);
        switch (slot) {
            case HEAD:
                parts.get(0).copyTransform(model.head);
                parts.get(0).visible = true;
                break;
            case CHEST:
                parts.get(1).copyTransform(model.rightArm);
                parts.get(2).copyTransform(model.leftArm);
                parts.get(1).visible = true;
                parts.get(2).visible = true;
                break;
            case LEGS:
                parts.get(3).copyTransform(model.rightLeg);
                parts.get(4).copyTransform(model.leftLeg);
                parts.get(3).visible = true;
                parts.get(4).visible = true;
                break;
        }
        super.copyFromModel(model, slot);
    }
}
