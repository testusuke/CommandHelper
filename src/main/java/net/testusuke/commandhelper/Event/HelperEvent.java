package net.testusuke.commandhelper.Event;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if(event.getView().getTitle().equalsIgnoreCase(plugin.prefix + "§e§lコマンド一覧")){
            event.setCancelled(true);

            //  アイテムが緑色の羊毛か
            if(item.getType() == Material.GREEN_WOOL){
                try {
                    String command = item.getItemMeta().getDisplayName();
                    player.sendMessage(plugin.prefix + "§aコマンドを実行します。command: " + command);
                    //  コマンドの実行
                    plugin.cl.existCommand(player, command);

                }catch (NullPointerException e){
                    //e.printStackTrace();
                    player.sendMessage(plugin.prefix + "§c§l無効な選択です。Error: NullPointerException Where: ClickEvent");
                }

            }

            //

        }

    }


    private void executeCommand(Player player, String command){

        //  execute
        Bukkit.dispatchCommand(player, command);
    }
}
