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
        plugin.getLog().warn("Warp 'spawn' não encontrado. O jogador não foi teleportado após a morte.");
    }

    private void teleportPlayerToSpawnWithKiller(Player player, Player killer, Location spawnLocation) {
        player.teleport(spawnLocation);
        player.sendMessage(new String[]{
                "",
                "§cVocê foi morto por §e" + killer.getName(),
                "§cE perdeu §e" + 8 + " pontos §ccomo resultado.",
                ""
        });
    }

    private void teleportPlayerToSpawnWithoutKiller(Player player, Location spawnLocation) {
        player.teleport(spawnLocation);
        player.sendMessage(new String[]{
                "",
                "§cVocê morreu!",
                "§cE perdeu §e" + 8 + " pontos §ccomo resultado.",
                ""
        });
    }
}
