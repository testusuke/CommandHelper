package net.testusuke.commandhelper.Data;

import net.testusuke.commandhelper.CommandHelper;
import net.testusuke.commandhelper.Manager.MySQLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
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
    private ConcurrentHashMap<Player, TreeMap<Integer, String>> PlayerCommandMap = new ConcurrentHashMap<>();

    //  プレイヤーと追加用コマンドリスト(List<command>)
    private ConcurrentHashMap<Player, ArrayList<String>> PlayerAddCommandMap = new ConcurrentHashMap<>();
    //  プレイヤーと削除用コマンドリスト(List<id>)
    private ConcurrentHashMap<Player, ArrayList<Integer>> PlayerRemoveCommandMap = new ConcurrentHashMap<>();


    public void createCommandMap(Player player) {
        if(PlayerCommandMap.containsKey(player)){
            return;
        }
        TreeMap<Integer, String> commandMap = new TreeMap<>();
        PlayerCommandMap.put(player, commandMap);
    }

    public TreeMap<Integer,String > getCommandMap(Player player) {
        TreeMap<Integer, String> commandMap;
        if (!PlayerCommandMap.containsKey(player)) {
            createCommandMap(player);
            return null;
        }
        commandMap = PlayerCommandMap.get(player);
        return commandMap;
    }

    public void setCommandMap(Player player, TreeMap<Integer, String> commandMap) {
        //  Map.put(Object,Object)Keyが存在してるときは上書き
        //if (PlayerCommandMap.containsKey(player)) {
        //    //PlayerCommandMap.remove(player);
        //    PlayerCommandMap.put(player, commandMap);
        //    return;
        //}

        PlayerCommandMap.put(player, commandMap);
    }

    public void removeCommandMap(Player player) {
        if (!PlayerCommandMap.containsKey(player)) {
            return;
        }
        PlayerCommandMap.remove(player);
    }

    public Boolean containsCommandMap(Player player) {
        boolean b = false;
        if (PlayerCommandMap.containsKey(player)) {
            b = true;
        }
        return b;
    }

    public synchronized void addCommand(Player player, String command) {
        TreeMap<Integer, String> commandMap = getCommandMap(player);
        //  仮想のIDを発行してcommandMapにputする(Keyの最後尾から i++; する)
        //  最後尾の値の取得
        //  Map → List
        ArrayList<Integer> list = new ArrayList<>();
        for(Integer id : commandMap.keySet()){
            list.add(id);
        }

        if(list.size() <= 0){
            //  仮想IDの発行
            int id = 1;

            //  Mapへ追加<仮想ID, Command>
            commandMap.put(id,command);
            //  PlayerCommandMapに追加
            setCommandMap(player,commandMap);

            //  PlayerAddCommandMapへの追加
            addPlayerAddCommandList(player,command);
            player.sendMessage(plugin.prefix + "§aコマンドを追加しました。command: " + command);
            return;
        }
        int index = list.size();
        index--;    //  listは0,1,2,3...なので -1
        int id = list.get(index);
        //  仮想IDの発行
        id++;   //  idを発行するために +1

        //  Mapへ追加<仮想ID, Command>
        commandMap.put(id,command);
        //  PlayerCommandMapに追加
        setCommandMap(player,commandMap);

        //  PlayerAddCommandMapへの追加
        addPlayerAddCommandList(player,command);

        player.sendMessage(plugin.prefix + "§aコマンドを追加しました。command: " + command);
    }

    public void removeCommand(Player player, Integer id){
        TreeMap<Integer, String > commandMap = getCommandMap(player);
        if(!commandMap.containsKey(id)){
            player.sendMessage(plugin.prefix + "§cコマンドが存在しません。");
            return;
        }
        //  commandMapからremoveする
        commandMap.remove(id);
        setCommandMap(player, commandMap);
        //  PlayerRemoveCommandMapへの追加
        addPlayerRemoveCommandList(player,id);


    }


    /*  TreeMapに変更したため、ソートの必要がなくなった
    public void sortCommandMap(Player player){
        TreeMap<Integer,String> commandMap = getCommandMap(player);
        if(commandMap == null){
            return;
        }
        //  sort-Key
        Object[] mapkey = commandMap.keySet().toArray();
        Arrays.sort(mapkey);

        setCommandMap(player, commandMap);
    }
    */

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

    public void removePlayerAddCommandList(Player player) {
        if (!PlayerAddCommandMap.containsKey(player)) {
            return;
        }
        PlayerAddCommandMap.remove(player);
    }

    public Boolean containsPlayerAddCommandList(Player player) {
        boolean b = false;
        if (PlayerAddCommandMap.containsKey(player)) {
            b = true;
        }
        return b;
    }

    public void addPlayerAddCommandList(Player player,String command){
        if(!containsPlayerAddCommandList(player)){
            createPlayerAddCommandList(player);
        }
        ArrayList<String> commandList = getPlayerAddCommandList(player);
        commandList.add(command);
        setPlayerAddCommandList(player,commandList);

    }


    /////////////////////////////////////////////
    //  PlayerRemoveCommandList関連のメソッド //
    /////////////////////////////////////////////

    public void createPlayerRemoveCommandList(Player player) {
        if (PlayerRemoveCommandMap.containsKey(player)) {

            return;
        }

        ArrayList<Integer> commandList = new ArrayList<>();
        PlayerRemoveCommandMap.put(player, commandList);
    }

    public ArrayList<Integer> getPlayerRemoveCommandList(Player player) {
        ArrayList<Integer> commandList;
        if (!PlayerRemoveCommandMap.containsKey(player)) {
            return null;
        }
        commandList = PlayerRemoveCommandMap.get(player);
        return commandList;
    }

    public void setPlayerRemoveCommandList(Player player, ArrayList<Integer> commandList) {
        if (PlayerRemoveCommandMap.containsKey(player)) {
            PlayerRemoveCommandMap.remove(player);
            PlayerRemoveCommandMap.put(player, commandList);
            return;
        }
        PlayerRemoveCommandMap.put(player, commandList);
    }

    public void removePlayerRemoveCommandList(Player player) {
        if (!PlayerRemoveCommandMap.containsKey(player)) {
            return;
        }
        PlayerRemoveCommandMap.remove(player);
    }

    public Boolean containsPlayerRemoveCommandList(Player player) {
        boolean b = false;
        if (PlayerRemoveCommandMap.containsKey(player)) {
            b = true;
        }
        return b;
    }

    public void addPlayerRemoveCommandList(Player player,int id){
        if(!containsPlayerRemoveCommandList(player)){
            createPlayerRemoveCommandList(player);
        }
        ArrayList<Integer> commandList = getPlayerRemoveCommandList(player);
        commandList.add(id);
        setPlayerRemoveCommandList(player,commandList);

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

    //  サーバー内のプレイヤーのロード
    public void loadCommandListWhenOnEnable(){
        Bukkit.getLogger().info("Start Load CommandMap!");
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            loadCommandList(player);
        }
        Bukkit.getLogger().info("Complete load CommandMap!");
    }

    //  サーバー内のプレイヤーのコマンド書き込み(シャットダウン時)
    public void writeCommandListWhenOnDisable(){
        Bukkit.getLogger().info("Start write CommandList");
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            writeCommandList(player);
        }
        Bukkit.getLogger().info("Complete write CommandList");
    }

    ////////////////////////////////////////////////////////////////////
    //  プレイヤーのログイン時、ログアウト時に実行されるメソッドなど  //
    ////////////////////////////////////////////////////////////////////
    /*
    ・プレイヤーのログイン時
    Mysqlにデータがない場合はcreateCommandMapで空を作る
    ある場合createCommandMap->読み込み
    PlayerAddCommandList,PlayerRemoveCommandListの作成
    ・プレイヤーのログアウト時
    PlayerAddCommandList,PlayerRemoveCommandListにデータの追加がなければ処理終了
    PlayerAddCommandListにデータが存在していた場合MySQLにUUIDとCommandでINSERTする
    PlayerRemoveCommandListにデータが存在していた場合MySQLにUUIDとArrayList<Integer>のIDでDELETEする
    */

    //  プレイヤーのログイン時のCommandのロード
    public synchronized void loadCommandList(Player player){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!containsCommandMap(player)) {
                    createCommandMap(player);
                } else {
                    getCommandMap(player).clear();
                }

                TreeMap<Integer, String> commandMap = getCommandMap(player);
                //DB
                String uuid = player.getUniqueId().toString();
                String sql = "SELECT id,command FROM cmdhelper_list WHERE uuid='" + uuid + "';";
                ResultSet rs = mysql.query(sql);

                try {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String command = rs.getString("command");
                        commandMap.put(id, command);
                    }
                    //  DB後片付け
                    rs.close();
                    mysql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Bukkit.getLogger().info("Load CommandMap. Player: " + player.getName());
            }
        }.runTaskAsynchronously(plugin);
    }

    //  プレイヤーログアウト時のコマンドリスト更新適応
    public synchronized void writeCommandList(Player player){
        if(getPlayerAddCommandList(player) == null && getPlayerRemoveCommandList(player) == null){
            removeCommandMap(player);
            removePlayerAddCommandList(player);
            removePlayerRemoveCommandList(player);
            Bukkit.getLogger().info("Nothing to write to DB. Player: " + player.getName());
            return;
        }

        new BukkitRunnable(){
            @Override
            public void run(){
                //  PlayerAddCommandList
                if(getPlayerAddCommandList(player) != null && getPlayerAddCommandList(player).size() > 0){
                    ArrayList<String> list = getPlayerAddCommandList(player);
                    String uuid = player.getUniqueId().toString();
                    for(String command : list){
                        String sql = "INSERT INTO cmdhelper_list (uuid,name,command) VALUES ('" + uuid +  "','" + player.getName() + "','" + command + "'); ";
                        mysql.execute(sql);
                    }

                    removePlayerAddCommandList(player);
                }
                //  PlayerRemoveCommandList
                if(getPlayerRemoveCommandList(player) != null && getPlayerRemoveCommandList(player).size() > 0){
                    ArrayList<Integer> list = getPlayerRemoveCommandList(player);
                    String uuid = player.getUniqueId().toString();
                    for(int id : list){
                        String sql = "DELETE FROM cmdhelper_list WHERE id = '" + id +"' AND uuid='" + uuid + "';";
                        mysql.execute(sql);
                    }

                    removePlayerRemoveCommandList(player);
                }

                //  CommandMap片付け
                removeCommandMap(player);

                Bukkit.getLogger().info("Write CommandMap. Player: " + player.getName());
            }
        }.runTaskAsynchronously(plugin);

    }


    /////////////////////////////
    //  OP用のプレイヤー情報   //
    /////////////////////////////
    //  checkChangeMCID
    public synchronized void  checkChangeMCID(Player player){
        new BukkitRunnable(){
            @Override
            public void run(){
                String uuid = player.getUniqueId().toString();
                String select_sql = "SELECT name FROM cmdhelper_list WHERE uuid='" + uuid + "' LIMIT 1;";
                ResultSet rs = mysql.query(select_sql);
                try {
                    if (!rs.next()) {
                        return;
                    }
                    String old_name = null;
                    while (rs.next()){
                        old_name = rs.getString("name");
                    }
                    if(old_name == null){
                        return;
                    }
                    if(old_name.equalsIgnoreCase(player.getName())){
                        String sql = "UPDATE cmdhelper_list SET name='" + player.getName() + "' WHERE uuid='" + uuid + "';";
                        mysql.execute(sql);

                        return;
                    }
                    mysql.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }.runTaskAsynchronously(plugin);
    }

    /**
     * if you use this method,you have to run Task.
     * @param mcid
     * @return uuid
     */
    //  getUUIDFromMCID
    public synchronized String getUUIDFromMCID(String mcid){
        String uuid = null;
        String sql = "SELECT uuid FROM cmdhelper_list WHERE name='" + mcid + "' LIMIT 1;";
        ResultSet rs = mysql.query(sql);
        try{
            if(!rs.next()){
                return null;
            }
            while (rs.next()){
                uuid = rs.getString("uuid");
            }

            return uuid;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return uuid;
    }

    //  getCommandList
    public TreeMap<Integer,String > getCommandMapAboutOffLinePlayer(String target){
        TreeMap<Integer, String> commandMap = new TreeMap<>();
        //DB
        String sql = "SELECT id,command FROM cmdhelper_list WHERE name='" + target + "';";
        ResultSet rs = mysql.query(sql);

        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String command = rs.getString("command");
                commandMap.put(id, command);
            }
            //  DB後片付け
            rs.close();
            mysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandMap;
    }

    public synchronized void removeCommandForAdmin(String target, int id){
        new BukkitRunnable(){
            @Override
            public void run(){
                String sql = "DELETE FROM cmdhelper_list WHERE id = '" + id +"' AND name='" + target + "';";
                mysql.execute(sql);
            }
        }.runTaskAsynchronously(plugin);
    }
}
