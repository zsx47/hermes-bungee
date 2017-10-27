package net.thisisz.hermes.bungee.messaging.local.callback;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.callback.Callback;

import java.util.UUID;

public class DisplayLoginNotification implements Callback {

    private UUID player;
    private HermesChat plugin;
    private boolean vjoin;

    public DisplayLoginNotification(HermesChat plugin, UUID player, boolean vjoin) {
        this.plugin = plugin;
        this.player = player;
        this.vjoin = vjoin;
    }

    @Override
    public void run() {
        getPlugin().getMessagingController().displayLoginNotification(player, vjoin);
    }

    @Override
    public HermesChat getPlugin() {
        return plugin;
    }

}
