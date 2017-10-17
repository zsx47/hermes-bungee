package net.thisisz.hermes.bungee.messaging.local.callback;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.callback.Callback;

import java.util.UUID;

public class DisplayLoginNotification implements Callback {

    private UUID player;
    private HermesChat plugin;

    public DisplayLoginNotification(HermesChat plugin, UUID player) {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void run() {
        getPlugin().getMessagingController().displayLoginNotification(player);
    }

    @Override
    public HermesChat getPlugin() {
        return plugin;
    }

}
