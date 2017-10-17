package net.thisisz.hermes.bungee.asynctask;

import net.thisisz.hermes.bungee.callback.Callback;
import net.thisisz.hermes.bungee.HermesChat;

import java.util.UUID;

public class LoadPlayerThenCallback extends AsyncTask {

    private UUID uuid;
    private Callback callback;

    public LoadPlayerThenCallback(HermesChat plugin, UUID uuid, Callback callback) {
        super(plugin);
        this.uuid = uuid;
        this.callback = callback;
    }

    @Override
    public void run() {
        //load player in this thread
        getPlugin().getStorageController().loadLocalUserToCache(uuid);
        //run callback
        callback.run();
    }


}
