package io.github.rainpaw.autocompressors.commands;

import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCompressorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou can only use this command if you are a player.");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("getcompressor")) {
            if (args.length >= 1) {
                try {
                    int index = Integer.parseInt(args[0]);
                    player.getInventory().addItem(CompressorItemManager.getCompressor(index - 1).getItemStack());
                } catch(IllegalArgumentException | IndexOutOfBoundsException e) {
                    player.sendMessage("§cPlease provide an integer index that is in the range of 1-" + (CompressorItemManager.getCompressorAmount()) + ".");
                }
            } else {
                player.sendMessage("§c/getcompressor <compressor index>");
            }
        }

        return true;
    }

}
