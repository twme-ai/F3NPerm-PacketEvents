package nexus.slime.f3nperm.provider;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;
import nexus.slime.f3nperm.F3NPermPlugin;
import nexus.slime.f3nperm.OpPermissionLevel;
import org.bukkit.entity.Player;

public final class PacketEventsProvider extends PacketListenerAbstract implements Provider {
    private F3NPermPlugin plugin;

    @Override
    public void register(F3NPermPlugin plugin) {
        this.plugin = plugin;
        PacketEvents.getAPI().getEventManager().registerListener(this);
    }

    @Override
    public void unregister() {
        PacketEvents.getAPI().getEventManager().unregisterListener(this);
    }

    @Override
    public void update(Player player) {
        OpPermissionLevel level = plugin.getF3NPermPermissionLevel(player);
        WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(
                player.getEntityId(),
                level.toStatus()
        );

        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.ENTITY_STATUS) {
            return;
        }

        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(event);

        if (packet.getEntityId() != player.getEntityId()) {
            return;
        }

        if (OpPermissionLevel.fromStatus(packet.getStatus()) == null) {
            return;
        }

        packet.setStatus(plugin.getF3NPermPermissionLevel(player).toStatus());
    }
}
