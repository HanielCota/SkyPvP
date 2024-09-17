package com.github.hanielcota.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Classe utilitária para enviar títulos e subtítulos aos jogadores usando pacotes.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TitleUtils {

    private static final int DEFAULT_FADE_IN = 10;
    private static final int DEFAULT_STAY = 70;
    private static final int DEFAULT_FADE_OUT = 20;

    /**
     * Envia um título e um subtítulo a um jogador.
     *
     * @param player   O jogador que receberá o título.
     * @param title    O título a ser enviado.
     * @param subtitle O subtítulo a ser enviado.
     * @param fadeIn   O tempo de fade-in (em ticks).
     * @param stay     O tempo de permanência (em ticks).
     * @param fadeOut  O tempo de fade-out (em ticks).
     */
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (player == null || !player.isOnline()) {
            return;
        }

        IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        IChatBaseComponent subtitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

        sendPacket(player, new PacketPlayOutTitle(EnumTitleAction.TITLE, titleComponent, fadeIn, stay, fadeOut));
        sendPacket(player, new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleComponent, fadeIn, stay, fadeOut));
    }

    /**
     * Envia um título e um subtítulo a todos os jogadores online.
     *
     * @param title    O título a ser enviado.
     * @param subtitle O subtítulo a ser enviado.
     * @param fadeIn   O tempo de fade-in (em ticks).
     * @param stay     O tempo de permanência (em ticks).
     * @param fadeOut  O tempo de fade-out (em ticks).
     */
    public static void sendTitleToAll(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendTitle(player, title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    /**
     * Envia um título sem subtítulo a um jogador com valores padrão para fadeIn, stay e fadeOut.
     *
     * @param player  O jogador que receberá o título.
     * @param title   O título a ser enviado.
     */
    public static void sendTitle(Player player, String title) {
        sendTitle(player, title, "", DEFAULT_FADE_IN, DEFAULT_STAY, DEFAULT_FADE_OUT);
    }

    /**
     * Envia um título e um subtítulo a um jogador com valores padrão para fadeIn, stay e fadeOut.
     *
     * @param player   O jogador que receberá o título.
     * @param title    O título a ser enviado.
     * @param subtitle O subtítulo a ser enviado.
     */
    public static void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, title, subtitle, DEFAULT_FADE_IN, DEFAULT_STAY, DEFAULT_FADE_OUT);
    }

    /**
     * Envia um título e um subtítulo a todos os jogadores online com valores padrão para fadeIn, stay e fadeOut.
     *
     * @param title    O título a ser enviado.
     * @param subtitle O subtítulo a ser enviado.
     */
    public static void sendTitleToAll(String title, String subtitle) {
        sendTitleToAll(title, subtitle, DEFAULT_FADE_IN, DEFAULT_STAY, DEFAULT_FADE_OUT);
    }

    /**
     * Envia um título sem subtítulo a todos os jogadores online com valores padrão para fadeIn, stay e fadeOut.
     *
     * @param title   O título a ser enviado.
     */
    public static void sendTitleToAll(String title) {
        sendTitleToAll(title, "", DEFAULT_FADE_IN, DEFAULT_STAY, DEFAULT_FADE_OUT);
    }

    private static void sendPacket(Player player, PacketPlayOutTitle packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
