package net.testusuke.commandhelper;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvent implements Listener {
    private final CommandHelper plugin;

    public PlayerEvent(CommandHelper plugin) {
        this.plugin = plugin;
    }

    //  onJoin
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        plugin.commandListData.loadCommandList(player);
        player.sendMessage(plugin.prefix + "§a§lようこそ！あなたのコマンドリストを読み込みました。");
    }

}
