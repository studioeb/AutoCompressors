/*
Command to open the Edit Compressors GUI
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
import io.github.studioeb.autocompressors.guis.CompressorViewGUI;
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
