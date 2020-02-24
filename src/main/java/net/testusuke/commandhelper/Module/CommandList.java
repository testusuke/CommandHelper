package net.testusuke.commandhelper.Module;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.UUID;

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

        if(plugin.prepareCommandPlayer.containsKey(player)){
            b = true;
        }
        return b;
    }

    //  モード
    public String getPlayerMode(Player player){
        String mode = "no";

        if(!checkPlayer(player)){
            return mode;
        }

        mode = plugin.prepareCommandPlayer.get(player);

        return mode;
    }

    //  モード設定
    public void setMode(Player player,String mode){

        if(!checkPlayer(player)){
            plugin.prepareCommandPlayer.remove(player);
            plugin.prepareCommandPlayer.put(player, mode);
            return;
        }

        plugin.prepareCommandPlayer.put(player, mode);
    }

    //  削除
    public void removeMode(Player player){
        plugin.prepareCommandPlayer.remove(player);
        return;
    }

    //  全削除
    public void removeAllPlayerMode(){

        plugin.prepareCommandPlayer.clear();
        return;
    }


    ////////////////////////////////////////////////////
    //////////  データベース系の操作メソッド  //////////
    ////////////////////////////////////////////////////

    //  コマンドの存在
    public boolean checkCommand(Player player, String command){
        boolean b = false;

        UUID uuid = player.getUniqueId();

        String sql = "SELECT * FROM `` WHERE `uuid` = `" + uuid + "`  AND `command` = `" + command + "`;";
        ResultSet rs = plugin.mysql.query(sql);

        return b;
    }

    //  コマンド追加
    public void addCommandToDB(Player player, String command){


    }

    //  コマンド削除
    public void removeCommandFromDB(Player player, String command){



    }

}
