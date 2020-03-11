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
        Inventory inventory = event.getInventory();

        if(event.getView().getTitle().equalsIgnoreCase(plugin.prefix + "§e§lコマンド一覧")){
            event.setCancelled(true);

            //  アイテムが黄緑色の羊毛か
            if(item.getType() == Material.LIME_WOOL){
                try {
                    String command = item.getItemMeta().getDisplayName();
                    player.sendMessage(plugin.prefix + "§aコマンドを実行します。command: " + command);
                    //  コマンドの実行
                    executeCommand(player, command);

                }catch (NullPointerException e){
                    //e.printStackTrace();
                    player.sendMessage(plugin.prefix + "§c§l無効な選択です。Error: NullPointerException Where: ClickEvent");
                }

            }

            //  ページ
            if(item.getType() == Material.SUNFLOWER){
                //  つぎのページ
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§e次のページ")){
                    if(inventory.getItem(44).getType() == Material.AIR || inventory.getItem(44) == null){
                        player.sendMessage(plugin.prefix + "§c次のページが存在しません。");
                        return;
                    }
                    player.sendMessage(plugin.prefix + "§a次のページを開きます。");
                    int page = getPage(inventory);
                    page++;
                    plugin.openGui.OpenGui(player, page);

                }
                //  前のページ
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§e前のページ")){
                    int page = getPage(inventory);
                    if(page <= 1){
                        return;
                    }
                    page--;
                    plugin.openGui.OpenGui(player,page);
                }
            }

        }


        ////////////////////////////////////////////////
        //  コマンド削除用GUI
        ////////////////////////////////////////////////
        if(event.getView().getTitle().equalsIgnoreCase(plugin.prefix + "§c§lコマンド削除用GUI")){
            event.setCancelled(true);

            //  アイテムが黄緑色の羊毛か
            if(item.getType() == Material.RED_WOOL){
                try {
                    String command = item.getItemMeta().getDisplayName();
                    player.sendMessage(plugin.prefix + "§aコマンドを削除します。command: " + command);
                    //  コマンドの削除
                    //  id取得
                    String id_before = item.getItemMeta().getLore().get(0);
                    int id = Integer.parseInt(id_before);

                    plugin.commandListData.removerCommand(player, id);

                }catch (NullPointerException e){
                    //e.printStackTrace();
                    player.sendMessage(plugin.prefix + "§c§l無効な選択です。Error: NullPointerException Where: ClickEvent");
                }

            }

            //  ページ
            if(item.getType() == Material.SUNFLOWER){
                //  つぎのページ
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§e次のページ")){
                    if(inventory.getItem(44).getType() == Material.AIR || inventory.getItem(44) == null){
                        player.sendMessage(plugin.prefix + "§c次のページが存在しません。");
                        return;
                    }
                    player.sendMessage(plugin.prefix + "§a次のページを開きます。");
                    int page = getPage(inventory);
                    page++;
                    plugin.openGui.OpenGui(player, page);

                }
                //  前のページ
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§e前のページ")){
                    int page = getPage(inventory);
                    if(page <= 1){
                        return;
                    }
                    page--;
                    plugin.openGui.OpenGui(player,page);
                }
            }

        }

    }

    private int getPage(Inventory inventory){
        int i = 0;

        ItemStack item_49 = inventory.getItem(49);
        if(item_49.getType() == Material.BOOK){
            String name = item_49.getItemMeta().getDisplayName();
            String page = name.replace("§aページ： ", "");
            i = Integer.getInteger(page);
        }

        return i;
    }

    /*
    private int getLastIndex(Inventory inventory){
        int index;
        ItemStack item = inventory.getItem(44);
        if(item == null){
            return 0;
        }
        List<String> list = item.getItemMeta().getLore();
        String s = list.get(0);
        index = Integer.getInteger(s);

        return index;
    }

    private int getFirstIndex(Inventory inventory){
        int index;
        ItemStack item = inventory.getItem(0);
        if(item == null){
            return 0;
        }
        List<String> list = item.getItemMeta().getLore();
        String s = list.get(0);
        index = Integer.getInteger(s);

        return index;
    }
    */

    private void executeCommand(Player player, String command){

        //  execute
        Bukkit.dispatchCommand(player, command);
    }
}
