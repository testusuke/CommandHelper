package net.testusuke.commandhelper;

import net.testusuke.commandhelper.Command.HelperCommand;
import net.testusuke.commandhelper.Event.ChatEvent;
import net.testusuke.commandhelper.Event.HelperEvent;
import net.testusuke.commandhelper.Manager.MySQLManager;
import net.testusuke.commandhelper.Module.CommandList;
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
    //  PrepareCommandPlayer add/remove
    public HashMap<Player,String> prepareCommandPlayer = new HashMap<>();

    //  CommandListClass
    public CommandList cl = null;

    ///////////////////////////
    //  コマンド実行系変数   //
    ///////////////////////////
    //  OpenGuiClass
    public OpenGui openGui = null;


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
        getServer().getPluginManager().registerEvents(new ChatEvent(this), this);

        //  loadPrefix
        loadPrefix();

        //  MySQL
        mysql = new MySQLManager(this,"CommandHelper");
        mysql.debugMode = true;
        //  CreateTable
        createTable();

        //  Class
        cl = new CommandList(this);
        openGui = new OpenGui(this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //  Remove All Player Mode
        cl.removeAllPlayerMode();

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
    private String sqlCommandList = "";

    private void createTable(){
        mysql.execute(sqlCommandList);

        getServer().getLogger().info("Create Table!");
    }

}
