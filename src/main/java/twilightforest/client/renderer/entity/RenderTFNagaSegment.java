package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.entity.boss.EntityTFNagaSegment;

public class RenderTFNagaSegment<T extends EntityTFNagaSegment> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("nagasegment.png");
	private final ModelBase model;

	public RenderTFNagaSegment(EntityRendererManager manager, T model) {
		super(manager);
		this.model = model;
	}

	@Override
	public void doRender(EntityTFNagaSegment entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) x, (float) y, (float) z);
		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);

		// I forget what this glitch is called, but let's fix it
		float yawDiff = entity.rotationYaw - entity.prevRotationYaw;
		if (yawDiff > 180) {
			yawDiff -= 360;
		} else if (yawDiff < -180) {
			yawDiff += 360;
		}
		float yaw = entity.prevRotationYaw + yawDiff * partialTicks;

		GlStateManager.rotatef(yaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
		this.bindTexture(textureLoc);
		this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}
