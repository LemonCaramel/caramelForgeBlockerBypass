package moe.caramel.forgeblockerbypass.mixin;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.handshake.client.CHandshakePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CHandshakePacket.class)
public abstract class MixinHandshake {

    @Shadow
    private int protocolVersion;
    @Shadow
    private String hostName;
    @Shadow
    private int port;
    @Shadow
    private ProtocolType intention;

    /**
     * @author LemonCaramel
     * @reason Remove FML2 character.
     */
    @Overwrite
    public void write(PacketBuffer buf) {
        buf.writeVarInt(this.protocolVersion);
        buf.writeUtf(this.hostName);
        buf.writeShort(this.port);
        buf.writeVarInt(this.intention.getId());
    }

}
