package net.thisisz.hermes.bungee.messaging.network.provider;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.network.NetworkMessagingController;

import java.util.UUID;

//Network messaging provider that only uses a local provider of choice.
public class LocalOnlyProvider implements NetworkProvider {

    private NetworkMessagingController networkController;

    public LocalOnlyProvider(NetworkMessagingController parent) {
        this.networkController = parent;
    }

    public HermesChat getPlugin() {
        return networkController.getPlugin();
    }

    @Override
    public void sendNewNetworkChatMessage(UUID sender, String server, String message) {
        networkController.displayMessageLocal(sender, server, message);
    }

    @Override
    public void sendNewUserNotification(UUID to, String message) {
        networkController.displayUserNotification(to, message);
    }

    @Override
    public void sendNewUserErrorMessage(UUID to, String message) {
        networkController.displayUserErrorMessage(to, message);
    }

}
