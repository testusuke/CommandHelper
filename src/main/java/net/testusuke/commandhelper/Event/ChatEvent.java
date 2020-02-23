package net.testusuke.commandhelper.Event;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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

        if(!plugin.prepareCommandPlayer.containsKey(player)){
            return;
        }

        String mode = plugin.cl.getPlayerMode(player);
        if(mode == "no"){return;}

        if(mode == "add"){


            plugin.cl.removeMode(player);
        }

        if(mode == "remove"){


            plugin.cl.removeMode(player);
        }

    }
}
