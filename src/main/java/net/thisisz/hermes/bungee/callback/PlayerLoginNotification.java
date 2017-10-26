package net.thisisz.hermes.bungee.callback;

import net.thisisz.hermes.bungee.HermesChat;

import java.util.UUID;

public class PlayerLoginNotification implements Callback {

    private HermesChat plugin;
    private UUID uuid;

    public PlayerLoginNotification(HermesChat plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
    }

    @Override
    public HermesChat getPlugin() {
        return plugin;
    }

    @Override
    public void run() {
        getPlugin().getMessagingController().displayLoginNotification(uuid);
    }


}
