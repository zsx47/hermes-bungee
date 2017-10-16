package net.thisisz.hermes.bungee.callbacks;

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
        this.user = getPlugin().getStorageController().getCachedUser(uuid);
        if (getPlugin().getRedisBungeeAPI() != null) {
            for (UUID player : getPlugin().getRedisBungeeAPI().getPlayersOnline()) {
                getPlugin().getMessagingController().sendNewNotification(player, "&ePlayer " + user.getDisplayName() + " &ehas joined the network.");
            }
        } else {
            for (ProxiedPlayer player : getPlugin().getProxy().getPlayers()) {
                getPlugin().getMessagingController().sendNewNotification(player.getUniqueId(), "&ePlayer " + user.getDisplayName() + " &ehas joined the network.");
            }
        }
    }


}
