package net.thisisz.hermes.bungee.messaging.network.provider.asynctask.redis;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.network.provider.RedisBungeeProvider;
import net.thisisz.hermes.bungee.messaging.network.provider.object.NetworkNotification;

import java.util.UUID;

public class DisplayNetworkNotification implements Runnable {

    private NetworkNotification message;
    private RedisBungeeProvider provider;

    public DisplayNetworkNotification(NetworkNotification message, RedisBungeeProvider provider) {
        this.message = message;
        this.provider = provider;
    }

    public HermesChat getPlugin() {
        return provider.getPlugin();
    }

    @Override
    public void run() {
        if (getPlugin().getProxy().getPlayer(UUID.fromString(message.getReceiver())) != null) {
            provider.getNetworkController().displayUserNotification(UUID.fromString(message.getReceiver()), message.getMessage());
        }
    }

}
