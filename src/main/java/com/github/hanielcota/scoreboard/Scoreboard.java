package com.github.hanielcota.scoreboard;

import com.github.hanielcota.SkyPvpPlugin;
import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class Scoreboard implements Listener {

    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final SkyPvpPlugin plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        FastBoard board = new FastBoard(player);

        String title = plugin.getMessageConfig().getConfiguration().getString("scoreboard.title", "§6§lFLORUIT MC");
        if (title == null) return;

        board.updateTitle(applyPlaceholders(player, title));
        this.boards.put(player.getUniqueId(), board);

        updateBoard(board);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        FastBoard board = this.boards.remove(player.getUniqueId());
        if (board == null) return;

        board.delete();
    }

    private void updateBoard(FastBoard board) {
        if (board == null) return;

        Player player = board.getPlayer();
        if (player == null) return;

        List<String> lines = plugin.getMessageConfig().getConfiguration().getStringList("scoreboard.lines");
        if (lines == null || lines.isEmpty()) return;

        lines.replaceAll(s -> applyPlaceholders(player, s
                .replace("{online}", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                .replace("{kills}", String.valueOf(0))
                .replace("{deaths}", String.valueOf(0))
                .replace("{rank}", "Noob")
                .replace("{points}", String.valueOf(plugin.getPlayerPoints().getPoints(player)))));

        board.updateLines(lines.toArray(new String[0]));
    }

    public void startScoreboardUpdateTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (FastBoard board : boards.values()) {
                    if (board != null) updateBoard(board);
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    private String applyPlaceholders(Player player, String text) {
        if (player == null || text == null) return "";


        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }
}
