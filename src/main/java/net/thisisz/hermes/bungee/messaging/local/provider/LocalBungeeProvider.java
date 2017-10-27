package net.thisisz.hermes.bungee.messaging.local.provider;

import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.caching.PermissionData;
import me.lucko.luckperms.api.caching.UserData;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
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
    public void displayChatMessage(CachedUser player, ServerInfo server, String message) {
        ComponentBuilder serverName = new ComponentBuilder(server.getName());
        ComponentBuilder playerName = new ComponentBuilder(translateCodes(player.getDisplayName()));
        ComponentBuilder realName = new ComponentBuilder(player.getName());
        HoverEvent showRealName = new HoverEvent(HoverEvent.Action.SHOW_TEXT, realName.create());
        playerName.event(showRealName);
        ComponentBuilder playerPrefix = new ComponentBuilder(translateCodes(player.getPrefix()));
        ComponentBuilder finalMessage = new ComponentBuilder("");
        finalMessage = finalMessage.append(carrotL.create()).append(spaceComp.create()).append(bracketL.color(ChatColor.DARK_GREEN).create())
                .append(serverName.color(ChatColor.GREEN).create()).append(bracketR.color(ChatColor.DARK_GREEN).create()).append(spaceComp.create())
                .append(playerPrefix.create()).append(playerName.create()).append(spaceComp.create()).append(carrotR.create())
                .append(colon.create()).append(spaceComp.create()).append(translateCodes(message));
        getPlugin().getProxy().broadcast(finalMessage.create());
    }

    @Override
    public void displayUserErrorMessage(CachedUser to, String message) {
        ComponentBuilder error = new ComponentBuilder(message).color(ChatColor.RED);
        getPlugin().getProxy().getPlayer(to.getUUID()).sendMessage(error.create());
    }

    @Override
    public void displayUserNotification(CachedUser to, String message) {
        ComponentBuilder notification = new ComponentBuilder(translateCodes(message));
        getPlugin().getProxy().getPlayer(to.getUUID()).sendMessage(notification.create());
    }

    @Override
    public void displayLoginNotification(CachedUser player, boolean vjoin) {
        Boolean playervjoin;
        if (vjoin) {
            playervjoin = getUserPermission(player, "hermes.vanishjoin");
        } else {
            playervjoin = false;
        }
        ComponentBuilder realName = new ComponentBuilder(player.getName());
        HoverEvent showRealName = new HoverEvent(HoverEvent.Action.SHOW_TEXT, realName.create());
        ComponentBuilder playerName = new ComponentBuilder(translateCodes(player.getDisplayName()));
        playerName.event(showRealName);
        ComponentBuilder playerPrefix = new ComponentBuilder(translateCodes(player.getPrefix()));
        ComponentBuilder finalMessage = new ComponentBuilder("");
        finalMessage = finalMessage.append(new ComponentBuilder(translateCodes("&ePlayer ")).create()).append(playerPrefix.create())
                .append(playerName.create()).append(new ComponentBuilder(translateCodes(" &ehas logged on")).create());
        ComponentBuilder finalMessageVjoinSee = new ComponentBuilder("");
        finalMessageVjoinSee = finalMessageVjoinSee.append(new ComponentBuilder(translateCodes("&bPlayer ")).create()).append(playerPrefix.create())
                .append(playerName.create()).append(new ComponentBuilder(translateCodes(" &bhas joined silently")).create());
        for (ProxiedPlayer proxyPlayer : getPlugin().getProxy().getPlayers()) {
            if (playervjoin) {
                if (proxyPlayer.hasPermission("hermes.vanishjoin.see")) {
                    proxyPlayer.sendMessage(finalMessageVjoinSee.create());
                }
            } else {
                proxyPlayer.sendMessage(finalMessage.create());
            }
        }
    }

    @Override
    public void displayLogoutNotification(CachedUser player) {
        Boolean vjoin = getUserPermission(player, "hermes.vanishjoin");
        ComponentBuilder realName = new ComponentBuilder(player.getName());
        HoverEvent showRealName = new HoverEvent(HoverEvent.Action.SHOW_TEXT, realName.create());
        ComponentBuilder playerName = new ComponentBuilder(translateCodes(player.getDisplayName()));
        playerName.event(showRealName);
        ComponentBuilder playerPrefix = new ComponentBuilder(translateCodes(player.getPrefix()));
        ComponentBuilder finalMessage = new ComponentBuilder("");
        finalMessage = finalMessage.append(new ComponentBuilder(translateCodes("&ePlayer ")).create()).append(playerPrefix.create())
                .append(playerName.create()).append(new ComponentBuilder(translateCodes(" &ehas logged off")).create());
        for (ProxiedPlayer proxyPlayer : getPlugin().getProxy().getPlayers()) {
            if (vjoin) {
                 if (proxyPlayer.hasPermission("hermes.vanishjoin.see")) {
                     proxyPlayer.sendMessage(finalMessage.create());
                 }
            } else {
                proxyPlayer.sendMessage(finalMessage.create());
            }
        }
    }

    private boolean getUserPermission(CachedUser user, String permission) {
        UUID luckUUID = getPlugin().getLuckApi().getUuidCache().getUUID(user.getUUID());
        UserData udat;
        try {
            udat = getPlugin().getLuckApi().getUser(luckUUID).getCachedData();
        } catch (NullPointerException e) {
            getPlugin().getLuckApi().getStorage().loadUser(luckUUID).join();
            udat = getPlugin().getLuckApi().getUser(luckUUID).getCachedData();
            getPlugin().getLuckApi().cleanupUser(getPlugin().getLuckApi().getUser(luckUUID));
        }
        PermissionData pdat = udat.getPermissionData(Contexts.global());
        return pdat.getPermissionValue(permission).asBoolean();
    }

    private String translateCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
