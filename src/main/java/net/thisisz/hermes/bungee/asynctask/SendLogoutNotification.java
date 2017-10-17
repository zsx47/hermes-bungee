package net.thisisz.hermes.bungee.asynctask;

import net.thisisz.hermes.bungee.HermesChat;

import java.util.UUID;

public class SendLogoutNotification extends AsyncTask {

    private UUID uuid;

    public SendLogoutNotification(HermesChat plugin, UUID uuid) {
        super(plugin);
        this.uuid = uuid;
    }

    @Override
    public void run() {
        getPlugin().getMessagingController().displayLoginNotification(uuid);
    }

}
