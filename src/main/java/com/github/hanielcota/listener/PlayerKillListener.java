package com.github.hanielcota.listener;

import com.github.hanielcota.SkyPvpPlugin;
import lombok.AllArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@AllArgsConstructor
public class PlayerKillListener implements Listener {

    private final SkyPvpPlugin plugin;

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player killedPlayer = event.getEntity();
        Player killer = killedPlayer.getKiller();

        if (killer == null) return;

        plugin.getPlayerPoints().addPoints(killer, 10);

        for (String message : plugin.getMessageConfig().getConfiguration().getStringList("player-kill-messages")) {
            killer.sendMessage(message.replace("{killed}", killedPlayer.getName()));
        }

        killer.playSound(killer.getLocation(), Sound.CAT_HISS, 1f, 1f);
    }
}
