package net.thisisz.hermes.bungee.messaging.network.provider.asynctask.common;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.callback.Callback;
import net.thisisz.hermes.bungee.messaging.network.provider.NetworkProvider;

import java.util.UUID;

public class DisplayLoginNotification implements Runnable, Callback {

    private NetworkProvider provider;
    private UUID player;

    public DisplayLoginNotification(NetworkProvider provider, UUID player) {
        this.provider = provider;
        this.player = player;
    }

    public HermesChat getPlugin() {
        return provider.getPlugin();
    }

    @Override
    public void run() {
        provider.getNetworkController().displayLoginNotification(player);
    }

}
