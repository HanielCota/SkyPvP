package com.github.hanielcota.points;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class PlayerPoints {

    private Map<Player, Integer> pointsMap = new HashMap<>();

    public void addPoints(Player player, int points) {
        if (player == null || points <= 0) return;

        pointsMap.put(player, Math.max(0, pointsMap.getOrDefault(player, 0) + points));
    }

    public void removePoints(Player player, int points) {
        if (player == null || points <= 0) return;

        int currentPoints = pointsMap.getOrDefault(player, 0);
        if (currentPoints <= 0) return;

        pointsMap.put(player, Math.max(0, currentPoints - points));
    }

    public int getPoints(Player player) {
        if (player == null) return 0;

        return pointsMap.getOrDefault(player, 0);
    }

    public void resetPoints(Player player) {
        if (player == null || !pointsMap.containsKey(player)) return;

        pointsMap.remove(player);
    }
}
