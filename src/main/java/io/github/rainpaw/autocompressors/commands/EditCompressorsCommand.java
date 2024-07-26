package io.github.rainpaw.autocompressors.commands;

import io.github.rainpaw.autocompressors.AutoCompressors;
import io.github.rainpaw.autocompressors.guis.CompressorViewGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditCompressorsCommand implements CommandExecutor {

    private final AutoCompressors plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou can only use this command if you are a player.");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("editcompressors")) {
            CompressorViewGUI gui = new CompressorViewGUI(plugin);
            gui.open(player);
        }

        return true;
    }

    public EditCompressorsCommand(AutoCompressors plugin) {
        this.plugin = plugin;
    }
}
