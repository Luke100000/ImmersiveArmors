package immersive_armors.client.render.entity.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

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
        for (int t = 0; t < SPIKE_PIVOTS_X.length; t++) {
            ModelPart part = new ModelPart(8, 8, 0, 0);
            for (int i = 0; i < SPIKE_PIVOTS_X[t].length; i++) {
                ModelPart spike = new ModelPart(8, 8, 0, 0);
                spike.addCuboid(-1.0f, -1.0f, -1.0f, 2.0f, 5.0f, 2.0f);
                spike.pitch = (float)(SPIKE_PITCHES[t][i] / 180.0f * Math.PI);
                spike.yaw = (float)(SPIKE_YAWS[t][i] / 180.0f * Math.PI);
                spike.roll = (float)(SPIKE_ROLLS[t][i] / 180.0f * Math.PI);
                spike.pivotX = SPIKE_PIVOTS_X[t][i];
                spike.pivotY = SPIKE_PIVOTS_Y[t][i];
                spike.pivotZ = SPIKE_PIVOTS_Z[t][i];
                part.addChild(spike);
            }
            parts.add(part);
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
    public void copyFromModel(BipedEntityModel model) {
        copyFromPart(parts.get(0), model.head);
        copyFromPart(parts.get(1), model.rightArm);
        copyFromPart(parts.get(2), model.leftArm);
        copyFromPart(parts.get(3), model.rightLeg);
        copyFromPart(parts.get(4), model.leftLeg);
        super.copyFromModel(model);
    }
}
