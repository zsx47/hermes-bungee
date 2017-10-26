package net.thisisz.hermes.bungee.messaging.network.provider.asynctask.redis;

import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.messaging.network.provider.RedisBungeeProvider;
import net.thisisz.hermes.bungee.messaging.network.provider.object.common.NetworkNicknameUpdate;

public class RemoteUpdateNickname implements Runnable {


    private final RedisBungeeProvider provider;
    private final NetworkNicknameUpdate nicknameUpdate;

    public RemoteUpdateNickname(NetworkNicknameUpdate nicknameUpdate, RedisBungeeProvider redisBungeeProvider) {
        this.nicknameUpdate = nicknameUpdate;
        this.provider = redisBungeeProvider;
    }

    public HermesChat getPlugin() {
        return provider.getPlugin();
    }


    @Override
    public void run() {
        if (getPlugin().getStorageController().isLoaded(nicknameUpdate.getUUID())) {
            getPlugin().getStorageController().getCachedUser(nicknameUpdate.getUUID()).setNicknameNoSave(nicknameUpdate.getNewNickname());
        }
    }

}
