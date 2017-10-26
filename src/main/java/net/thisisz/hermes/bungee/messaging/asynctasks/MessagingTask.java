package net.thisisz.hermes.bungee.messaging.asynctasks;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.MessagingController;

public class MessagingTask implements Runnable {

    private HermesChat plugin;

    public MessagingTask(HermesChat plugin) {
        this.plugin = plugin;
    }

    public MessagingController getMessagingController() {
        return plugin.getMessagingController();
    }

    public HermesChat getPlugin() {
        return this.plugin;
    }

    @Override
    public void run() {

    }
}
