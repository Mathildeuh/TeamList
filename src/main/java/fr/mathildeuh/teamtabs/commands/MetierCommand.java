package fr.mathildeuh.teamtabs.commands;

import fr.mathildeuh.teamtabs.TeamList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MetierCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigurationSection metiers = TeamList.getPlugin(TeamList.class).getConfig().getConfigurationSection("metiers");
        if (metiers == null) return true;

        if (args.length == 0) {
            // No arguments provided, list all jobs and their members
            for (String metier : metiers.getKeys(false)) {
                List<String> members = metiers.getStringList(metier);
                List<String> coloredMembers = new ArrayList<>();
                for (String member : members) {
                    if (Bukkit.getPlayerExact(member) != null) {
                        // Player is online
                        coloredMembers.add("§a" + member);
                    } else {
                        // Player is offline
                        coloredMembers.add("§c" + member);
                    }
                }
                sender.sendMessage("§6" + ChatColor.translateAlternateColorCodes('&', metier) + ":");
                sender.sendMessage(String.join(", ", coloredMembers));
            }
            sender.sendMessage("§c\uD83D\uDFE2 = Déconnecté, §a\uD83D\uDFE2 = Connecté");
        } else {
            // Argument provided, find all jobs for the specified player
            String playerName = args[0];
            List<String> playerMetiers = new ArrayList<>();
            for (String metier : metiers.getKeys(false)) {
                List<String> members = metiers.getStringList(metier);
                if (members.contains(playerName)) {
                    playerMetiers.add(metier);
                }
            }
            if (playerMetiers.isEmpty()) {
                sender.sendMessage("§cAucun métier trouvé pour " + playerName);
            } else {
                boolean isOnline = Bukkit.getPlayer(playerName) != null;
                String prefix =  isOnline ? "§a" : "§c";
                sender.sendMessage("§6Metiers de " + prefix + playerName + ":");
                sender.sendMessage(String.join(", ",  "§6" + playerMetiers));
            }
        }


        return true;
    }
}
