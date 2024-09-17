package com.github.hanielcota.utils;

import com.github.hanielcota.SkyPvpPlugin;
import com.github.hanielcota.logging.MinecraftLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LocationUtils {

    private final Map<String, Location> locations;
    private final SkyPvpPlugin plugin;
    private final MinecraftLogger logger = new MinecraftLogger("LocationUtils");

    public LocationUtils(SkyPvpPlugin plugin) {
        this.plugin = plugin;
        this.locations = new HashMap<>();
        loadLocationsFromConfig();
    }

    public static String serializeLocation(Location location) {
        return String.format(
                "%s,%f,%f,%f,%f,%f",
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }

    public static Location deserializeLocation(String serializedLocation) {
        String[] parts = serializedLocation.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Localização serializada inválida.");
        }

        World world = Bukkit.getWorld(parts[0]);
        if (world == null) {
            throw new IllegalArgumentException("Mundo não encontrado: " + parts[0]);
        }

        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }

    public void setLocation(String name, Location location) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }

        if (location == null) {
            throw new IllegalArgumentException("A localização não pode ser nula.");
        }

        locations.put(name, location);

        updateConfig(name, serializeLocation(location));
    }

    public Optional<Location> getLocation(String name) {
        return Optional.ofNullable(locations.get(name));
    }

    public void removeLocation(String name) {
        locations.remove(name);
        updateConfig(name, null);
    }

    public Map<String, Location> getAllLocations() {
        return new HashMap<>(locations);
    }

    private void loadLocationsFromConfig() {
        FileConfiguration configuration = getConfiguration();
        if (!configuration.contains("locations")) {
            logger.info("Nenhuma localização encontrada na configuração.");
            return;
        }

        configuration.getConfigurationSection("locations").getKeys(false).forEach(name -> {
            String serializedLocation = configuration.getString("locations." + name);

            if (serializedLocation != null) {
                try {
                    Location location = deserializeLocation(serializedLocation);
                    locations.put(name, location);
                } catch (IllegalArgumentException e) {
                    logger.error("Falha ao carregar a localização: " + name + " - " + e.getMessage());
                }
            }
        });
    }

    private void updateConfig(String name, String serializedLocation) {
        FileConfiguration configuration = getConfiguration();
        configuration.set("locations." + name, serializedLocation);
        saveConfig();
    }

    private FileConfiguration getConfiguration() {
        return plugin.getLocationConfig().getConfiguration();
    }

    private void saveConfig() {
        plugin.getLocationConfig().saveConfiguration();
    }

    public boolean teleportPlayer(Player player, String name) {
        Optional<Location> locationOpt = getLocation(name);
        if (locationOpt.isEmpty()) {
            return false;
        }

        player.teleport(locationOpt.get());
        return true;
    }

    public void saveLocations() {
        FileConfiguration configuration = getConfiguration();
        locations.forEach((name, location) -> configuration.set("locations." + name, serializeLocation(location)));
        saveConfig();
    }

    public void loadLocations(Map<String, String> serializedLocations) {
        serializedLocations.forEach((name, serializedLocation) -> {
            try {
                Location location = deserializeLocation(serializedLocation);
                locations.put(name, location);
            } catch (IllegalArgumentException e) {
                logger.error("Falha ao carregar a localização: " + name + " - " + e.getMessage());
            }
        });
        saveLocations();
    }
}
