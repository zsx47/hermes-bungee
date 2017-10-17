package net.thisisz.hermes.bungee.asynctask;

import net.thisisz.hermes.bungee.HermesChat;

public class AsyncTask implements Runnable {

    private HermesChat plugin;

    public AsyncTask(HermesChat plugin) {
        this.plugin = plugin;
    }

    public HermesChat getPlugin() {
        return this.plugin;
    }

    @Override
    public void run() {
        return;
    }
}
