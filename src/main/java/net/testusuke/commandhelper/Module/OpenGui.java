package net.testusuke.commandhelper.Module;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpenGui {

    private final CommandHelper plugin;

    public OpenGui(CommandHelper plugin){
        this.plugin = plugin;
    }

    public void OpenGui(Player player, int page){

        new BukkitRunnable(){
            @Override
            public void run(){
                String uuid = player.getUniqueId().toString();
                Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54,plugin.prefix + "§e§lコマンド一覧");

                //  コマンドリスト取得
                //  一次関数 y=45x-45
                int index = (45 * page) -45;
                String query = "select command from cmdhelper_list where uuid='" + uuid + "' order by id limit 45 offset " + index + ";";
                ResultSet rs = plugin.mysql.query(query);

                try {

                    if(rs == null){
                        player.sendMessage(plugin.prefix + "§cコマンドリストが存在しませんでした。§a/cmdhelp add でコマンドを追加しましょう。");
                        rs.close();
                    }

                    int count = 0;
                    while (rs.next()) {
                        String command = rs.getString("command");
                        int id = rs.getInt("id");
                        ItemStack item = createItemAboutAdd(command,id);
                        inv.setItem(count,item);
                        count++;
                    }

                    //  ページ切り替え、ページ数のアイテムの設置
                    //  次のページ
                    ItemStack item_nextPage = new ItemStack(Material.SUNFLOWER);
                    ItemMeta meta_nextPage = item_nextPage.getItemMeta();
                    meta_nextPage.setDisplayName("§e次のページ");
                    item_nextPage.setItemMeta(meta_nextPage);
                    inv.setItem(53,item_nextPage);
                    //  前のページ
                    ItemStack item_backPage = new ItemStack(Material.SUNFLOWER);
                    ItemMeta meta_backPage = item_backPage.getItemMeta();
                    meta_backPage.setDisplayName("§e前のページ");
                    item_backPage.setItemMeta(meta_backPage);
                    inv.setItem(45,item_backPage);
                    //  ページ数
                    ItemStack item_pageIndex = new ItemStack(Material.BOOK);
                    ItemMeta meta_pageIndex = item_pageIndex.getItemMeta();
                    meta_pageIndex.setDisplayName("§aページ： " + page);
                    item_pageIndex.setItemMeta(meta_pageIndex);
                    inv.setItem(49,item_nextPage);

                    player.openInventory(inv);

                    //  DBの処理
                    rs.close();

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(this.plugin);
    }

    private ItemStack createItemAboutAdd(String command, int id){
        ItemStack item;
        item = new ItemStack(Material.LIME_WOOL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(command);
        ArrayList<String> list = new ArrayList<>();
        list.add(id + "");
        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }

}
