package net.thisisz.hermes.bungee.messaging.network.provider;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.event.EventHandler;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.asynctask.LoadPlayerThenCallback;
import net.thisisz.hermes.bungee.asynctask.SendLogoutNotification;
import net.thisisz.hermes.bungee.callback.PlayerLoginNotification;
import net.thisisz.hermes.bungee.messaging.network.NetworkMessagingController;
import net.thisisz.hermes.bungee.messaging.network.provider.asynctask.common.DisplayLoginNotification;
import net.thisisz.hermes.bungee.messaging.network.provider.asynctask.common.DisplayLogoutNotification;

import java.util.UUID;

//Network messaging provider that only uses a local provider of choice.
public class LocalOnlyProvider implements NetworkProvider, net.md_5.bungee.api.plugin.Listener {

    private NetworkMessagingController networkController;

    public LocalOnlyProvider(NetworkMessagingController parent) {
        this.networkController = parent;
        getPlugin().getProxy().getPluginManager().registerListener(getPlugin(), this);
    }

    public HermesChat getPlugin() {
        return networkController.getPlugin();
    }

    @Override
    public void sendChatMessage(UUID sender, String server, String message) {
        networkController.displayChatMessage(sender, server, message);
    }

    @Override
    public void sendNewUserNotification(UUID to, String message) {
        networkController.displayUserNotification(to, message);
    }

    @Override
    public void sendNewUserErrorMessage(UUID to, String message) {
        networkController.displayUserErrorMessage(to, message);
    }

    @Override
    public NetworkMessagingController getNetworkController() {
        return networkController;
    }

    @EventHandler
    public void onPlayerDisconnectEvent(PlayerDisconnectEvent event) {
        getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new DisplayLogoutNotification(this, event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onPostLoginEvent(PostLoginEvent event) {
        getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new LoadPlayerThenCallback(getPlugin(), event.getPlayer().getUniqueId(), new DisplayLoginNotification(this, event.getPlayer().getUniqueId())));
    }

}
