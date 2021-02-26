package moe.caramel.caramelforgeblockerbypass.mixin;

import io.netty.buffer.Unpooled;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CCustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

    @Shadow public abstract INetHandler getNetHandler();

    public MixinNetworkManager() {
    }

    @Inject(
            method = "sendPacket(Lnet/minecraft/network/IPacket;)V",
            at = {@At("HEAD")},
            cancellable = true
    )
    private void sendPacket(IPacket<?> packet, CallbackInfo callbackInfo) {
        if (packet instanceof CCustomPayloadPacket) {
            CCustomPayloadPacket customPayloadPacket = (CCustomPayloadPacket) packet;
            if (customPayloadPacket.getName() == CCustomPayloadPacket.BRAND) {
                getNetHandler().getNetworkManager().sendPacket(new CCustomPayloadPacket(CCustomPayloadPacket.BRAND,
                        (new PacketBuffer(Unpooled.buffer())).writeString("vanilla")), null);
                callbackInfo.cancel();
            }
        }
    }

}
