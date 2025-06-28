/*
Confirmation screen
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

package io.github.studioeb.autocompressors.guis;

import io.github.studioeb.autocompressors.utils.GUIUtils;
import org.bukkit.Material;

public class ConfirmGUI extends BaseGUI {

    public ConfirmGUI(String warning, BaseGUI previousGUI, ConfirmAction action) {
        super(27, "Confirm Action", GUIUtils.GUIType.NORMAL);

        if (warning == null) {
            warning = "§c§lThis action is permanent and cannot be undone!";
        }

        GUIUtils.fillAllSlots(getInventory(), GUIUtils.getBgItem());
        setItem(11, GUIUtils.createGuiItem("§cConfirm", Material.GREEN_TERRACOTTA, warning, "", "§cClick to confirm!"), player -> {
            action.doAction();
            close(player);
            previousGUI.open(player);
        });
        setItem(15, GUIUtils.createGuiItem("§cCancel", Material.RED_TERRACOTTA, "", "§cClick to cancel!"), player -> {
            close(player);
            previousGUI.open(player);
        });
    }

    public interface ConfirmAction {
        void doAction();
    }
}
