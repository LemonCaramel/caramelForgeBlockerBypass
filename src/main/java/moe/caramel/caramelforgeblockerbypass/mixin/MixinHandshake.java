package moe.caramel.caramelforgeblockerbypass.mixin;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.handshake.client.CHandshakePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CHandshakePacket.class)
public abstract class MixinHandshake {

    @Shadow
    private int port;
    @Shadow
    private String ip;
    @Shadow
    private int protocolVersion;
    @Shadow
    private ProtocolType requestedState;

    /**
     * @author LemonCaramel
     * @reason Remove FML2 character.
     */
    @Overwrite
    public void writePacketData(PacketBuffer buf) {
        buf.writeVarInt(this.protocolVersion);
        buf.writeString(this.ip);
        buf.writeShort(this.port);
        buf.writeVarInt(this.requestedState.getId());
    }

}
