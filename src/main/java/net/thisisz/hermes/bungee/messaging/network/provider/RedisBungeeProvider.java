package net.thisisz.hermes.bungee.messaging.network.provider;

import com.google.gson.Gson;
import com.imaginarycode.minecraft.redisbungee.events.PlayerJoinedNetworkEvent;
import com.imaginarycode.minecraft.redisbungee.events.PlayerLeftNetworkEvent;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.event.EventHandler;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.asynctask.LoadPlayerThenCallback;
import net.thisisz.hermes.bungee.messaging.network.NetworkMessagingController;
import net.thisisz.hermes.bungee.messaging.network.provider.asynctask.common.DisplayLoginNotification;
import net.thisisz.hermes.bungee.messaging.network.provider.asynctask.common.DisplayLogoutNotification;
import net.thisisz.hermes.bungee.messaging.network.provider.asynctask.redis.DisplayNetworkErrorMessage;
import net.thisisz.hermes.bungee.messaging.network.provider.asynctask.redis.DisplayNetworkMessage;
import net.thisisz.hermes.bungee.messaging.network.provider.asynctask.redis.DisplayNetworkNotification;
import net.thisisz.hermes.bungee.messaging.network.provider.asynctask.redis.RemoteUpdateNickname;
import net.thisisz.hermes.bungee.messaging.network.provider.object.common.NetworkErrorMessage;
import net.thisisz.hermes.bungee.messaging.network.provider.object.common.NetworkMessage;
import net.thisisz.hermes.bungee.messaging.network.provider.object.common.NetworkNicknameUpdate;
import net.thisisz.hermes.bungee.messaging.network.provider.object.common.NetworkNotification;

import java.util.UUID;

public class RedisBungeeProvider implements NetworkProvider, net.md_5.bungee.api.plugin.Listener {

    private NetworkMessagingController networkController;

    public RedisBungeeProvider(NetworkMessagingController parent) {
        this.networkController = parent;
        getPlugin().getRedisBungeeAPI().registerPubSubChannels("network-chat-messages");
        getPlugin().getRedisBungeeAPI().registerPubSubChannels("network-notification");
        getPlugin().getRedisBungeeAPI().registerPubSubChannels("network-error-message");
        getPlugin().getRedisBungeeAPI().registerPubSubChannels("network-nickname-updates");
        getPlugin().getProxy().getPluginManager().registerListener(getPlugin(), this);
    }

    public HermesChat getPlugin() {
        return networkController.getPlugin();
    }

    @Override
    public void sendNicknameUpdate(UUID uuid, String nickname) {
        NetworkNicknameUpdate obj = new NetworkNicknameUpdate(uuid, nickname);
        Gson gson = new Gson();
        getPlugin().getRedisBungeeAPI().sendChannelMessage("network-nickname-updates", gson.toJson(obj));
    }

    public NetworkMessagingController getNetworkController() {
        return networkController;
    }

    @Override
    public void sendChatMessage(UUID sender, String server, String message) {
        NetworkMessage obj = new NetworkMessage(sender, server, message);
        Gson gson = new Gson();
        getPlugin().getRedisBungeeAPI().sendChannelMessage("network-chat-messages", gson.toJson(obj));
    }

    @Override
    public void sendNewUserNotification(UUID to, String message) {
        NetworkNotification obj = new NetworkNotification(to, message);
        Gson gson = new Gson();
        getPlugin().getRedisBungeeAPI().sendChannelMessage("network-notification", gson.toJson(obj));
    }

    @Override
    public void sendNewUserErrorMessage(UUID to, String message) {
        NetworkNotification obj = new NetworkNotification(to, message);
        Gson gson = new Gson();
        getPlugin().getRedisBungeeAPI().sendChannelMessage("network-error-notification", gson.toJson(obj));
    }

    @EventHandler
    public void onPlayerJoinedNetworkEvent(PlayerJoinedNetworkEvent event) {
        displayPlayerLogin(event.getUuid());
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        displayPlayerLogin(event.getPlayer().getUniqueId());
    }

    private void displayPlayerLogin(UUID player) {
        getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new LoadPlayerThenCallback(getPlugin(), player, new DisplayLoginNotification(this, player)));
    }

    @EventHandler
    public void onPlayerLeftNetworkEvent(PlayerLeftNetworkEvent event) {
        displayPlayerLogout(event.getUuid());
    }

    @EventHandler
    public void onPlayerLogout(PlayerDisconnectEvent event) {
        displayPlayerLogout(event.getPlayer().getUniqueId());
    }

    private void displayPlayerLogout(UUID player) {
        getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new DisplayLogoutNotification(this, player));
    }

    @EventHandler
    public void onPubSubMessageEvent(PubSubMessageEvent event) {
        Gson gson = new Gson();
        switch (event.getChannel()) {
            case "network-chat-messages":
                NetworkMessage networkMessage = gson.fromJson(event.getMessage(), NetworkMessage.class);
                getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new DisplayNetworkMessage(networkMessage, this));
                break;
            case "network-notification":
                NetworkNotification networkNotification = gson.fromJson(event.getMessage(), NetworkNotification.class);
                getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new DisplayNetworkNotification(networkNotification, this));
                break;
            case "network-error-notification":
                NetworkErrorMessage networkErrorMessage = gson.fromJson(event.getMessage(), NetworkErrorMessage.class);
                getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new DisplayNetworkErrorMessage(networkErrorMessage, this));
                break;
            case "network-nickname-updates":
                NetworkNicknameUpdate networkNicknameUpdate = gson.fromJson(event.getMessage(), NetworkNicknameUpdate.class);
                getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new RemoteUpdateNickname(networkNicknameUpdate, this));
                break;
            default:
                break;
        }
    }

}
