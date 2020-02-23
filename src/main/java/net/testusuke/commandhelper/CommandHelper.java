package net.testusuke.commandhelper;

import net.testusuke.commandhelper.Command.HelperCommand;
import net.testusuke.commandhelper.Event.HelperEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class CommandHelper extends JavaPlugin {

    ////////////
    //  変数  //
    ////////////

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
    //  AddCommandPlayerList
    public ArrayList<Player> addCommandPlayerList = new ArrayList<>();
    //  RemoveCommandPlayerList
    public ArrayList<Player> removeCommandPlayerList = new ArrayList<>();



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

        //  loadPrefix
        loadPrefix();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private void loadPrefix(){
        FileConfiguration config = this.getConfig();
        prefix = config.getString("prefix");
    }

}
