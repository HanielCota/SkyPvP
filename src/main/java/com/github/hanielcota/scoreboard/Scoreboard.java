package com.github.hanielcota.scoreboard;

import com.github.hanielcota.SkyPvpPlugin;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class Scoreboard implements Listener {

    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final SkyPvpPlugin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FastBoard board = new FastBoard(player);

        board.updateTitle("§6§lFLORUIT MC");

        this.boards.put(player.getUniqueId(), board);

        updateBoard(board);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        FastBoard board = this.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    private void updateBoard(FastBoard board) {
        board.updateLines(
                "      §7SkyPvp   ",
                "",
                "  §fOnline: §e" + Bukkit.getServer().getOnlinePlayers().size(),
                "",
                "  §e§lGERAL:",
                "  §fAbates: §a" + 0,
                "  §fMortes: §a" + 0,
                "  §fRank: §eNoob",
                "",
                "  §fPontos: §a " + plugin.getPlayerPoints().getPoints(board.getPlayer()),
                "",
                "§fplay.§bflor§au§eitmc§f.com"
        );
    }

    public void startScoreboardUpdateTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (FastBoard board : boards.values()) {
                    updateBoard(board);
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }
}
