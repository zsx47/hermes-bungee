package net.thisisz.hermes.bungee.messaging;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.filter.FilterManager;
import net.thisisz.hermes.bungee.messaging.local.LocalMessagingController;
import net.thisisz.hermes.bungee.messaging.network.NetworkMessagingController;

import java.util.UUID;

public class MessagingController {

    private HermesChat plugin;
    private LocalMessagingController localController;
    private NetworkMessagingController networkController;
    private FilterManager filterManager;

    public MessagingController(HermesChat parent) {
        this.plugin = parent;
        this.localController = new LocalMessagingController(this);
        this.networkController = new NetworkMessagingController(this);
        this.filterManager = new FilterManager(this);
    }

    public HermesChat getPlugin() {
        return plugin;
    }

    //Methods prefixed with display are returns from network controller system.
    public void displayChatMessage(UUID sender, String server, String message) {
        this.localController.displayChatMessage(sender, server, message);
    }

    public void displayUserNotification(UUID to, String message) {
        this.localController.displayUserNotification(to, message);
    }

    public void displayUserErrorMessage(UUID to, String message) {
        this.localController.displayUserErrorMessage(to, message);
    }

    public void displayLoginNotification(UUID player, boolean vjoin) {
        localController.displayLoginNotification(player, vjoin);
    }


    public void displayLogoutNotification(UUID player) {
        localController.displayLogoutNotification(player);
    }

    //Methods prefixed with send new are sent out to network controller system, so that any information can be passed to other bungee proxies via non local only messaging provider i.e. redisbungee
    public void sendChatMessage(ProxiedPlayer sender, Server server, String message) {
        message = filterManager.filterMessage(message);
        networkController.sendChatMessage(sender, server, message);
    }

    public void sendNewErrorMessage(ProxiedPlayer to, String message) {
        networkController.sendNewUserErrorMessage(to, message);
    }

    public void sendNewNotification(UUID to, String message) {
        networkController.sendNewUserNotification(to, message);
    }

    public void sendNicknameUpdate(UUID uuid, String nickname) {
        networkController.sendNicknameUpdate(uuid, nickname);
    }

    public void sendLoginNotification(ProxiedPlayer commandSender) {
        networkController.sendLoginNotification(commandSender);
    }

    public void sendUserVanishStatus(UUID uuid, boolean status) {
        networkController.sendUserVanishStatus(uuid, status);
    }

    public void sendStaffChatMessage(ProxiedPlayer sender, Server server, String message) {
        networkController.sendStaffChatMessage(sender, server, message);
    }

    public void displayStaffChatMessage(UUID sender, String server, String message) {
        localController.displayStaffChatMessage(sender, server, message);
    }
}
