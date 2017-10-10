package net.thisisz.hermes.bungee.messaging.local;

import net.md_5.bungee.api.ChatColor;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.local.provider.LocalProvider;
import net.thisisz.hermes.bungee.messaging.MessagingController;
import net.thisisz.hermes.bungee.messaging.local.provider.LocalBungeeProvider;

import java.util.UUID;

public class LocalMessagingController {


    private MessagingController controller;
    private LocalProvider provider;

    public LocalMessagingController(MessagingController controller) {
        this.controller = controller;
        this.provider = getLocalProvider();
    }

    private LocalProvider getLocalProvider() {
        return new LocalBungeeProvider(this);
    }

    public HermesChat getPlugin() {
        return controller.getPlugin();
    }

    public void displayNewChatMessage(UUID sender, String server, String message) {
        provider.displayNewChatMessage(sender, getPlugin().getProxy().getServerInfo(server), message);
    }

    public void displayUserErrorMessage(UUID to, String message) {
        provider.displayUserErrorMessage(to, message);
    }

    public void displayUserNotification(UUID to, String message) {
        provider.displayUserNotification(to, message);
    }


}
