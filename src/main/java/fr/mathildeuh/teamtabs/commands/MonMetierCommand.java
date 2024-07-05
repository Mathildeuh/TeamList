package fr.mathildeuh.teamtabs.commands;

import fr.mathildeuh.teamtabs.TeamList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public class MonMetierCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length != 0) {
            return false;
        }
        ConfigurationSection metiers = TeamList.getPlugin(TeamList.class).getConfig().getConfigurationSection("metiers");
        for (String metiera : metiers.getKeys(false)) {
            List<String> members = metiers.getStringList(metiera);
            if (members.contains(player.getName())){
                player.sendMessage("§6" + metiera + ": §b" + TeamList.descriptionsMap.get(metiera));
            }
        }

        return true;
    }
}
