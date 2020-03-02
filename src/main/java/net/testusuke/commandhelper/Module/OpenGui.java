package net.testusuke.commandhelper.Module;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.sql.ResultSet;

public class OpenGui {

    private final CommandHelper plugin;

    public OpenGui(CommandHelper plugin){
        this.plugin = plugin;
    }

    public void OpenGui(Player player){

        String uuid = player.getUniqueId().toString();

        Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54,plugin.prefix + "§e§lコマンド一覧");

        //  コマンドリスト取得
        String query = "SELECT command FROM cmdhelper_list WHERE uuid = '" + uuid + "';";
        ResultSet rs = plugin.mysql.query(query);

    }

}
