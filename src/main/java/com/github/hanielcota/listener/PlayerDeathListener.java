package com.github.hanielcota.listener;

import com.github.hanielcota.SkyPvpPlugin;
import com.github.hanielcota.utils.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

@RequiredArgsConstructor
public class PlayerDeathListener implements Listener {

    private final SkyPvpPlugin plugin;
    private final LocationUtils locationUtils;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);

        if (player.getLastDamageCause() != null && player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {
            return;
        }

        Location deathLocation = player.getLocation();
        deathLocation.getWorld().strikeLightningEffect(deathLocation);

        var spawnLocationOpt = locationUtils.getLocation("spawn");
        if (spawnLocationOpt.isEmpty()) {
            handleMissingSpawnLocation();
            return;
        }

        Player killer = player.getKiller();
        if (killer == null) {
            teleportPlayerToSpawnWithoutKiller(player, spawnLocationOpt.get());
            return;
        }

        teleportPlayerToSpawnWithKiller(player, killer, spawnLocationOpt.get());
    }

    private void handleMissingSpawnLocation() {
        plugin.getLog().warn(plugin.getMessageConfig().getConfiguration().getString("spawn-not-found", "Warp 'spawn' não encontrado. O jogador não foi teleportado após a morte."));
    }

    private void teleportPlayerToSpawnWithKiller(Player player, Player killer, Location spawnLocation) {
        player.teleport(spawnLocation);

        // Mensagens configuráveis ao jogador morto
        for (String message : plugin.getMessageConfig().getConfiguration().getStringList("death-messages-with-killer")) {
            player.sendMessage(message
                    .replace("{killer}", killer.getName())
                    .replace("{points}", String.valueOf(8)));
        }
    }

    private void teleportPlayerToSpawnWithoutKiller(Player player, Location spawnLocation) {
        player.teleport(spawnLocation);

        for (String message : plugin.getMessageConfig().getConfiguration().getStringList("death-messages-no-killer")) {
            player.sendMessage(message.replace("{points}", String.valueOf(8)));
        }
    }
}
