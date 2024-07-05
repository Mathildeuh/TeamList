package fr.mathildeuh.teamtabs.commands;

import fr.mathildeuh.teamtabs.TeamList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MetierReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        JavaPlugin.getPlugin(TeamList.class).reloadConfig();
        sender.sendMessage("§cPlugin rechargé");
        return false;
    }
}
