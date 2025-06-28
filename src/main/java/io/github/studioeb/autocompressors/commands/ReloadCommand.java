/*
Reload Config command
Copyright (C) 2025 Ethan Bayer

This file is part of AutoCompressors.

AutoCompressors is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

AutoCompressors is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package io.github.studioeb.autocompressors.commands;

import io.github.studioeb.autocompressors.AutoCompressors;
import io.github.studioeb.autocompressors.items.CompressorItemManager;
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
