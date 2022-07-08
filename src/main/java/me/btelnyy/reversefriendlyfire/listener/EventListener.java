package me.btelnyy.reversefriendlyfire.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.btelnyy.reversefriendlyfire.ReverseFriendlyFire;
import me.btelnyy.reversefriendlyfire.constants.ConfigData;
import me.btelnyy.reversefriendlyfire.service.Utils;
import me.btelnyy.reversefriendlyfire.service.file_manager.Configuration;
import me.btelnyy.reversefriendlyfire.service.file_manager.FileID;

public class EventListener implements Listener {
    private static final Configuration language = ReverseFriendlyFire.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!ConfigData.getInstance().pluginEnabled){
            return;
        }
        //if the damaged OR the damager are NOT players, return
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)){
            return;
        }
        Player victim = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();
        if(attacker.hasPermission(ConfigData.getInstance().overridePermission) || attacker.isOp()){
            return;
        }
        Team victimteam = null;
        Team attackerteam = null;
        ScoreboardManager sman = Bukkit.getServer().getScoreboardManager();
        for(Team t : sman.getMainScoreboard().getTeams()){
            if(t.getEntries().contains(victim.getName())){
                victimteam = t;
            }
            if(t.getEntries().contains(attacker.getName())){
                attackerteam = t;
            }
        }
        if(victimteam == null || attackerteam == null){
            return;
        }
        if(victimteam == attackerteam){
            event.getDamager().setLastDamageCause(event);
            attacker.damage(event.getFinalDamage());
            attacker.sendMessage(Utils.colored(language.getString("damage_friendly")));
        }
    }
}
