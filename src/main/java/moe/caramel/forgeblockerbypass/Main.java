package moe.caramel.forgeblockerbypass;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

@OnlyIn(Dist.CLIENT)
@Mod("caramelforgeblockerbypass")
public class Main {

    public Main() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.forgeblockerbypass.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }

}
