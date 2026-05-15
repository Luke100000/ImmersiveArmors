package immersive_armors.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
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

        MeshDefinition modelData = new MeshDefinition();

        for (int t = 0; t < SPIKE_PIVOTS_X.length; t++) {
            PartDefinition data = modelData.getRoot().addOrReplaceChild("part_" + t, CubeListBuilder.create(), PartPose.ZERO);

            for (int i = 0; i < SPIKE_PIVOTS_X[t].length; i++) {
                data.addOrReplaceChild("spike_" + i,
                        CubeListBuilder.create()
                                .addBox(-1.0f, -1.0f, -1.0f, 2.0f, 5.0f, 2.0f),
                        PartPose.offsetAndRotation(
                                SPIKE_PIVOTS_X[t][i],
                                SPIKE_PIVOTS_Y[t][i],
                                SPIKE_PIVOTS_Z[t][i],
                                (float)(SPIKE_PITCHES[t][i] / 180.0f * Math.PI),
                                (float)(SPIKE_YAWS[t][i] / 180.0f * Math.PI),
                                (float)(SPIKE_ROLLS[t][i] / 180.0f * Math.PI)
                        ));
            }

            parts.add(data.bake(8, 8));
        }
    }

    @Override
    public Iterable<ModelPart> parts() {
        return parts;
    }

    @Override
    public void copyFromModel(HumanoidModel model, EquipmentSlot slot) {
        parts.forEach(p -> p.visible = false);
        switch (slot) {
            case HEAD -> {
                copyPart(parts.getFirst(), model.head);
                parts.getFirst().visible = true;
            }
            case CHEST -> {
                copyPart(parts.get(1), model.rightArm);
                copyPart(parts.get(2), model.leftArm);
                parts.get(1).visible = true;
                parts.get(2).visible = true;
            }
            case LEGS -> {
                copyPart(parts.get(3), model.rightLeg);
                copyPart(parts.get(4), model.leftLeg);
                parts.get(3).visible = true;
                parts.get(4).visible = true;
            }
        }
        super.copyFromModel(model, slot);
    }
}
