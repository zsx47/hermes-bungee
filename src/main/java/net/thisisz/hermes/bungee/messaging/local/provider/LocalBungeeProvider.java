package net.thisisz.hermes.bungee.messaging.local.provider;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.local.LocalMessagingController;
import net.thisisz.hermes.bungee.storage.CachedUser;

import java.util.UUID;

public class LocalBungeeProvider implements LocalProvider {

    private LocalMessagingController localController;
    private ComponentBuilder bracketL;
    private ComponentBuilder bracketR;
    private ComponentBuilder colon;
    private ComponentBuilder carrotL;
    private ComponentBuilder carrotR;
    private ComponentBuilder spaceComp;


    public LocalBungeeProvider(LocalMessagingController parent) {
        this.localController = parent;
        this.bracketL = new ComponentBuilder("[").color(ChatColor.WHITE);
        this.bracketR = new ComponentBuilder("]").color(ChatColor.WHITE);
        this.colon = new ComponentBuilder(":").color(ChatColor.WHITE);
        this.carrotL = new ComponentBuilder("<").color(ChatColor.WHITE);
        this.carrotR = new ComponentBuilder(">").color(ChatColor.WHITE);
        this.spaceComp = new ComponentBuilder(" ");
    }

    public HermesChat getPlugin() {
        return localController.getPlugin();
    }

    @Override
    public void displayNewChatMessage(UUID player, ServerInfo server, String message) {
        ComponentBuilder serverName = new ComponentBuilder(server.getName());
        CachedUser user = getPlugin().getStorageController().getCachedUser(player);
        ComponentBuilder playerName = new ComponentBuilder(translateColorCodes(user.getDisplayName()));
        ComponentBuilder playerPrefix = new ComponentBuilder(translateColorCodes(user.getPrefix()));
        ComponentBuilder finalMessage = new ComponentBuilder("");
        finalMessage = finalMessage.append(carrotL.create()).append(spaceComp.create()).append(bracketL.color(ChatColor.DARK_GREEN).create())
                .append(serverName.color(ChatColor.GREEN).create()).append(bracketR.color(ChatColor.DARK_GREEN).create()).append(spaceComp.create())
                .append(playerPrefix.create()).append(playerName.create()).append(spaceComp.create()).append(carrotR.create())
                .append(colon.create()).append(spaceComp.create()).append(translateColorCodes(message));
        getPlugin().getProxy().broadcast(finalMessage.create());
    }

    @Override
    public void displayUserErrorMessage(UUID to, String message) {
        ComponentBuilder error = new ComponentBuilder(message).color(ChatColor.RED);
        getPlugin().getProxy().getPlayer(to).sendMessage(error.create());
    }

    @Override
    public void displayUserNotification(UUID to, String message) {
        ComponentBuilder notification = new ComponentBuilder(message);
        getPlugin().getProxy().getPlayer(to).sendMessage(notification.create());
    }

    private String translateColorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
