package me.jumper251.replay.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent extends AbstractListener {


    @EventHandler
    public void onDeatEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.hasPermission("group.vagt") && !player.isOp()) {

            if(DamageEvent.recordingPlayers.containsKey(player)) {
                //Bukkit.broadcastMessage("§8§l[ §6§l➲ §8§l] §fEn vagt blev dræbt og recording er nu sat til true!");
                DamageEvent.recordingPlayers.put(player, true);
            }
        }
    }
}
