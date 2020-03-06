package net.testusuke.commandhelper.Module;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandList {

    private final CommandHelper plugin;

    public CommandList(CommandHelper plugin){
        this.plugin = plugin;

    }


    ///////////////////////////////////////////////////
    //////////////  Mapなどの変数操作  ////////////////
    ///////////////////////////////////////////////////
    //  存在するか
    public boolean checkPlayer(Player player){
        Boolean b = false;

        if(plugin.addCommandPlayer.containsKey(player)){
            b = true;
        }
        return b;
    }

    //  モード
    public Boolean getPlayerMode(Player player){
        boolean mode;

        if(!checkPlayer(player)){
            return false;
        }

        mode = plugin.addCommandPlayer.get(player);

        return mode;
    }

    //  モード設定
    public void setMode(Player player,boolean mode){

        if(!checkPlayer(player)){
            plugin.addCommandPlayer.remove(player);
            plugin.addCommandPlayer.put(player, mode);
            return;
        }

        plugin.addCommandPlayer.put(player, mode);
    }

    //  削除
    public void removeMode(Player player){
        plugin.addCommandPlayer.remove(player);
        return;
    }

    //  全削除
    public void removeAllPlayerMode(){

        plugin.addCommandPlayer.clear();
        return;
    }


    ////////////////////////////////////////////////////
    //////////  データベース系の操作メソッド  //////////
    ////////////////////////////////////////////////////

    //  コマンド追加
    public void addCommandToDB(Player player, String command){

        new BukkitRunnable(){
            @Override
            public void run(){
                boolean b = false;
                String uuid = player.getUniqueId().toString();

                String sql = "INSERT INTO cmdhelper_list (uuid,name,command) VALUES ('" + uuid +  "','" + player.getName() + "','" + command + "'); ";
                //  mysql
               b = plugin.mysql.execute(sql);
                if(b){
                    //  Message
                    player.sendMessage(plugin.prefix + "§aコマンドを追加しました。command: " + command);
                    Bukkit.getLogger().info("Add command player: " + player.getName() + " command: " + command);
                }else {
                    //  Message
                    player.sendMessage(plugin.prefix + "§c§lコマンドの追加に失敗しました。command: " + command);

                }
            }
        }.runTaskAsynchronously(this.plugin);

    }

    //  コマンド削除
    public void removeCommandFromDB(Player player, String id){

        new BukkitRunnable(){
            @Override
            public void run(){
                boolean b = false;
                String uuid = player.getUniqueId().toString();

                String sql = "DELETE FROM cmdhelper_list WHERE id = '" + id +  "' AND uuid = '" + uuid + "';";
                //  mysql
                b = plugin.mysql.execute(sql);
                if(b){
                    player.sendMessage(plugin.prefix + "§aコマンドを削除しました。");
                }else {
                    player.sendMessage(plugin.prefix + "§c§lコマンドの削除に失敗しました。");
                }
            }
        }.runTaskAsynchronously(this.plugin);
    }

}
