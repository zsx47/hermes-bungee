package net.thisisz.hermes.bungee.storage;

import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.caching.UserData;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.storage.exception.controller.GenericControllerException;
import net.thisisz.hermes.bungee.storage.tasks.LoadPrefix;

import java.util.UUID;

public class CachedUser {

    private final UUID uuid;
    private final StorageController controller;
    private String nickname;
    private String name;
    private UserData userData;
    private boolean vanished = false;

    public CachedUser(UUID uuid, StorageController controller) {
        this.controller = controller;
        this.uuid = uuid;
        try {
            this.controller.loadNickname(this);
        } catch (Exception e) {
            getPlugin().getLogger().warning("Failed to get nickname for uuid: " + getUUID().toString());
        }
        updateUserData();
        if (isLocal()) {
            this.name = getPlugin().getProxy().getPlayer(this.uuid).getName();
        } else {
            if (getPlugin().getRedisBungeeAPI() != null) {
                this.name = getPlugin().getRedisBungeeAPI().getNameFromUuid(this.uuid);
            }
        }
    }

    public CachedUser(UUID uuid, StorageController controller, String name) {
        this.controller = controller;
        this.uuid = uuid;
        try {
            this.controller.loadNickname(this);
        } catch (Exception e) {
            getPlugin().getLogger().warning("Failed to get nickname for uuid: " + getUUID().toString());
        }
        updateUserDataInThread();
        this.name = name;
    }

    private HermesChat getPlugin() {
        return HermesChat.getPlugin();
    }

    public String getNickname() {
        return nickname;
    }

    public String getDisplayName() {
        if (nickname == null) {
            if (name == null) {
                return "NOPE!";
            } else {
                return name;
            }
        }
        return nickname;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public boolean isLocal() {
        return getPlugin().getProxy().getPlayer(this.uuid) != null;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        if (isLocal()) {
            if (nickname != null) {
                getPlugin().getProxy().getPlayer(this.uuid).setDisplayName(this.nickname);
            } else {
                getPlugin().getProxy().getPlayer(this.uuid).setDisplayName(getPlugin().getProxy().getPlayer(uuid).getName());
            }
        }
        getPlugin().getMessagingController().sendNicknameUpdate(uuid, nickname);
        try {
            this.controller.saveNicknameAsync(this);
        } catch (GenericControllerException e) {
            getPlugin().getLogger().warning("Failed to save nickname for user with uuid '" + uuid.toString() + "'!" );
        }
    }

    public void setNicknameNoSave(String nickname) {
        this.nickname = nickname;
        if (isLocal()) {
            if (nickname != null) {
                getPlugin().getProxy().getPlayer(this.uuid).setDisplayName(this.nickname);
            } else {
                getPlugin().getProxy().getPlayer(this.uuid).setDisplayName(getPlugin().getProxy().getPlayer(uuid).getName());
            }
        }
    }

    public void update() {
        try {
            this.controller.loadNickname(this);
        } catch (Exception e) {
            getPlugin().getLogger().warning("Failed to update update nickname for uuid: " + getUUID().toString());
        }
    }

    public String getPrefix(){
        if (userData != null) {
            String prefix = userData.calculateMeta(Contexts.global()).getPrefix();
            if (prefix == null) {
                return "";
            }
            return prefix;
        } else {
            return "";
        }
    }

    public String getName() {
        if (name == null) {
            return "still loading for some reason";
        }
        return name;
    }

    public void setVanished() {
        vanished = true;
    }

    public void setUnvanishd() {
        vanished = false;
    }

    public void updateUserData() {
        getPlugin().getProxy().getScheduler().runAsync(getPlugin(), new LoadPrefix(this));
    }

    private void updateUserDataInThread() {
        Runnable loadPrefix = new LoadPrefix(this);
        loadPrefix.run();
    }

    public void setUserData(UserData udat) {
        this.userData = udat;
    }

}
