package io.github.rainpaw.autocompressors.commands;

import io.github.rainpaw.autocompressors.AutoCompressors;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final AutoCompressors plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("autocompressorsreload")) {
            plugin.reloadConfig();
            CompressorItemManager.initializeItems(plugin);
            sender.sendMessage("AutoCompressors config reloaded and items reinitialized.");
        }
        return true;
    }

    public ReloadCommand(AutoCompressors autoCompressors) {
        plugin = autoCompressors;
    }
}
