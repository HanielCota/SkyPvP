package com.github.hanielcota.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.github.hanielcota.utils.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@CommandAlias("warp")
public class WarpCommand extends BaseCommand {

    private final LocationUtils locationUtils;

    /**
     * Comando para definir uma nova warp com base na posição atual do jogador.
     *
     * @param sender O emissor do comando.
     * @param args   Argumentos do comando. O nome da warp é necessário.
     */
    @Subcommand("setwarp")
    @Description("Define uma nova warp com base na posição atual do jogador.")
    public void onSetWarp(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Este comando só pode ser executado por jogadores.");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Você deve fornecer um nome para a warp.");
            return;
        }

        String warpName = args[0];
        locationUtils.setLocation(warpName, player.getLocation());
        sender.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' definida com sucesso!");
    }

    /**
     * Comando para teleportar o jogador para a warp especificada.
     *
     * @param sender O emissor do comando.
     * @param args   Argumentos do comando. O nome da warp é necessário.
     */
    @Default
    @Description("Teleporta o jogador para a warp especificada.")
    public void onWarp(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Este comando só pode ser executado por jogadores.");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Você deve fornecer o nome da warp.");
            return;
        }

        String warpName = args[0];
        if (locationUtils.teleportPlayer(player, warpName)) {
            player.sendMessage(ChatColor.GREEN + "Teleportado para a warp '" + warpName + "'!");
            return;
        }

        player.sendMessage(ChatColor.RED + "Warp '" + warpName + "' não encontrada.");
    }

    /**
     * Comando para deletar uma warp existente.
     *
     * @param sender O emissor do comando.
     * @param args   Argumentos do comando. O nome da warp é necessário.
     */
    @Subcommand("delwarp")
    @Description("Deleta a warp especificada.")
    public void onDelWarp(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Este comando só pode ser executado por jogadores.");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Você deve fornecer o nome da warp.");
            return;
        }

        String warpName = args[0];
        locationUtils.removeLocation(warpName);
        sender.sendMessage(ChatColor.GREEN + "Warp '" + warpName + "' deletada com sucesso!");
    }
}
