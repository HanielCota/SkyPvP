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
        if (player == null) {
            return;
        }

        Location location = player.getLocation();

        if (location == null) {
            player.sendMessage("Erro: Não foi possível obter a localização atual.");
            return;
        }

        locationUtils.setLocation("itemSpawn", location);

        player.sendMessage("Localização específica de spawn de itens foi salva com sucesso!");
    }
}
