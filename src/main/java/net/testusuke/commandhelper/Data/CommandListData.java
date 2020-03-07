package net.testusuke.commandhelper.Data;

import net.testusuke.commandhelper.CommandHelper;
import net.testusuke.commandhelper.Manager.MySQLManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CommandListData {
    private final CommandHelper plugin;
    //  mysql
    private MySQLManager mysql = null;

    public CommandListData(CommandHelper plugin) {
        this.plugin = plugin;
        //  MySQL
        mysql = new MySQLManager(plugin,"CommandHelper<CommandListData.class>");
        mysql.debugMode = true;

    }

    ////////////
    //  変数  //
    ////////////

    //  プレイヤーとコマンドリスト保存(id,command)
    private ConcurrentHashMap<Player, HashMap<Integer, String>> PlayerCommandMap = new ConcurrentHashMap<>();

    //  プレイヤーと追加用コマンドリスト(List<command>)
    private ConcurrentHashMap<Player, ArrayList<String>> PlayerAddCommandMap = new ConcurrentHashMap<>();
    //  プレイヤーと削除用コマンドリスト(List<id>)
    private ConcurrentHashMap<Player, ArrayList<Integer>> PlayerRemoveCommandMap = new ConcurrentHashMap<>();


    public void createCommandMap(Player player) {
        HashMap<Integer, String> commandMap = new HashMap<>();
        //  DBでコマンド追加処理

        PlayerCommandMap.put(player, commandMap);
    }

    public HashMap getCommandMap(Player player) {
        HashMap<Integer, String> commandMap;
        if (!PlayerCommandMap.containsKey(player)) {
            return null;
        }
        commandMap = PlayerCommandMap.get(player);
        return commandMap;
    }

    public void setCommandMap(Player player, HashMap<Integer, String> commandMap) {
        if (PlayerCommandMap.containsKey(player)) {
            PlayerCommandMap.remove(player);
            PlayerCommandMap.put(player, commandMap);
            return;
        }

        PlayerCommandMap.put(player, commandMap);
    }

    public void removeCommandMap(Player player) {
        if (!PlayerCommandMap.containsKey(player)) {
            return;
        }
        PlayerCommandMap.remove(player);
    }

    public Boolean CommandMapContains(Player player) {
        boolean b = false;
        if (PlayerCommandMap.containsKey(player)) {
            b = true;
        }
        return b;
    }

    public void addCommand(Player player, String command) {
        HashMap<Integer, String> commandMap = getCommandMap(player);


    }

    ////////////////////////////////////////
    //  PlayerAddCommandMap関連メソッド   //
    ////////////////////////////////////////

    public void createPlayerAddCommandList(Player player) {
        if (PlayerAddCommandMap.containsKey(player)) {

            return;
        }

        ArrayList<String> commandList = new ArrayList<>();
        PlayerAddCommandMap.put(player, commandList);
    }

    public ArrayList<String> getPlayerAddCommandList(Player player) {
        ArrayList<String> commandList;
        if (!PlayerAddCommandMap.containsKey(player)) {
            return null;
        }
        commandList = PlayerAddCommandMap.get(player);
        return commandList;
    }

    public void setPlayerAddCommandList(Player player, ArrayList<String> commandList) {
        if (PlayerAddCommandMap.containsKey(player)) {
            PlayerAddCommandMap.remove(player);
            PlayerAddCommandMap.put(player, commandList);
            return;
        }
        PlayerAddCommandMap.put(player, commandList);
    }

    public void RemovePlayerAddCommandList(Player player) {
        if (!PlayerAddCommandMap.containsKey(player)) {
            return;
        }
        PlayerAddCommandMap.remove(player);
    }

    public Boolean PlayerAddCommandListContains(Player player) {
        boolean b = false;
        if (PlayerAddCommandMap.containsKey(player)) {
            b = true;
        }
        return b;
    }


    /////////////////////////////////////////////
    //  PlayerRemoveCommandList関連のメソッド //
    /////////////////////////////////////////////

    public void createPlayerRemoveCommandList(Player player) {

    }


    ///////////////////////////////////////////////////////////////////
    //  サーバーの起動時、シャットダウン時に実行されるメソッドなど   //
    ///////////////////////////////////////////////////////////////////
    /*
    ・サーバー起動時
    サーバ内にいるプレーヤーのロード
    ・シャットダウン時
    PlayerAddCommandList,PlayerRemoveCommandListのMySQLへの処理
    変数の後片付け
     */


    ////////////////////////////////////////////////////////////////////
    //  プレイヤーのログイン時、ログアウト時に実行されるメソッドなど  //
    ////////////////////////////////////////////////////////////////////
    /*
    ・プレイヤーのログイン時
    MySQLにデータが存在しない場合はcreateCommandMapで空のMapを作る、ある場合は通常どうりcreateCommandMap
    PlayerAddCommandList,PlayerRemoveCommandListの作成
    ・プレイヤーのログアウト時
    PlayerAddCommandList,PlayerRemoveCommandListにデータの追加がなければ処理終了
    PlayerAddCommandListにデータが存在していた場合MySQLにUUIDとCommandでINSERTする
    PlayerRemoveCommandListにデータが存在していた場合MySQLにUUIDとArrayList<Integer>のIDでDELETEする
    */



}
