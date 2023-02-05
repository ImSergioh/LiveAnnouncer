package me.imsergioh.liveannouncer;

import me.imsergioh.liveannouncer.commands.liveCMD;
import me.imsergioh.liveannouncer.instance.PluginConfig;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public final class LiveAnnouncer extends Plugin {

    private static LiveAnnouncer plugin;

    private PluginConfig mainConfig;

    @Override
    public void onEnable() {
        plugin = this;

        mainConfig = new PluginConfig("config.yml");
        mainConfig.regDefault("permissionNode", "liveannouncer.command");
        mainConfig.regDefault("permissionNodeMessage", "&cYou don't have permissions to execute this command.");
        mainConfig.regDefault("correctUsageMessage", "&cCorrect usage: /live <link>");
        mainConfig.regDefault("notPermittedLinkMessage", "&cThis link is not permitted!");
        mainConfig.regDefault("commandAliases", list("directo", "livestream"));
        mainConfig.regDefault("permittedLinks", list("youtube.com", "twitch.tv"));
        mainConfig.regDefault("announceMessage", list("&8&n----------------", "&f%player% is livestreaming: &d%link%", "&8&n----------------"));
        mainConfig.regDefault("hoverMessage", "Click for open link!");
        mainConfig.save();

        String[] aliases = mainConfig.getStringList("commandAliases").toArray(new String[0]);
        getProxy().getPluginManager().registerCommand(getPlugin(), new liveCMD("live", aliases));
    }

    @Override
    public void onDisable() {

    }

    private List<String> list(String... entries) {
        return Arrays.asList(entries);
    }

    public PluginConfig getMainConfig() {
        return mainConfig;
    }

    public static LiveAnnouncer getPlugin() {
        return plugin;
    }
}
