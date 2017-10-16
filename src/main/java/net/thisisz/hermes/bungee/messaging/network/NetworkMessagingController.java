package net.thisisz.hermes.bungee.messaging.network;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.MessagingController;
import net.thisisz.hermes.bungee.messaging.network.provider.LocalOnlyProvider;
import net.thisisz.hermes.bungee.messaging.network.provider.NetworkProvider;
import net.thisisz.hermes.bungee.messaging.network.provider.RedisBungeeProvider;

import java.util.UUID;

public class NetworkMessagingController {

    private MessagingController controller;
    private NetworkProvider provider;

    public NetworkMessagingController(MessagingController parent) {
        this.controller = parent;
        setNetworkProvider();
    }

    private void setNetworkProvider() {
        if (getPlugin().getRedisBungeeAPI() == null) {
            this.provider = new LocalOnlyProvider(this);
        } else {
            this.provider = new RedisBungeeProvider(this);
        }
    }

    public HermesChat getPlugin() {
        return controller.getPlugin();
    }

    public void displayMessageLocal(UUID sender, String server, String message) {
        controller.displayMessageLocal(sender, server, message);
    }

    public void displayUserErrorMessage(UUID to, String message) {
        controller.displayUserErrorMessage(to, message);
    }

    public void displayUserNotification(UUID to, String message) {
        controller.displayUserNotification(to, message);
    }

    public void sendNewNetworkChatMessage(ProxiedPlayer sender, Server server, String message) {
        provider.sendNewNetworkChatMessage(sender.getUniqueId(), server.getInfo().getName(), message);
    }

    public void sendNewUserErrorMessage(ProxiedPlayer to, String message) {
        provider.sendNewUserErrorMessage(to.getUniqueId(), message);
    }

    public void sendNewUserNotification(UUID to, String message) {
        provider.sendNewUserNotification(to, message);
    }

}
