package com.github.hanielcota.task;

import com.github.hanielcota.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.Random;

public class ItemSpawnTask implements Runnable {

    private final LocationUtils locationUtils;
    private final Random random = new Random();

    public ItemSpawnTask(LocationUtils locationUtils) {
        this.locationUtils = locationUtils;
    }

    @Override
    public void run() {
        Optional<Location> spawnLocationOpt = locationUtils.getLocation("itemSpawn");

        if (spawnLocationOpt.isEmpty()) {
            Bukkit.getLogger().info("Nenhuma localização de spawn de itens específica foi definida.");
            return;
        }

        Location spawnLocation = spawnLocationOpt.get();

        Material[] materials = {Material.DIAMOND, Material.GOLD_INGOT, Material.IRON_INGOT, Material.EMERALD};
        Material randomMaterial = materials[random.nextInt(materials.length)];

        ItemStack itemStack = new ItemStack(randomMaterial);
        Item item = spawnLocation.getWorld().dropItemNaturally(spawnLocation, itemStack);
        Bukkit.getLogger().info("Item " + randomMaterial.name() + " spawnado na localização de itens.");
    }

    public static void startTask(JavaPlugin plugin, LocationUtils locationUtils) {
        Bukkit.getScheduler().runTaskTimer(plugin, new ItemSpawnTask(locationUtils), 0L, 600L); // 600L = 30 segundos
    }
}
