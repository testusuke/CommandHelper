package net.testusuke.commandhelper.Module;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ExecuteCommand {

    private final CommandHelper plugin;

    public ExecuteCommand(CommandHelper plugin){
        this.plugin = plugin;
    }

    public void executeCommand(Player player,String command){

        //  execute
        Bukkit.dispatchCommand(player, command);
    }

}
