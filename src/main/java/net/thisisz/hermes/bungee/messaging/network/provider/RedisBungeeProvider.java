package net.thisisz.hermes.bungee.messaging.network.provider;

import com.google.gson.Gson;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;
import net.md_5.bungee.event.EventHandler;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.network.NetworkMessagingController;
import net.thisisz.hermes.bungee.messaging.network.provider.redis.object.NetworkMessage;
import net.thisisz.hermes.bungee.messaging.network.provider.redis.task.DisplayNetworkMessage;

import java.util.Objects;
import java.util.UUID;

public class RedisBungeeProvider implements NetworkProvider, net.md_5.bungee.api.plugin.Listener {

    private NetworkMessagingController networkController;

    public RedisBungeeProvider(NetworkMessagingController parent) {
        this.networkController = parent;
        getPlugin().getRedisBungeeAPI().registerPubSubChannels("network-chat-messages");
        getPlugin().getProxy().getPluginManager().registerListener(getPlugin(), this);
    }

    public HermesChat getPlugin() {
        return networkController.getPlugin();
    }

    public NetworkMessagingController getNetworkController() {
        return networkController;
    }

    @Override
    public void sendNewNetworkChatMessage(UUID sender, String server, String message) {
        NetworkMessage obj = new NetworkMessage(sender, server, message);
        Gson gson = new Gson();
        getPlugin().getRedisBungeeAPI().sendChannelMessage("network-chat-messages", gson.toJson(obj));
    }

    @Override
    public void sendNewUserNotification(UUID to, String message) {

    }

    @Override
    public void sendNewUserErrorMessage(UUID to, String message) {

    }

    @EventHandler
    public void onPubSubMessageEvent(PubSubMessageEvent event) {
        if (Objects.equals(event.getChannel(), "network-chat-messages")) {
            Gson gson = new Gson();
            NetworkMessage message = gson.fromJson(event.getMessage(), NetworkMessage.class);
            getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new DisplayNetworkMessage(message, this));
        }
    }

}
