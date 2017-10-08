package net.thisisz.hermes.bungee.messaging.network.provider;

import java.util.UUID;

//Providers for network wide communication.
public interface NetworkProvider {

    void sendNewNetworkChatMessage(UUID sender, String server, String message);

    void sendNewUserNotification(UUID to, String message);

    void sendNewUserErrorMessage(UUID to, String message);

}
