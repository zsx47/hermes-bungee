package net.thisisz.hermes.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.thisisz.hermes.bungee.HermesChat;
import net.thisisz.hermes.bungee.storage.CachedUser;

public class Nickname extends Command {

    private HermesChat plugin;

    public Nickname(HermesChat parent) {
        super("nickname");
        this.plugin = parent;
    }

    public HermesChat getPlugin() {
        return this.plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        CachedUser user = plugin.getStorageController().getCachedUser((ProxiedPlayer) sender);
        if (args.length == 0) {
            sender.sendMessage(new ComponentBuilder("Your current display name is: " + user.getDisplayName()).create());
        } else if (args.length == 1) {
            //noinspection StringEquality
            if (args[0].toUpperCase() == "OFF") {
                user.setNickname(null);
            } else {
                if (sender.hasPermission("hermes.nickname")) {
                    user.setNickname(args[0]);
                    getPlugin().getMessagingController().sendNewNotification((ProxiedPlayer) sender, "Your display name is now: " + args[0]);
                } else {
                    getPlugin().getMessagingController().sendNewErrorMessage((ProxiedPlayer) sender, "You do not have permission to do this.");
                }
            }
        } else {
            sender.sendMessage(new ComponentBuilder("Usage: /nickname <nickname>").create());
        }
    }


}
