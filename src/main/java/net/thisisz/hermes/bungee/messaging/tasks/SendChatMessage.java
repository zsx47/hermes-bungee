package net.thisisz.hermes.bungee.messaging.tasks;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.thisisz.hermes.bungee.HermesChat;

public class SendChatMessage extends MessagingTask {

    private ProxiedPlayer sender;
    private Server server;
    private String message;

    public SendChatMessage(HermesChat plugin, ProxiedPlayer sender, Server server, String message) {
        super(plugin);
        this.sender = sender;
        this.server = server;
        this.message = message;
    }

    @Override
    public void run() {
        getMessagingController().sendNewNetworkChatMessage(this.sender, this.server, this.message);
    }
}
