package net.thisisz.hermes.bungee.messaging.local;

import net.md_5.bungee.api.connection.Server;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.asynctask.LoadPlayerThenCallback;
import net.thisisz.hermes.bungee.callback.Callback;
import net.thisisz.hermes.bungee.messaging.local.callback.*;
import net.thisisz.hermes.bungee.messaging.local.provider.LocalProvider;
import net.thisisz.hermes.bungee.messaging.MessagingController;
import net.thisisz.hermes.bungee.messaging.local.provider.LocalBungeeProvider;
import net.thisisz.hermes.bungee.storage.StorageController;

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

    private StorageController getStorageController() {
        return controller.getPlugin().getStorageController();
    }

    public void displayChatMessage(UUID sender, String server, String message) {
        if (getStorageController().isLoaded(sender)) {
            provider.displayChatMessage(getStorageController().getCachedUser(sender), getPlugin().getProxy().getServerInfo(server), message);
        } else {
            //load user in async call then call this method again
            loadCachedUserThenCallback( sender, new DisplayChatMessage(getPlugin(), sender, server, message));
        }
    }

    public void displayUserErrorMessage(UUID to, String message) {
        if (getStorageController().isLoaded(to)) {
            provider.displayUserErrorMessage(getStorageController().getCachedUser(to), message);
        } else {
            loadCachedUserThenCallback(to, new DisplayUserErrorMessage(getPlugin(), to, message));
        }
    }

    public void displayUserNotification(UUID to, String message) {
        if (getStorageController().isLoaded(to)) {
            provider.displayUserNotification(getStorageController().getCachedUser(to), message);
        } else {
            loadCachedUserThenCallback(to, new DisplayUserNotification(getPlugin(), to, message));
        }
    }

    public void displayLoginNotification(UUID player, boolean vjoin) {
        if (getStorageController().isLoaded(player)) {
            provider.displayLoginNotification(getStorageController().getCachedUser(player), vjoin);
        } else {
            loadCachedUserThenCallback(player, new DisplayLoginNotification(getPlugin(), player, vjoin));
        }
    }

    public void displayLogoutNotification(UUID player) {
        if (getStorageController().isLoaded(player)) {
            provider.displayLogoutNotification(getStorageController().getCachedUser(player));
        } else {
            loadCachedUserThenCallback(player, new DisplayLogoutNotification(getPlugin(), player));
        }
    }

    private void loadCachedUserThenCallback(UUID uuid, Callback callback) {
        getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new LoadPlayerThenCallback(getPlugin(), uuid, callback));
    }


    public void displayStaffChatMessage(UUID sender, String server, String message) {
        if (getStorageController().isLoaded(sender)) {
            provider.displayStaffChatMessage(getStorageController().getCachedUser(sender), getPlugin().getProxy().getServerInfo(server), message);
        } else {
            loadCachedUserThenCallback(sender, new DisplayStaffChatMessage(getPlugin(), sender, server, message));
        }
    }
}
