package net.thisisz.hermes.bungee.messaging.local.provider;

import net.md_5.bungee.api.config.ServerInfo;

import java.util.UUID;

//Providers for communication on single bungee proxy level. Used by MessagingController as an interface for sending network wide messages to each local proxy or to only the local proxy with the LocalOnlyProvider network messaging provider.
public interface LocalProvider {

    void displayNewChatMessage(UUID player, ServerInfo server, String message);

    void displayUserErrorMessage(UUID to, String message);

    void displayUserNotification(UUID to, String message);
}
