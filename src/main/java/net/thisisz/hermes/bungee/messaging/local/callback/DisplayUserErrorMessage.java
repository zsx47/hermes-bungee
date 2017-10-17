package net.thisisz.hermes.bungee.messaging.local.callback;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.callback.Callback;

import java.util.UUID;

public class DisplayUserErrorMessage implements Callback {

    private String message;
    private UUID to;
    private HermesChat plugin;

    public DisplayUserErrorMessage(HermesChat plugin, UUID to, String message) {
        this.plugin = plugin;
        this.to = to;
        this.message = message;
    }

    @Override
    public void run() {
        getPlugin().getMessagingController().displayUserErrorMessage(to, message);
    }

    @Override
    public HermesChat getPlugin() {
        return plugin;
    }
}
