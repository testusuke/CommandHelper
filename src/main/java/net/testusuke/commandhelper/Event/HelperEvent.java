package net.testusuke.commandhelper.Event;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HelperEvent implements Listener {

    private final CommandHelper plugin;

    public HelperEvent(CommandHelper plugin) {
        this.plugin = plugin;
    }

    //////////////
    //  Event   //
    //////////////

    //  onClickEvent
    @EventHandler
    public void onClick(InventoryClickEvent event){


    }


}
