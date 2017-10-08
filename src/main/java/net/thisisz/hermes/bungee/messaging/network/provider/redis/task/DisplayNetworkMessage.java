package net.thisisz.hermes.bungee.messaging.network.provider.redis.task;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.network.provider.RedisBungeeProvider;
import net.thisisz.hermes.bungee.messaging.network.provider.redis.object.NetworkMessage;

import java.util.UUID;

public class DisplayNetworkMessage implements Runnable {

    private NetworkMessage message;
    private RedisBungeeProvider provider;

    public DisplayNetworkMessage(NetworkMessage message, RedisBungeeProvider provider) {
        this.message = message;
        this.provider = provider;
    }

    public HermesChat getPlugin() {
        return provider.getPlugin();
    }

    @Override
    public void run() {
        provider.getNetworkController().displayMessageLocal(UUID.fromString(message.getSender()), message.getServer(), message.getMessage());
    }

}
