package com.github.hanielcota.listener;

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

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.VOID) return;

        event.setCancelled(true);

        Location spawnLocation = locationUtils.getLocation("spawn").orElse(null);

        if (spawnLocation == null) {
            player.sendMessage("Localização de spawn não encontrada!");
            return;
        }

        player.teleport(spawnLocation);
        player.sendMessage(new String[]{
                "",
                "§c§l[ATENÇÃO] §cVocê caiu no Void e morreu!",
                "§cVocê perdeu §c§l-2 §cpontos por isso.",
                "§cTente evitar cair para manter seus pontos intactos.",
                "",
        });
    }
}
