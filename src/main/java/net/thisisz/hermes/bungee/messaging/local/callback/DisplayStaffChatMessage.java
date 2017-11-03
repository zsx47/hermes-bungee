package net.thisisz.hermes.bungee.messaging.local.callback;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.callback.Callback;

import java.util.UUID;

public class DisplayStaffChatMessage implements Callback {

    private String message, server;
    private UUID sender;
    private HermesChat plugin;

    public DisplayStaffChatMessage(HermesChat plugin, UUID sender, String server, String message) {
        this.plugin = plugin;
        this.sender = sender;
        this.server = server;
        this.message = message;
    }

    @Override
    public void run() {
        getPlugin().getMessagingController().displayStaffChatMessage(sender, server, message);
    }

    @Override
    public HermesChat getPlugin() {
        return plugin;
    }

}
