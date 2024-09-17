package com.github.hanielcota.utils;

import com.github.hanielcota.logging.MinecraftLogger;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

@Getter
public class ConfigUtils {

    private final Plugin plugin;
    private final String filename;
    private final MinecraftLogger logger = new MinecraftLogger("ConfigUtils");
    private File configFile;
    private FileConfiguration configuration;

    public ConfigUtils(Plugin plugin, String filename) {
        this.plugin = plugin;
        this.filename = filename.endsWith(".yml") ? filename : filename + ".yml";
        createFile();
    }

    private void createFile() {
        configFile = new File(plugin.getDataFolder(), filename);

        if (configFile.exists()) {
            loadConfiguration();
            return;
        }

        if (!configFile.getParentFile().exists() && !configFile.getParentFile().mkdirs()) {
            logger.error("Falha ao criar a estrutura de diretórios para: " + configFile);
            return;
        }

        plugin.saveResource(filename, false);
        loadConfiguration();
    }

    private void loadConfiguration() {
        configuration = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfiguration() {
        if (configuration == null) {
            logger.error("Configuração não carregada, não é possível salvar " + filename);
            return;
        }

        try {
            configuration.save(configFile);
        } catch (IOException e) {
            logger.error("Não foi possível salvar a configuração no arquivo " + configFile + " " + e);
        }
    }

    public void reloadConfiguration() {
        loadConfiguration();
    }
}
