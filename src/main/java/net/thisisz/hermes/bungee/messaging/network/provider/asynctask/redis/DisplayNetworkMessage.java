package net.thisisz.hermes.bungee.messaging.network.provider.asynctask.redis;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.network.provider.RedisBungeeProvider;
import net.thisisz.hermes.bungee.messaging.network.provider.object.common.NetworkMessage;

import java.util.UUID;

public class DisplayNetworkMessage implements Runnable {

    private NetworkMessage message;
    private RedisBungeeProvider provider;
    private boolean staff;

    public DisplayNetworkMessage(NetworkMessage message, RedisBungeeProvider provider, boolean staff) {
        this.message = message;
        this.provider = provider;
        this.staff = staff;
    }

    public HermesChat getPlugin() {
        return provider.getPlugin();
    }

    @Override
    public void run() {
        if (staff) {
            provider.getNetworkController().displayStaffChatMessage(UUID.fromString(message.getSender()), message.getServer(), message.getMessage());
        } else {
            provider.getNetworkController().displayChatMessage(UUID.fromString(message.getSender()), message.getServer(), message.getMessage());
        }
    }

}
