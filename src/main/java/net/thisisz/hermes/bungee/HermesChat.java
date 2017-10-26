package net.thisisz.hermes.bungee;

import com.imaginarycode.minecraft.redisbungee.RedisBungee;
import com.imaginarycode.minecraft.redisbungee.RedisBungeeAPI;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.thisisz.hermes.bungee.command.Nickname;
import net.thisisz.hermes.bungee.messaging.MessagingController;
import net.thisisz.hermes.bungee.storage.StorageController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

public class HermesChat extends Plugin {

    private Configuration configuration;
    private HermesChatListener listener;
    private LuckPermsApi luckApi;
    private StorageController storageController;
    private MessagingController messagingController;
    private RedisBungeeAPI redisBungeeAPI;

    @Override
    public void onEnable() {
        // You should not put an enable message in your plugin.
        // BungeeCord already does so

        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yaml");

        getLogger().info("Checking for config file.");
        if (!file.exists()) {
            getLogger().info("Creating default config file.");
            try (InputStream in = getResourceAsStream("config.yaml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        getLogger().info("Loading config.");
        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yaml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        getLogger().info("Loading user data storage system.");
        this.storageController = new StorageController(this);

        Optional<LuckPermsApi> api = LuckPerms.getApiSafe();
        if (api.isPresent()) {
            this.luckApi = api.get();
            getLogger().info("Luck perms api loaded.");
        } else {
            getLogger().warning("hermes chat failed to load Luck Perms api.");
        }

        if (getProxy().getPluginManager().getPlugin("RedisBungee") != null) {
            redisBungeeAPI = RedisBungee.getApi();
        }

        getLogger().info("Setting up messaging system");
        this.messagingController = new MessagingController(this);

        getLogger().info("Setting up chat listener.");
        this.listener = new HermesChatListener(this);

        getProxy().getPluginManager().registerListener(this, listener);

        getLogger().info("Registering commands.");
        getProxy().getPluginManager().registerCommand(this, new Nickname(this));

        getLogger().info("Ae chat loaded successfully!");
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public HermesChatListener getListener() {
        return listener;
    }

    public MessagingController getMessagingController() { return messagingController; }

    public LuckPermsApi getLuckApi() {
        return luckApi;
    }

    public boolean DebugMode() {
        return configuration.getBoolean("debug");
    }

    public StorageController getStorageController() {
        return storageController;
    }

    public RedisBungeeAPI getRedisBungeeAPI() { return redisBungeeAPI; }

}
