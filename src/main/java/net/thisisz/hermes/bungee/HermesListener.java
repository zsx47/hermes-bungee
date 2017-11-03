package net.thisisz.hermes.bungee;

import me.lucko.luckperms.api.event.user.UserDataRecalculateEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.TargetedEvent;
import net.md_5.bungee.event.EventHandler;
import net.thisisz.hermes.bungee.messaging.local.provider.LocalProvider;
import net.thisisz.hermes.bungee.messaging.asynctasks.SendChatMessage;
import net.thisisz.hermes.bungee.storage.CachedUser;

import java.util.Objects;
import java.util.UUID;

public class HermesListener implements net.md_5.bungee.api.plugin.Listener {

    private HermesChat plugin;

    public HermesListener(HermesChat parent) {
        this.plugin = parent;
    }

    public HermesChat getPlugin() {
        return this.plugin;
    }

    @EventHandler
    public void onChatEvent(ChatEvent event) {
        if (!event.isCommand() && event.getReceiver() instanceof Server && event.getSender() instanceof ProxiedPlayer) {
            event.setCancelled(true);
            if (!Objects.equals(event.getMessage(), "Connected with MineChat")) {
                getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new SendChatMessage(getPlugin(), (ProxiedPlayer) event.getSender(), (Server) event.getReceiver(), event.getMessage()));
            }
        }
    }

    @EventHandler
    public void onUserDataRecalculate(UserDataRecalculateEvent event)  {
        UUID uuid = event.getUser().getUuid();
        if (getPlugin().getStorageController().isLoaded(uuid)) {
            CachedUser user = getPlugin().getStorageController().getCachedUser(uuid);
            user.updateUserData();
        }
    }

}
