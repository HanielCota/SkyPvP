package com.github.hanielcota.listener;

import com.github.hanielcota.utils.ConfigUtils;
import com.github.hanielcota.utils.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@RequiredArgsConstructor
public class VoidFallListener implements Listener {

    private final LocationUtils locationUtils;
    private final ConfigUtils messageConfig;

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.VOID) return;

        event.setCancelled(true);

        Location spawnLocation = locationUtils.getLocation("spawn").orElse(null);

        if (spawnLocation == null) {
            player.sendMessage(String.join("\n", messageConfig.getConfiguration().getStringList("spawn-not-found")));
            return;
        }

        player.teleport(spawnLocation);

        messageConfig.getConfiguration().getStringList("void-fall-messages").forEach(player::sendMessage);
    }
}
