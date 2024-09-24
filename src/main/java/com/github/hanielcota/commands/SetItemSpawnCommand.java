package com.github.hanielcota.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.github.hanielcota.utils.LocationUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("setitemspawn")
@CommandPermission("skypvp.admin")
@Description("Define o local de spawn de itens.")
@AllArgsConstructor
public class SetItemSpawnCommand extends BaseCommand {

    private final LocationUtils locationUtils;

    @Default
    public void onSetItemSpawn(Player player) {
        // Check if player is null (this ensures the command was executed by a player and not console)
        if (player == null) {
            return; // Optionally, send a message to the console if needed
        }

        // Get the player's current location
        Location location = player.getLocation();

        // Check if the location is null (though it usually isn't, it's a good practice)
        if (location == null) {
            player.sendMessage("Erro: Não foi possível obter a localização atual.");
            return;
        }

        // Save the location using the utility class
        locationUtils.setLocation("itemSpawn", location);

        // Confirm to the player that the location was saved
        player.sendMessage("Localização específica de spawn de itens foi salva com sucesso!");
    }
}
