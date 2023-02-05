package me.imsergioh.liveannouncer.commands;

import me.imsergioh.liveannouncer.LiveAnnouncer;
import me.imsergioh.liveannouncer.util.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.awt.*;

public class liveCMD extends Command {

    public liveCMD(String name, String... aliases) {
        super(name, null, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String permission = LiveAnnouncer.getPlugin().getMainConfig().getString("permissionNode");
        if (!sender.hasPermission(permission)) {
            sendMessage(sender, "permissionNodeMessage");
            return;
        }

        if (args.length != 1) {
            sendMessage(sender, "correctUsageMessage");
            return;
        }

        announce(sender, args[0]);
    }

    private void announce(CommandSender sender, String link) {
        boolean isPermittedLink = false;

        for (String configString : LiveAnnouncer.getPlugin().getMainConfig().getStringList("permittedLinks")) {
            if (link.contains(configString)) {
                isPermittedLink = true;
                break;
            }
        }

        if (!isPermittedLink) {
            sendMessage(sender, "notPermittedLinkMessage");
            return;
        }

        LiveAnnouncer.getPlugin().getProxy().broadcast(broadcastMessage(sender.getName(), link));
    }

    private TextComponent broadcastMessage(String senderName, String link) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : LiveAnnouncer.getPlugin().getMainConfig().getStringList("announceMessage")) {
            stringBuilder.append(ChatUtil.chatColor(line).replace("%player%", senderName).replace("%link%", link));
            stringBuilder.append("\n");
        }
        net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent(stringBuilder.toString());
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        String hoverMessage = LiveAnnouncer.getPlugin().getMainConfig().getString("hoverMessage");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMessage).create()));
        return message;
    }

    private void sendMessage(CommandSender sender, String configPath) {
        String message = LiveAnnouncer.getPlugin().getMainConfig().getString(configPath);
        sender.sendMessage(ChatUtil.chatColor(message));
    }
}
