package net.testusuke.commandhelper.Module;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.entity.Player;

import java.sql.ResultSet;

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

    //  コマンドの存在
    public boolean existCommand(Player player, String command){
        boolean b = false;

        String uuid = player.getUniqueId().toString();

        String sql = "SELECT * FROM cmdhelper_list WHERE uuid = '" + uuid + "'  AND command = '" + command + "';";
        ResultSet rs = plugin.mysql.query(sql);



        return b;
    }

    //  コマンド追加
    public void addCommandToDB(Player player, String command){

        String uuid = player.getUniqueId().toString();

        String sql = "INSERT INTO cmdhelper_list(uuid,name,command) VALUES('" + uuid +  "','" + player.getName() + "','" + command + "'); ";
        //  mysql
        plugin.mysql.execute(sql);

    }

    //  コマンド削除
    public boolean removeCommandFromDB(Player player, String id){
        boolean b = false;
        String uuid = player.getUniqueId().toString();

        String sql = "DELETE FROM cmdhelper_list WHERE id = '" + id +  "' AND uuid = '" + uuid + "';";
        //  mysql
        b = plugin.mysql.execute(sql);

        return b;
    }

}
