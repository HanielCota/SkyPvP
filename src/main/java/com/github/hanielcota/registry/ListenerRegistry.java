package com.github.hanielcota.registry;

import com.github.hanielcota.SkyPvpPlugin;
import com.github.hanielcota.listener.*;
import com.github.hanielcota.logging.MinecraftLogger;
import com.github.hanielcota.scoreboard.Scoreboard;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.List;

@RequiredArgsConstructor
public class ListenerRegistry {

    private final SkyPvpPlugin plugin;
    private final MinecraftLogger logger = new MinecraftLogger("Listener");

    public void registerAllEventListeners() {
        List<Listener> listeners = List.of(
                new PlayerJoinListener(plugin, plugin.getLocationUtils()),
                new PlayerDeathListener(plugin, plugin.getLocationUtils()),
                new VoidFallListener(plugin.getLocationUtils(), plugin.getMessageConfig()),
                new Scoreboard(plugin),
                new PlayerKillListener(plugin),
                new PlayerQuitListener());

        listeners.forEach(this::registerListener);

        logger.info("Todos os listeners de eventos foram registrados com sucesso.");
    }

    private void registerListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        logger.info("Listener registrado: " + listener.getClass().getSimpleName());
    }
}
