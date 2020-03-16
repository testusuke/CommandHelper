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

import java.util.ArrayList;
import java.util.TreeMap;

public class OpenGui {

    private final CommandHelper plugin;

    public OpenGui(CommandHelper plugin){
        this.plugin = plugin;
    }

    public void OpenGui(Player player, int page) {
        Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, plugin.prefix + "§e§lコマンド一覧");

        //  コマンドリスト取得
        //  一次関数 y=45x-45
        int index = (45 * page) - 45;
        TreeMap<Integer,String> map = plugin.commandListData.getCommandMap(player);

        if (map == null || map.size() <= 0) {
            player.sendMessage(plugin.prefix + "§cコマンドリストが存在しませんでした。§a/cmdhelp add でコマンドを追加しましょう。");
            return;
        } else {
            int count = 0;
            for(Integer id : map.keySet()){
                if(count < index){
                    count++;
                    continue;
                }
                String command = map.get(id);
                ItemStack item = createItemAboutAdd(command, id);
                inv.setItem(count - index, item);
                if(count >=44+index){
                    break;
                }
                count++;
            }
        }
        //  ページ切り替え、ページ数のアイテムの設置
        //  次のページ
        ItemStack item_nextPage = new ItemStack(Material.SUNFLOWER);
        ItemMeta meta_nextPage = item_nextPage.getItemMeta();
        meta_nextPage.setDisplayName("§e次のページ");
        item_nextPage.setItemMeta(meta_nextPage);
        inv.setItem(53, item_nextPage);
        //  前のページ
        ItemStack item_backPage = new ItemStack(Material.SUNFLOWER);
        ItemMeta meta_backPage = item_backPage.getItemMeta();
        meta_backPage.setDisplayName("§e前のページ");
        item_backPage.setItemMeta(meta_backPage);
        inv.setItem(45, item_backPage);
        //  ページ数
        ItemStack item_pageIndex = new ItemStack(Material.BOOK);
        ItemMeta meta_pageIndex = item_pageIndex.getItemMeta();
        meta_pageIndex.setDisplayName("§aページ数");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(page + "");
        meta_pageIndex.setLore(lore);
        item_pageIndex.setItemMeta(meta_pageIndex);
        inv.setItem(49, item_pageIndex);

        player.openInventory(inv);


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

    public void OpenRemoveGui(Player player, int page){
        Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, plugin.prefix + "§c§lコマンド削除用GUI");

        //  コマンドリスト取得
        //  一次関数 y=45x-45
        int index = (45 * page) - 45;
        TreeMap<Integer,String> map = plugin.commandListData.getCommandMap(player);

        if (map == null || map.size() <= 0) {
            player.sendMessage(plugin.prefix + "§cコマンドリストが存在しませんでした。");
            return;
        } else {
            int count = 0;
            for(Integer id : map.keySet()){
                if(count < index){
                    count++;
                    continue;
                }
                String command = map.get(id);
                ItemStack item = createItemAboutRemove(command, id);
                inv.setItem(count - index, item);

                if(count >= 44 + index){
                    break;
                }
                count++;
            }
        }
        //  ページ切り替え、ページ数のアイテムの設置
        //  次のページ
        ItemStack item_nextPage = new ItemStack(Material.SUNFLOWER);
        ItemMeta meta_nextPage = item_nextPage.getItemMeta();
        meta_nextPage.setDisplayName("§e次のページ");
        item_nextPage.setItemMeta(meta_nextPage);
        inv.setItem(53, item_nextPage);
        //  前のページ
        ItemStack item_backPage = new ItemStack(Material.SUNFLOWER);
        ItemMeta meta_backPage = item_backPage.getItemMeta();
        meta_backPage.setDisplayName("§e前のページ");
        item_backPage.setItemMeta(meta_backPage);
        inv.setItem(45, item_backPage);
        //  ページ数
        ItemStack item_pageIndex = new ItemStack(Material.BOOK);
        ItemMeta meta_pageIndex = item_pageIndex.getItemMeta();
        meta_pageIndex.setDisplayName("§aページ数");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("" + page);
        meta_pageIndex.setLore(lore);
        item_pageIndex.setItemMeta(meta_pageIndex);
        inv.setItem(49, item_pageIndex);

        player.openInventory(inv);
    }

    private ItemStack createItemAboutRemove(String command, int id){
        ItemStack item;
        item = new ItemStack(Material.RED_WOOL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(command);
        ArrayList<String> list = new ArrayList<>();
        list.add(id + "");
        meta.setLore(list);
        item.setItemMeta(meta);

        return item;
    }



    ///////////////////////
    //  Admin用OpenGui   //
    ///////////////////////
    public void OpenGuiForAdmin(Player player, String target, int page){
        if(Bukkit.getPlayer(target) != null){
            Player target_Player = Bukkit.getPlayer(target);
            Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, plugin.prefix + "§e§lコマンド一覧　ADMIN");

            //  コマンドリスト取得
            //  一次関数 y=45x-45
            int index = (45 * page) - 45;
            TreeMap<Integer,String> map = plugin.commandListData.getCommandMap(target_Player);

            if (map == null || map.size() <= 0) {
                player.sendMessage(plugin.prefix + "§c" + target + "のコマンドリストが存在しませんでした。");
                return;
            } else {
                int count = 0;
                for(Integer id : map.keySet()){
                    if(count < index){
                        count++;
                        continue;
                    }
                    String command = map.get(id);
                    ItemStack item = createItemAboutAdd(command, id);
                    inv.setItem(count - index, item);
                    if(count >=44+index){
                        break;
                    }
                    count++;
                }
            }
            //  ページ切り替え、ページ数のアイテムの設置
            //  次のページ
            ItemStack item_nextPage = new ItemStack(Material.SUNFLOWER);
            ItemMeta meta_nextPage = item_nextPage.getItemMeta();
            meta_nextPage.setDisplayName("§e次のページ");
            item_nextPage.setItemMeta(meta_nextPage);
            inv.setItem(53, item_nextPage);
            //  前のページ
            ItemStack item_backPage = new ItemStack(Material.SUNFLOWER);
            ItemMeta meta_backPage = item_backPage.getItemMeta();
            meta_backPage.setDisplayName("§e前のページ");
            item_backPage.setItemMeta(meta_backPage);
            inv.setItem(45, item_backPage);
            //  ページ数
            ItemStack item_pageIndex = new ItemStack(Material.BOOK);
            ItemMeta meta_pageIndex = item_pageIndex.getItemMeta();
            meta_pageIndex.setDisplayName("§aページ数");
            ArrayList<String> lore = new ArrayList<>();
            lore.add(page + "");
            meta_pageIndex.setLore(lore);
            item_pageIndex.setItemMeta(meta_pageIndex);
            inv.setItem(49, item_pageIndex);
            //  プレイヤー名
            ItemStack item_palyer = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta_palyer = item_palyer.getItemMeta();
            meta_palyer.setDisplayName(target);
            item_palyer.setItemMeta(meta_palyer);
            inv.setItem(48, item_palyer);

            player.openInventory(inv);

        }else {
            new BukkitRunnable(){
                @Override
                public void run(){
                    Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, plugin.prefix + "§e§lコマンド一覧　ADMIN");

                    //  コマンドリスト取得
                    //  一次関数 y=45x-45
                    int index = (45 * page) - 45;
                    TreeMap<Integer,String> map = plugin.commandListData.getCommandMapAboutOffLinePlayer(target);

                    if (map == null || map.size() <= 0) {
                        player.sendMessage(plugin.prefix + "§c" + target + "のコマンドリストが存在しませんでした。");
                        return;
                    } else {
                        int count = 0;
                        for(Integer id : map.keySet()){
                            if(count < index){
                                count++;
                                continue;
                            }
                            String command = map.get(id);
                            ItemStack item = createItemAboutAdd(command, id);
                            inv.setItem(count - index, item);
                            if(count >=44+index){
                                break;
                            }
                            count++;
                        }
                    }
                    //  ページ切り替え、ページ数のアイテムの設置
                    //  次のページ
                    ItemStack item_nextPage = new ItemStack(Material.SUNFLOWER);
                    ItemMeta meta_nextPage = item_nextPage.getItemMeta();
                    meta_nextPage.setDisplayName("§e次のページ");
                    item_nextPage.setItemMeta(meta_nextPage);
                    inv.setItem(53, item_nextPage);
                    //  前のページ
                    ItemStack item_backPage = new ItemStack(Material.SUNFLOWER);
                    ItemMeta meta_backPage = item_backPage.getItemMeta();
                    meta_backPage.setDisplayName("§e前のページ");
                    item_backPage.setItemMeta(meta_backPage);
                    inv.setItem(45, item_backPage);
                    //  ページ数
                    ItemStack item_pageIndex = new ItemStack(Material.BOOK);
                    ItemMeta meta_pageIndex = item_pageIndex.getItemMeta();
                    meta_pageIndex.setDisplayName("§aページ数");
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(page + "");
                    meta_pageIndex.setLore(lore);
                    item_pageIndex.setItemMeta(meta_pageIndex);
                    inv.setItem(49, item_pageIndex);
                    //  プレイヤー名
                    ItemStack item_palyer = new ItemStack(Material.PLAYER_HEAD);
                    ItemMeta meta_palyer = item_palyer.getItemMeta();
                    meta_palyer.setDisplayName(target);
                    item_palyer.setItemMeta(meta_palyer);
                    inv.setItem(48, item_palyer);

                    player.openInventory(inv);
                }
            }.runTaskAsynchronously(plugin);
        }
    }

    //  削除
    public void OpenRemoveGuiForAdmin(Player player, String target, int page){
        if(Bukkit.getPlayer(target) != null){
            Player target_Player = Bukkit.getPlayer(target);
            Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, plugin.prefix + "§c§lコマンド削除用GUI ADMIN");

            //  コマンドリスト取得
            //  一次関数 y=45x-45
            int index = (45 * page) - 45;
            TreeMap<Integer,String> map = plugin.commandListData.getCommandMap(target_Player);

            if (map == null || map.size() <= 0) {
                player.sendMessage(plugin.prefix + "§cコマンドリストが存在しませんでした。");
                return;
            } else {
                int count = 0;
                for(Integer id : map.keySet()){
                    if(count < index){
                        count++;
                        continue;
                    }
                    String command = map.get(id);
                    ItemStack item = createItemAboutRemove(command, id);
                    inv.setItem(count - index, item);

                    if(count >= 44 + index){
                        break;
                    }
                    count++;
                }
            }
            //  ページ切り替え、ページ数のアイテムの設置
            //  次のページ
            ItemStack item_nextPage = new ItemStack(Material.SUNFLOWER);
            ItemMeta meta_nextPage = item_nextPage.getItemMeta();
            meta_nextPage.setDisplayName("§e次のページ");
            item_nextPage.setItemMeta(meta_nextPage);
            inv.setItem(53, item_nextPage);
            //  前のページ
            ItemStack item_backPage = new ItemStack(Material.SUNFLOWER);
            ItemMeta meta_backPage = item_backPage.getItemMeta();
            meta_backPage.setDisplayName("§e前のページ");
            item_backPage.setItemMeta(meta_backPage);
            inv.setItem(45, item_backPage);
            //  ページ数
            ItemStack item_pageIndex = new ItemStack(Material.BOOK);
            ItemMeta meta_pageIndex = item_pageIndex.getItemMeta();
            meta_pageIndex.setDisplayName("§aページ数");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("" + page);
            meta_pageIndex.setLore(lore);
            item_pageIndex.setItemMeta(meta_pageIndex);
            inv.setItem(49, item_pageIndex);
            //  プレイヤー名
            ItemStack item_palyer = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta_palyer = item_palyer.getItemMeta();
            meta_palyer.setDisplayName(target);
            item_palyer.setItemMeta(meta_palyer);
            inv.setItem(48, item_palyer);

            player.openInventory(inv);
        }else {
            Inventory inv = Bukkit.createInventory((InventoryHolder) null, 54, plugin.prefix + "§c§lコマンド削除用GUI ADMIN");

            //  コマンドリスト取得
            //  一次関数 y=45x-45
            int index = (45 * page) - 45;
            TreeMap<Integer,String> map = plugin.commandListData.getCommandMapAboutOffLinePlayer(target);

            if (map == null || map.size() <= 0) {
                player.sendMessage(plugin.prefix + "§cコマンドリストが存在しませんでした。");
                return;
            } else {
                int count = 0;
                for(Integer id : map.keySet()){
                    if(count < index){
                        count++;
                        continue;
                    }
                    String command = map.get(id);
                    ItemStack item = createItemAboutRemove(command, id);
                    inv.setItem(count - index, item);

                    if(count >= 44 + index){
                        break;
                    }
                    count++;
                }
            }
            //  ページ切り替え、ページ数のアイテムの設置
            //  次のページ
            ItemStack item_nextPage = new ItemStack(Material.SUNFLOWER);
            ItemMeta meta_nextPage = item_nextPage.getItemMeta();
            meta_nextPage.setDisplayName("§e次のページ");
            item_nextPage.setItemMeta(meta_nextPage);
            inv.setItem(53, item_nextPage);
            //  前のページ
            ItemStack item_backPage = new ItemStack(Material.SUNFLOWER);
            ItemMeta meta_backPage = item_backPage.getItemMeta();
            meta_backPage.setDisplayName("§e前のページ");
            item_backPage.setItemMeta(meta_backPage);
            inv.setItem(45, item_backPage);
            //  ページ数
            ItemStack item_pageIndex = new ItemStack(Material.BOOK);
            ItemMeta meta_pageIndex = item_pageIndex.getItemMeta();
            meta_pageIndex.setDisplayName("§aページ数");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("" + page);
            meta_pageIndex.setLore(lore);
            item_pageIndex.setItemMeta(meta_pageIndex);
            inv.setItem(49, item_pageIndex);
            //  プレイヤー名
            ItemStack item_palyer = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta_palyer = item_palyer.getItemMeta();
            meta_palyer.setDisplayName(target);
            item_palyer.setItemMeta(meta_palyer);
            inv.setItem(48, item_palyer);

            player.openInventory(inv);

        }

    }

}
