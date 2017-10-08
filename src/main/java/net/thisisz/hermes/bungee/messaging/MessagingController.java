package net.thisisz.hermes.bungee.messaging;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.local.LocalMessagingController;
import net.thisisz.hermes.bungee.messaging.network.NetworkMessagingController;

import java.util.UUID;

public class MessagingController {

    private HermesChat plugin;
    private LocalMessagingController localController;
    private NetworkMessagingController networkController;

    public MessagingController(HermesChat parent) {
        this.plugin = parent;
        this.localController = new LocalMessagingController(this);
        this.networkController = new NetworkMessagingController(this);
    }

    public HermesChat getPlugin() {
        return plugin;
    }

    //Methods prefixed with display are returns from network controller system.
    public void displayMessageLocal(UUID sender, String server, String message) {
        this.localController.displayNewChatMessage(sender, server, message);
    }

    public void displayUserNotification(UUID to, String message) {
        this.localController.displayUserNotification(to, message);
    }

    public void displayUserErrorMessage(UUID to, String message) {
        this.localController.displayUserErrorMessage(to, message);
    }

    //Methods prefixed with send new are sent out to network controller system, so that any information can be passed to other proxies in the network.
    public void sendNewNetworkChatMessage(ProxiedPlayer sender, Server server, String message) {
        networkController.sendNewNetworkChatMessage(sender, server, message);
    }

    public void sendNewErrorMessage(ProxiedPlayer to, String message) {
        networkController.sendNewUserErrorMessage(to, message);
    }

    public void sendNewNotification(ProxiedPlayer to, String message) {
        networkController.sendNewUserNotification(to, message);
    }


}
