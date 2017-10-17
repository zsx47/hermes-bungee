package net.thisisz.hermes.bungee.callback;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.storage.CachedUser;

import java.util.UUID;

public class PlayerLoginNotification implements Callback {

    private HermesChat plugin;
    private UUID uuid;
    private CachedUser user;

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
