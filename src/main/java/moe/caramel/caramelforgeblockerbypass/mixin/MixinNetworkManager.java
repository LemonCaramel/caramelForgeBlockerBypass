package moe.caramel.caramelforgeblockerbypass.mixin;

import io.netty.buffer.Unpooled;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CCustomPayloadPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

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
                try {
                    customPayloadPacket.readPacketData(new PacketBuffer(Unpooled.buffer())
                            .writeResourceLocation(CCustomPayloadPacket.BRAND).writeString("vanilla"));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

}
