package net.testusuke.commandhelper.Command;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class HelperCommand implements CommandExecutor {

    private final CommandHelper plugin;

    public HelperCommand(CommandHelper plugin) {
        this.plugin = plugin;
    }

    //////////////
    //  Command //
    //////////////
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "You can't use console.");
            return false;
        }

        if(args.length == 0){
            Player player = (Player)sender;


            return true;
        }

        if (args.length == 1){
            Player player = (Player)sender;

            if (args[0].equalsIgnoreCase("help")){

                sendHelp(player);
                return true;
            }

            if(args[0].equalsIgnoreCase("add")){
                prepareAddCommand(player);

                player.sendMessage(plugin.prefix + "§e追加したいコマンドを[ / ]を付けないで入力してください。");
                return true;
            }

            if(args[0].equalsIgnoreCase("remove")){
                prepareRemoveCommand(player);

                player.sendMessage(plugin.prefix + "§e削除したいコマンドを[ / ]を付けないで入力してください。");
                return true;
            }
        }

        return false;
    }

    private void sendHelp(Player player){

        player.sendMessage("§e§l===================================");
        player.sendMessage("§e/cmdhelp <-登録しているコマンド一覧表示");
        player.sendMessage("§e/cmdhelp help <-ヘルプの表示");
        player.sendMessage("§e/cmdhelp add <-コマンドを追加する。§e§lコマンド実行後に[ / ]を省略した追加したいコマンドをチャットに入力してください");
        player.sendMessage("§e/cmdhelp remove <-コマンドを削除する。§e§lコマンド実行後に[ / ]を省略した削除したいコマンドを入力してください。");
        player.sendMessage("§d§lCreated by testusuke Version: " + plugin.version);
        player.sendMessage("§e§l===================================");
    }

    private void prepareAddCommand(Player player){
        plugin.cl.setMode(player, "add");
    }

    private void prepareRemoveCommand(Player player){
        plugin.cl.setMode(player, "remove");
    }

}
