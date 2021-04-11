package moe.caramel.forgeblockerbypass.mixin;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.PacketDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.BiConsumer;

@Mixin(PacketDispatcher.class)
public abstract class MixinPacketDispatcher {

    @Shadow(remap = false)
    BiConsumer<ResourceLocation, PacketBuffer> packetSink;

    /**
     * @author LemonCaramel
     * @reason Bypass FML2 Detect
     */
    @Overwrite(remap = false)
    public void sendPacket(ResourceLocation resourceLocation, PacketBuffer buffer) {
        if (!resourceLocation.getNamespace().equals("fml"))
            packetSink.accept(resourceLocation, buffer);
    }

}
