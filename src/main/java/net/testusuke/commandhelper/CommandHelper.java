package net.testusuke.commandhelper;

import net.testusuke.commandhelper.Command.HelperCommand;
import net.testusuke.commandhelper.Data.CommandListData;
import net.testusuke.commandhelper.Event.AdminEvent;
import net.testusuke.commandhelper.Event.HelperEvent;
import net.testusuke.commandhelper.Manager.MySQLManager;
import net.testusuke.commandhelper.Module.OpenGui;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class CommandHelper extends JavaPlugin {

    ////////////
    //  変数  //
    ////////////

    //////////////
    //  MySQL   //
    //////////////
    public MySQLManager mysql = null;

    //////////////////////////
    //  Plugin Information  //
    //////////////////////////
    //  Prefix
    public String prefix;
    //  plugin
    public String pluginName = "CommandHelper";
    //  Version
    public String version = "1.0.0";


    /////////////////////////////////////////////
    //  チャットでのコマンド追加・削除用変数  //
    ////////////////////////////////////////////
    //  CommandPlayer add
    public HashMap<Player,Boolean> addCommandPlayer = new HashMap<>();

    ///////////////////////
    //  コマンド系変数   //
    ///////////////////////
    //  OpenGuiClass
    public OpenGui openGui = null;

    /////////////////////////////
    //  スレッドセーフのクラス //
    /////////////////////////////
    //  CommandListData
    public CommandListData commandListData = null;


    @Override
    public void onEnable() {
        // Plugin startup logic
        //  Logger
        getLogger().info("==============================");
        getLogger().info("Plugin: " + pluginName);
        getLogger().info("Ver: " + version + "  Author: testusuke");
        getLogger().info("==============================");

        //  Config
        this.saveDefaultConfig();

        //  Command
        getCommand("cmdhelp").setExecutor(new HelperCommand(this));
        //  Event
        getServer().getPluginManager().registerEvents(new HelperEvent(this), this);
        getServer().getPluginManager().registerEvents(new AdminEvent(this), this);
        //  loadPrefix
        loadPrefix();
        //  MySQL
        mysql = new MySQLManager(this,"CommandHelper");
        mysql.debugMode = true;
        //  CreateTable
        createTable();
        //  Class
        openGui = new OpenGui(this);
        commandListData = new CommandListData(this);
        //  コマンドリストのロード
        commandListData.loadCommandListWhenOnEnable();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //  Logger
        getLogger().info("onDisable");
        //  コマンドリスト更新の適応
        commandListData.writeCommandListWhenOnDisable();

    }


    private void loadPrefix(){
        FileConfiguration config = this.getConfig();
        try {
            prefix = config.getString("prefix").replace("&", "§");
        }catch (NullPointerException e){
            e.printStackTrace();
            prefix = "§e[§aCommandHelper§e]§f";
        }

    }

    //////////////////////////
    //  CommandListTable    //
    //////////////////////////
    private String sqlCommandList = "CREATE TABLE `cmdhelper_list` (\n" +
            "  id INT unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  uuid VARCHAR(36) NULL DEFAULT NULL,\n" +
            "  name VARCHAR(16) NULL DEFAULT NULL,\n" +
            "  command VARCHAR(256) NOT NULL,\n" +
            "  PRIMARY KEY (id));";

    private void createTable(){
        mysql.execute(sqlCommandList);

        getServer().getLogger().info("Create Table.");
    }

}
