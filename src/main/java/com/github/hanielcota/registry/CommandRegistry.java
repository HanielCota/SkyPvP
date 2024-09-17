package com.github.hanielcota.registry;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import com.github.hanielcota.SkyPvpPlugin;
import com.github.hanielcota.commands.PointsCommand;
import com.github.hanielcota.commands.WarpCommand;
import com.github.hanielcota.logging.MinecraftLogger;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommandRegistry {

    private final SkyPvpPlugin plugin;
    private final MinecraftLogger logger = new MinecraftLogger("Commands");
    private PaperCommandManager commandManager;

    public void registerAllCommands() {
        commandManager = new PaperCommandManager(plugin);

        List<BaseCommand> commands = List.of(
                new WarpCommand(plugin.getLocationUtils()),
                new PointsCommand(plugin.getPlayerPoints())
        );

        logger.info("Iniciando o registro de comandos...");

        commands.forEach(this::registerCommand);

        logger.info("Todos os comandos foram registrados com sucesso.");
    }

    private void registerCommand(BaseCommand command) {
        commandManager.registerCommand(command);
        logger.info("Comando registrado: " + command.getClass().getSimpleName());
    }
}
