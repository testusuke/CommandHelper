package net.testusuke.commandhelper.Event;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChatEvent implements Listener {

    public CommandHelper plugin;

    public ChatEvent(CommandHelper plugin) {
        this.plugin = plugin;
    }

    //////////////
    //  Event   //
    //////////////

    //  onChat
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String command = event.getMessage();

        if(!plugin.addCommandPlayer.containsKey(player)){
            return;
        }

        boolean mode = plugin.addCommandPlayer.get(player);
        if(mode){
            //  追加
            new BukkitRunnable(){
                @Override
                public void run(){
                    plugin.commandListData.addCommand(player,command);
                }
            }.runTaskAsynchronously(plugin);
            //plugin.commandListData.addCommand(player,command);
            plugin.cl.removeMode(player);

            event.setCancelled(true);
            return;
        }
    }
}
