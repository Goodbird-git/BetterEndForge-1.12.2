package mod.beethoven92.betterendforge.common.world.moderngen.decorator;

import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.BitSet;
import java.util.Random;

public class OreFeature extends WorldGenerator {

    OreFeatureConfig config;

    public OreFeature(OreFeatureConfig config){
        this.config = config;
    }

    @Override
    public boolean generate(World reader, Random rand, BlockPos pos) {
        float f = rand.nextFloat() * (float)Math.PI;
        float f1 = (float)config.size / 8.0F;
        int i = MathHelper.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d0 = (double)pos.getX() + Math.sin((double)f) * (double)f1;
        double d1 = (double)pos.getX() - Math.sin((double)f) * (double)f1;
        double d2 = (double)pos.getZ() + Math.cos((double)f) * (double)f1;
        double d3 = (double)pos.getZ() - Math.cos((double)f) * (double)f1;
        int j = 2;
        double d4 = (double)(pos.getY() + rand.nextInt(3) - 2);
        double d5 = (double)(pos.getY() + rand.nextInt(3) - 2);
        int k = pos.getX() - MathHelper.ceil(f1) - i;
        int l = pos.getY() - 2 - i;
        int i1 = pos.getZ() - MathHelper.ceil(f1) - i;
        int j1 = 2 * (MathHelper.ceil(f1) + i);
        int k1 = 2 * (2 + i);

        for(int l1 = k; l1 <= k + j1; ++l1) {
            for(int i2 = i1; i2 <= i1 + j1; ++i2) {
                if (l <= reader.getHeight(l1, i2)) {
                    return this.func_207803_a(reader, rand, config, d0, d1, d2, d3, d4, d5, k, l, i1, j1, k1);
                }
            }
        }

        return false;
    }

    protected boolean func_207803_a(World worldIn, Random random, OreFeatureConfig config, double p_207803_4_, double p_207803_6_, double p_207803_8_, double p_207803_10_, double p_207803_12_, double p_207803_14_, int p_207803_16_, int p_207803_17_, int p_207803_18_, int p_207803_19_, int p_207803_20_) {
        int i = 0;
        BitSet bitset = new BitSet(p_207803_19_ * p_207803_20_ * p_207803_19_);
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        int j = config.size;
        double[] adouble = new double[j * 4];

        for(int k = 0; k < j; ++k) {
            float f = (float)k / (float)j;
            double d0 = AdvMathHelper.lerp((double)f, p_207803_4_, p_207803_6_);
            double d2 = AdvMathHelper.lerp((double)f, p_207803_12_, p_207803_14_);
            double d4 = AdvMathHelper.lerp((double)f, p_207803_8_, p_207803_10_);
            double d6 = random.nextDouble() * (double)j / (double)16.0F;
            double d7 = ((double)(MathHelper.sin((float)Math.PI * f) + 1.0F) * d6 + (double)1.0F) / (double)2.0F;
            adouble[k * 4 + 0] = d0;
            adouble[k * 4 + 1] = d2;
            adouble[k * 4 + 2] = d4;
            adouble[k * 4 + 3] = d7;
        }

        for(int i3 = 0; i3 < j - 1; ++i3) {
            if (!(adouble[i3 * 4 + 3] <= (double)0.0F)) {
                for(int k3 = i3 + 1; k3 < j; ++k3) {
                    if (!(adouble[k3 * 4 + 3] <= (double)0.0F)) {
                        double d12 = adouble[i3 * 4 + 0] - adouble[k3 * 4 + 0];
                        double d13 = adouble[i3 * 4 + 1] - adouble[k3 * 4 + 1];
                        double d14 = adouble[i3 * 4 + 2] - adouble[k3 * 4 + 2];
                        double d15 = adouble[i3 * 4 + 3] - adouble[k3 * 4 + 3];
                        if (d15 * d15 > d12 * d12 + d13 * d13 + d14 * d14) {
                            if (d15 > (double)0.0F) {
                                adouble[k3 * 4 + 3] = (double)-1.0F;
                            } else {
                                adouble[i3 * 4 + 3] = (double)-1.0F;
                            }
                        }
                    }
                }
            }
        }

        for(int j3 = 0; j3 < j; ++j3) {
            double d11 = adouble[j3 * 4 + 3];
            if (!(d11 < (double)0.0F)) {
                double d1 = adouble[j3 * 4 + 0];
                double d3 = adouble[j3 * 4 + 1];
                double d5 = adouble[j3 * 4 + 2];
                int l = Math.max(MathHelper.floor(d1 - d11), p_207803_16_);
                int l3 = Math.max(MathHelper.floor(d3 - d11), p_207803_17_);
                int i1 = Math.max(MathHelper.floor(d5 - d11), p_207803_18_);
                int j1 = Math.max(MathHelper.floor(d1 + d11), l);
                int k1 = Math.max(MathHelper.floor(d3 + d11), l3);
                int l1 = Math.max(MathHelper.floor(d5 + d11), i1);

                for(int i2 = l; i2 <= j1; ++i2) {
                    double d8 = ((double)i2 + (double)0.5F - d1) / d11;
                    if (d8 * d8 < (double)1.0F) {
                        for(int j2 = l3; j2 <= k1; ++j2) {
                            double d9 = ((double)j2 + (double)0.5F - d3) / d11;
                            if (d8 * d8 + d9 * d9 < (double)1.0F) {
                                for(int k2 = i1; k2 <= l1; ++k2) {
                                    double d10 = ((double)k2 + (double)0.5F - d5) / d11;
                                    if (d8 * d8 + d9 * d9 + d10 * d10 < (double)1.0F) {
                                        int l2 = i2 - p_207803_16_ + (j2 - p_207803_17_) * p_207803_19_ + (k2 - p_207803_18_) * p_207803_19_ * p_207803_20_;
                                        if (!bitset.get(l2)) {
                                            bitset.set(l2);
                                            blockpos$mutable.setPos(i2, j2, k2);
                                            if (config.target.test(worldIn.getBlockState(blockpos$mutable), random)) {
                                                worldIn.setBlockState(blockpos$mutable, config.state, 2);
                                                ++i;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return i > 0;
    }


}