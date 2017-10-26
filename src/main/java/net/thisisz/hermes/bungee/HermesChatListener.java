package net.thisisz.hermes.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.event.EventHandler;
import net.thisisz.hermes.bungee.messaging.local.provider.LocalProvider;
import net.thisisz.hermes.bungee.messaging.asynctasks.SendChatMessage;

public class HermesChatListener implements net.md_5.bungee.api.plugin.Listener {

    private HermesChat plugin;

    public HermesChatListener(HermesChat parent) {
        this.plugin = parent;
    }

    public HermesChat getPlugin() {
        return this.plugin;
    }

    @EventHandler
    public void onChatEvent(ChatEvent event) {
        if (!event.isCommand() && event.getReceiver() instanceof Server && event.getSender() instanceof ProxiedPlayer) {
            event.setCancelled(true);
            getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new SendChatMessage(getPlugin(), (ProxiedPlayer) event.getSender(), (Server) event.getReceiver(), event.getMessage()));
        }
    }

}
