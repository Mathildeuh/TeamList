package fr.mathildeuh.teamtabs;

import fr.mathildeuh.teamtabs.commands.MetierCommand;
import fr.mathildeuh.teamtabs.commands.MetierReloadCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class TeamList extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getCommand("metier").setExecutor(new MetierCommand());
        getCommand("metierreloadconfig").setExecutor(new MetierReloadCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void reloadTabs(@Nullable Player player){
        if (player == null) {
            Bukkit.getOnlinePlayers().forEach(TeamList::updatePlayerListName);
        } else {
            updatePlayerListName(player);
        }
    }

    private static void updatePlayerListName(Player player) {
        String playerName = player.getName();
        org.bukkit.scoreboard.Team team = Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(playerName);
        if (team != null) {
            // If the player is in a team, prepend the team name to the player's name
            player.setPlayerListName(team.getPrefix() + playerName + team.getSuffix());
        } else {
            // Handle players not in any team, if necessary
            player.setPlayerListName(playerName);
        }
    }
}
