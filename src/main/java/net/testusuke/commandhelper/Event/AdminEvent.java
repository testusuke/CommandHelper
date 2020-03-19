package net.testusuke.commandhelper.Event;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AdminEvent implements Listener {

    private final CommandHelper plugin;
    public AdminEvent(CommandHelper plugin) {
        this.plugin = plugin;
    }

    //  onClickEvent
    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();

        if(event.getView().getTitle().equalsIgnoreCase(plugin.prefix + "§e§lコマンド一覧　ADMIN")){
            event.setCancelled(true);
            String target = inventory.getItem(48).getItemMeta().getDisplayName();

            try {
                //  アイテムが黄緑色の羊毛か
                if (item.getType() == Material.LIME_WOOL) {
                    try {
                        String command = item.getItemMeta().getDisplayName();
                        //  コマンドの実行
                        executeCommand(player, command);
                        player.closeInventory();
                    } catch (NullPointerException e) {
                        //e.printStackTrace();
                        player.sendMessage(plugin.prefix + "§c§l無効な選択です。Error: NullPointerException Where: ClickEvent");
                    }

                }
            }catch (NullPointerException e){
                return;
            }

            //  ページ
            if(item.getType() == Material.SUNFLOWER){
                //  つぎのページ
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§e次のページ")){
                    if(inventory.getItem(44) == null){
                        player.sendMessage(plugin.prefix + "§c次のページが存在しません。");
                        return;
                    }
                    player.sendMessage(plugin.prefix + "§a次のページを開きます。");
                    int page = getPage(inventory);
                    page++;
                    plugin.openGui.OpenGuiForAdmin(player, target, page);

                }
                //  前のページ
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§e前のページ")){
                    int page = getPage(inventory);
                    if(page <= 1){
                        return;
                    }
                    page--;
                    plugin.openGui.OpenGuiForAdmin(player, target, page);
                }
            }

        }


        ////////////////////////////////////////////////
        //  コマンド削除用GUI
        ////////////////////////////////////////////////
        if(event.getView().getTitle().equalsIgnoreCase(plugin.prefix + "§c§lコマンド削除用GUI ADMIN")){
            event.setCancelled(true);
            String target = inventory.getItem(48).getItemMeta().getDisplayName();

            try {
                //  アイテムが黄緑色の羊毛か
                if (item.getType() == Material.RED_WOOL) {
                    try {
                        String command = item.getItemMeta().getDisplayName();
                        player.sendMessage(plugin.prefix + "§aコマンドを削除します。command: " + command);
                        //  コマンドの削除
                        //  id取得
                        String id_before = item.getItemMeta().getLore().get(0);
                        int id = Integer.parseInt(id_before);
                        plugin.commandListData.removeCommandForAdmin(target, id);

                    } catch (NullPointerException e) {
                        //e.printStackTrace();
                        player.sendMessage(plugin.prefix + "§c§l無効な選択です。Error: NullPointerException Where: ClickEvent");
                    }

                }
            }catch (NullPointerException e){
                return;
            }

            //  ページ
            if(item.getType() == Material.SUNFLOWER){
                //  つぎのページ
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§e次のページ")){
                    if(inventory.getItem(44) == null){
                        player.sendMessage(plugin.prefix + "§c次のページが存在しません。");
                        return;
                    }
                    player.sendMessage(plugin.prefix + "§a次のページを開きます。");
                    int page = getPage(inventory);
                    page++;
                    player.closeInventory();
                    plugin.openGui.OpenRemoveGuiForAdmin(player, target, page);

                }
                //  前のページ
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§e前のページ")){
                    int page = getPage(inventory);
                    if(page <= 1){
                        return;
                    }
                    page--;
                    player.closeInventory();
                    plugin.openGui.OpenRemoveGuiForAdmin(player, target, page);
                }
            }

        }

    }

    private int getPage(Inventory inventory) {
        int i = 0;

        ItemStack item_49 = inventory.getItem(49);
        try {
            ItemMeta meta = item_49.getItemMeta();
            List<String> list = meta.getLore();
            String page = list.get(0);
            i = Integer.parseInt(page);
            return i;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return i;
        }
    }

    private void executeCommand(Player player, String command){

        //  execute
        Bukkit.dispatchCommand(player, command);
    }
}
