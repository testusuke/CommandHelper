package net.testusuke.commandhelper.Command;

import net.testusuke.commandhelper.CommandHelper;
import org.bukkit.ChatColor;
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

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You can't use console.");
            return false;
        }

        if (args.length == 0) {
            Player player = (Player) sender;

            player.sendMessage(plugin.prefix + "§eコマンドリストを開きます。");
            plugin.openGui.OpenGui(player, 1);
            return true;
        }

        if (args.length == 1) {
            Player player = (Player) sender;

            if (args[0].equalsIgnoreCase("help")) {

                sendHelp(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("remove")) {
                player.sendMessage(plugin.prefix + "§e削除用のGUIを開きます。削除したいコマンドをクリックして下さい");
                plugin.openGui.OpenRemoveGui(player, 1);

                return true;
            }
        }
        if (args[0].equalsIgnoreCase("add")) {
            Player player = (Player)sender;
            //  追加
            String commands = getCommandFromStrings(args);
            if (commands == null) {
                player.sendMessage(plugin.prefix + "§c§l無効な使用方法です。/cmdhelp help を参照してください。");
                return false;
            }

            plugin.commandListData.addCommand(player, commands);
            return true;
        }


        return false;
    }

    private void sendHelp(Player player){

        player.sendMessage("§e§l===================================");
        player.sendMessage("§e/cmdhelp <-登録しているコマンド一覧表示");
        player.sendMessage("§e/cmdhelp help <-ヘルプの表示");
        player.sendMessage("§e/cmdhelp add (command) <-コマンドを追加する。§e§lコマンドの後に続けて追加したいコマンド§d§l[ / ]§e§lを入力してください");
        player.sendMessage("§e/cmdhelp remove <-コマンド削除用のGUIを開きます。");
        player.sendMessage("§d§lCreated by testusuke Version: " + plugin.version);
        player.sendMessage("§e§l===================================");
    }

    private String getCommandFromStrings(String[] command){
        //String[] command = command.split(" ");
        String encode_command = null;
        int i = 0;
        for(String s : command){
            if(i < 1){
                i++;
                continue;
            }
            if(encode_command == null){
                encode_command = s;
            }else {
                encode_command += " " + s;
            }
            i++;
        }
        return encode_command;
    }

}
