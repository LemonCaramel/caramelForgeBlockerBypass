package moe.caramel.forgeblockerbypass.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.util.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Inject(method = "getLaunchedVersion", at = @At("HEAD"), cancellable = true)
    public void getGameVersion(CallbackInfoReturnable<String> ci) {
        ci.setReturnValue(SharedConstants.getCurrentVersion().getName());
    }

    @Inject(method = "isProbablyModded", at = @At("HEAD"), cancellable = true)
    public void isProbablyModded(CallbackInfoReturnable<Boolean> ci) {
        ci.setReturnValue(false);
    }

}
