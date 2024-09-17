package com.github.hanielcota.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.hanielcota.points.PlayerPoints;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@CommandAlias("points")
@AllArgsConstructor
public class PointsCommand extends BaseCommand {

    private final PlayerPoints playerPoints;

    @Default
    @Syntax("/points view [player]")
    @Description("Veja seus pontos ou os pontos de outro jogador")
    public void onViewPoints(Player sender, @Optional Player target) {
        if (target == null) target = sender;

        int points = playerPoints.getPoints(target);
        sender.sendMessage("§e " + target.getName() + " tem " + points + " pontos.");
    }

    @CommandAlias("add")
    @Syntax("/points add <player> <amount>")
    @Description("Adicione pontos a um jogador")
    public void onAddPoints(Player sender, Player target, int amount) {
        if (amount <= 0) {
            sender.sendMessage("§cA quantidade de pontos deve ser positiva.");
            return;
        }

        playerPoints.addPoints(target, amount);
        sender.sendMessage("§aAdicionado " + amount + " pontos a " + target.getName() + ".");
    }

    @CommandAlias("remove")
    @Syntax("/points remove <player> <amount>")
    @Description("Remova pontos de um jogador")
    public void onRemovePoints(Player sender, Player target, int amount) {
        if (amount <= 0) {
            sender.sendMessage("§cA quantidade de pontos deve ser positiva.");
            return;
        }

        playerPoints.removePoints(target, amount);
        sender.sendMessage("§cRemovido " + amount + " pontos de " + target.getName() + ".");
    }
}
