package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import mod.beethoven92.betterendforge.common.block.UmbrellaTreeClusterBlock;
import mod.beethoven92.betterendforge.common.block.UmbrellaTreeMembraneBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.SplineHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFFlatWave;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSmoothUnion;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFUnion;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class UmbrellaTreeFeature extends WorldGenerator
{
	private static final Function<IBlockState, Boolean> REPLACE;
	private static final List<Vector3f> SPLINE;
	private static final List<Vector3f> ROOT;

	public UmbrellaTreeFeature(){
		super();
	}

	public UmbrellaTreeFeature(boolean notify){
		super(notify);
	}


	static 
	{
		SPLINE = Lists.newArrayList(
			new Vector3f(0.00F, 0.00F, 0.00F),
			new Vector3f(0.10F, 0.35F, 0.00F),
			new Vector3f(0.20F, 0.50F, 0.00F),
			new Vector3f(0.30F, 0.55F, 0.00F),
			new Vector3f(0.42F, 0.70F, 0.00F),
			new Vector3f(0.50F, 1.00F, 0.00F)
		);
		
		ROOT = Lists.newArrayList(
			new Vector3f(0.1F,  0.70F, 0),
			new Vector3f(0.3F,  0.30F, 0),
			new Vector3f(0.7F,  0.05F, 0),
			new Vector3f(0.8F, -0.20F, 0)
		);
		SplineHelper.offset(ROOT, new Vector3f(0, -0.45F, 0));
		
		REPLACE = (state) -> {
			if (ModTags.END_GROUND.contains(state.getBlock()) || state.getMaterial().equals(Material.PLANTS) || state.getBlock()==(ModBlocks.UMBRELLA_TREE_MEMBRANE))
			{
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) 
	{
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) return false;
		
		IBlockState wood = ModBlocks.UMBRELLA_TREE.bark.getDefaultState();
		IBlockState membrane = ModBlocks.UMBRELLA_TREE_MEMBRANE.getDefaultState().withProperty(UmbrellaTreeMembraneBlock.COLOR, 1);
		IBlockState center = ModBlocks.UMBRELLA_TREE_MEMBRANE.getDefaultState().withProperty(UmbrellaTreeMembraneBlock.COLOR, 0);
		IBlockState fruit = ModBlocks.UMBRELLA_TREE_CLUSTER.getDefaultState().withProperty(UmbrellaTreeClusterBlock.NATURAL, true);
		
		float size = ModMathHelper.randRange(10, 20, rand);
		int count = (int) (size * 0.15F);
		float var = ModMathHelper.PI2 /  (float) (count * 3);
		float start = ModMathHelper.randRange(0, ModMathHelper.PI2, rand);
		SDF sdf = null;
		List<Center> centers = Lists.newArrayList();
		
		float scale = 1;
//		if (config != null)  TODO CHECK
//		{
			scale = ModMathHelper.randRange(1F, 1.7F, rand);
//		}
		
		for (int i = 0; i < count; i++)
		{
			float angle = (float) i / (float) count * ModMathHelper.PI2 + ModMathHelper.randRange(0, var, rand) + start;
			List<Vector3f> spline = SplineHelper.copySpline(SPLINE);
			float sizeXZ = (size + ModMathHelper.randRange(0, size * 0.5F, rand)) * 0.7F;
			SplineHelper.scale(spline, sizeXZ, sizeXZ * ModMathHelper.randRange(1F, 2F, rand), sizeXZ);
			//SplineHelper.offset(spline, new Vector3f((20 - size) * 0.2F, 0, 0));
			SplineHelper.rotateSpline(spline, angle);
			SplineHelper.offsetParts(spline, rand, 0.5F, 0, 0.5F);
			
			if (SplineHelper.canGenerate(spline, pos, world, REPLACE)) 
			{
				float rScale = (scale - 1) * 0.4F + 1;
				SDF branch = SplineHelper.buildSDF(spline, 1.2F * rScale, 0.8F * rScale, (bpos) -> {
					return wood;
				});
	
				Vector3f vec = spline.get(spline.size() - 1);
				float radius = (size + ModMathHelper.randRange(0, size * 0.5F, rand)) * 0.4F;
				
				sdf = (sdf == null) ? branch : new SDFUnion().setSourceA(sdf).setSourceB(branch);
				SDF mem = makeMembrane(world, radius, rand, membrane, center);
				
				float px = ModMathHelper.floor(vec.getX()) + 0.5F;
				float py = ModMathHelper.floor(vec.getY()) + 0.5F;
				float pz = ModMathHelper.floor(vec.getZ()) + 0.5F;
				mem = new SDFTranslate().setTranslate(px, py, pz).setSource(mem);
				sdf = new SDFSmoothUnion().setRadius(2).setSourceA(sdf).setSourceB(mem);
				centers.add(new Center(pos.getX() + (double) (px * scale), pos.getY() + (double) (py * scale), pos.getZ() + (double) (pz * scale), radius * scale));
				
				vec = spline.get(0);
			}
		}
		
		if (sdf == null)
		{
			return false;
		}
		
		if (scale > 1) 
		{
			sdf = new SDFScale().setScale(scale).setSource(sdf);
		}
		
		sdf.setReplaceFunction(REPLACE).addPostProcess((info) -> {
			if (ModBlocks.UMBRELLA_TREE.isTreeLog(info.getStateUp()) && ModBlocks.UMBRELLA_TREE.isTreeLog(info.getStateDown())) 
			{
				return ModBlocks.UMBRELLA_TREE.log.getDefaultState();
			}
			else if (info.getState().equals(membrane)) 
			{
				Center min = centers.get(0);
				double d = Double.MAX_VALUE;
				BlockPos bpos = info.getPos();
				for (Center c: centers) {
					double d2 = c.distance(bpos.getX(), bpos.getZ());
					if (d2 < d) {
						d = d2;
						min = c;
					}
				}
				int color = ModMathHelper.floor(d / min.radius * 7);
				color = MathHelper.clamp(color, 1, 7);
				return info.getState().withProperty(UmbrellaTreeMembraneBlock.COLOR, color);
			}
			return info.getState();
		}).fillRecursive(world, pos);
		makeRoots(world, pos, (size * 0.5F + 3) * scale, rand, wood);
		
		for (Center c: centers) 
		{
			if (world.getBlockState(new BlockPos(c.px, c.py, c.pz)).getBlock()!= Blocks.AIR)
			{
				count = ModMathHelper.floor(ModMathHelper.randRange(5F, 10F, rand) * scale);
				float startAngle = rand.nextFloat() * ModMathHelper.PI2;
				for (int i = 0; i < count; i++) {
					float angle = (float) i / count * ModMathHelper.PI2 + startAngle;
					float dist = ModMathHelper.randRange(1.5F, 2.5F, rand) * scale;
					double px = c.px + Math.sin(angle) * dist;
					double pz = c.pz + Math.cos(angle) * dist;
					makeFruits(world, px, c.py - 1, pz, fruit, scale);
				}
			}
		}
		
		return true;
	}

	private void makeRoots(World world, BlockPos pos, float radius, Random random, IBlockState wood)
	{
		int count = (int) (radius * 1.5F);
		for (int i = 0; i < count; i++) 
		{
			float angle = (float) i / (float) count * ModMathHelper.PI2;
			float scale = radius * ModMathHelper.randRange(0.85F, 1.15F, random);
			
			List<Vector3f> branch = SplineHelper.copySpline(ROOT);
			SplineHelper.rotateSpline(branch, angle);
			SplineHelper.scale(branch, scale);
			Vector3f last = branch.get(branch.size() - 1);
			if (ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.add(last.getX(), last.getY(), last.getZ())).getBlock()))
			{
				SplineHelper.fillSplineForce(branch, world, wood, pos, REPLACE);
			}
		}
	}
	
	private SDF makeMembrane(World world, float radius, Random random, IBlockState membrane, IBlockState center)
	{
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(membrane);
		SDF sub = new SDFTranslate().setTranslate(0, -4, 0).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(sub);
		sphere = new SDFScale3D().setScale(1, 0.5F, 1).setSource(sphere);
		sphere = new SDFTranslate().setTranslate(0, 1 - radius * 0.5F, 0).setSource(sphere);
		
		float angle = random.nextFloat() * ModMathHelper.PI2;
		int count = (int) ModMathHelper.randRange(radius, radius * 2, random);
		if (count < 5) 
		{
			count = 5;
		}
		sphere = new SDFFlatWave().setAngle(angle).setRaysCount(count).setIntensity(0.6F).setSource(sphere);
		
		SDF cent = new SDFSphere().setRadius(2.5F).setBlock(center);
		sphere = new SDFUnion().setSourceA(sphere).setSourceB(cent);
		
		return sphere;
	}
	
	private void makeFruits(World world, double px, double py, double pz, IBlockState fruit, float scale) 
	{
		BlockPos.MutableBlockPos mut = new Mutable().setPos(px, py, pz);
		for (int i = 0; i < 8; i++) {
			mut.move(EnumFacing.DOWN);
			if (world.isAirBlock(mut)) 
			{
				IBlockState state = world.getBlockState(mut.up());
				if (state.getBlock()==(ModBlocks.UMBRELLA_TREE_MEMBRANE) && state.getValue(UmbrellaTreeMembraneBlock.COLOR) < 2)
				{
					BlockHelper.setWithoutUpdate(world, mut, fruit);
				}
				break;
			}
		}
	}
	
	private class Center 
	{
		final double px;
		final double py;
		final double pz;
		final float radius;
		
		Center(double x, double y, double z, float radius) 
		{
			this.px = x;
			this.py = y;
			this.pz = z;
			this.radius = radius;
		}
		
		double distance(float x, float z) 
		{
			return ModMathHelper.length(px - x, pz - z);
		}
	}
}
