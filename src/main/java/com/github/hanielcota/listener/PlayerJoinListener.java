package com.github.hanielcota.listener;

import com.github.hanielcota.SkyPvpPlugin;
import com.github.hanielcota.utils.LocationUtils;
import com.github.hanielcota.utils.TitleUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final SkyPvpPlugin plugin;
    private final LocationUtils locationUtils;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        Optional<Location> spawnLocation = locationUtils.getLocation("spawn");
        if (spawnLocation.isEmpty()) {
            logSpawnLocationNotFound();
            return;
        }

        teleportPlayerToSpawn(player);
    }

    private void logSpawnLocationNotFound() {
        // Mensagem de log configurável
        plugin.getLog().warn(plugin.getMessageConfig().getConfiguration().getString("spawn-not-found", "Warp 'spawn' não encontrado. O jogador não foi teleportado."));
    }

    private void teleportPlayerToSpawn(Player player) {
        locationUtils.teleportPlayer(player, "spawn");

        // Título configurável
        String title = plugin.getMessageConfig().getConfiguration().getString("join-title", "§e§lSKY PVP");
        String subtitle = plugin.getMessageConfig().getConfiguration().getString("join-subtitle", "Você entrou no Spawn.");

        TitleUtils.sendTitle(player, title, subtitle);
    }
}
