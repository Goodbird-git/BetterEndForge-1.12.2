package mod.beethoven92.betterendforge.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class CubozoaModel extends ModelBase {
	private final ModelRenderer model;
	private final ModelRenderer main_cube_r1;
	private final ModelRenderer tentacle_center_1;
	private final ModelRenderer tentacle_1;
	private final ModelRenderer tentacle_center_2;
	private final ModelRenderer tentacle_2;
	private final ModelRenderer tentacle_center_3;
	private final ModelRenderer tentacle_3;
	private final ModelRenderer tentacle_center_4;
	private final ModelRenderer tentacle_4;
	private float scaleY;
	private float scaleXZ;

	public CubozoaModel() {
		textureWidth = 48;
		textureHeight = 48;

		model = new ModelRenderer(this);
		model.setRotationPoint(0.0F, 24.0F, 0.0F);
		model.setTextureOffset(0, 17).addBox(-2.0F, -12.5F, -2.0F, 4, 4, 4, 0.0F);

		main_cube_r1 = new ModelRenderer(this);
		main_cube_r1.setRotationPoint(0.0F, -14.0F, 0.0F);
		model.addChild(main_cube_r1);
		setRotationAngle(main_cube_r1, 0.0F, 0.0F, -3.1416F);
		main_cube_r1.setTextureOffset(0, 0).addBox(-5.0F, -7.0F, -5.0F, 10, 7, 10, 0.0F);

		tentacle_center_1 = new ModelRenderer(this);
		tentacle_center_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(tentacle_center_1);

		tentacle_1 = new ModelRenderer(this);
		tentacle_1.setRotationPoint(0.0F, -7.0F, 4.5F);
		tentacle_center_1.addChild(tentacle_1);
		tentacle_1.setTextureOffset(16, 17).addBox(-4.0F, 0.0F, 0.0F, 8, 7, 0, 0.0F);

		tentacle_center_2 = new ModelRenderer(this);
		tentacle_center_2.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(tentacle_center_2);
		setRotationAngle(tentacle_center_2, 0.0F, -1.5708F, 0.0F);

		tentacle_2 = new ModelRenderer(this);
		tentacle_2.setRotationPoint(0.0F, -7.0F, 4.5F);
		tentacle_center_2.addChild(tentacle_2);
		tentacle_2.setTextureOffset(16, 17).addBox(-4.0F, 0.0F, 0.0F, 8, 7, 0, 0.0F);

		tentacle_center_3 = new ModelRenderer(this);
		tentacle_center_3.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(tentacle_center_3);
		setRotationAngle(tentacle_center_3, 0.0F, 3.1416F, 0.0F);

		tentacle_3 = new ModelRenderer(this);
		tentacle_3.setRotationPoint(0.0F, -7.0F, 4.5F);
		tentacle_center_3.addChild(tentacle_3);
		tentacle_3.setTextureOffset(16, 17).addBox(-4.0F, 0.0F, 0.0F, 8, 7, 0, 0.0F);

		tentacle_center_4 = new ModelRenderer(this);
		tentacle_center_4.setRotationPoint(0.0F, 0.0F, 0.0F);
		model.addChild(tentacle_center_4);
		setRotationAngle(tentacle_center_4, 0.0F, 1.5708F, 0.0F);

		tentacle_4 = new ModelRenderer(this);
		tentacle_4.setRotationPoint(0.0F, -7.0F, 4.5F);
		tentacle_center_4.addChild(tentacle_4);
		tentacle_4.setTextureOffset(16, 17).addBox(-4.0F, 0.0F, 0.0F, 8, 7, 0, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		float sin = MathHelper.sin(ageInTicks * 0.13F);
		scaleY = sin * 0.1F + 0.9F;
		scaleXZ = MathHelper.sin(ageInTicks * 0.13F + 3.14F) * 0.1F + 0.9F;

		tentacle_1.rotateAngleX = sin * 0.15F;
		tentacle_2.rotateAngleX = sin * 0.15F;
		tentacle_3.rotateAngleX = sin * 0.15F;
		tentacle_4.rotateAngleX = sin * 0.15F;
		GL11.glPushMatrix();
		GL11.glScaled(scaleXZ, scaleY, scaleXZ);
		model.render(scale);
		GL11.glPopMatrix();
	}

	private void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
