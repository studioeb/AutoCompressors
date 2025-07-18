/*
GUI where the material of the compressor is entered
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
import org.bukkit.inventory.ItemStack;

public class MaterialEnterGUI extends BaseGUI {

    private final CompressorEditGUI editGUI;
    private Material materialInSlot = null;

    public MaterialEnterGUI(CompressorEditGUI editGUI) {
        super(45, "Change Material", GUIUtils.GUIType.MATERIAL_ENTER);

        this.editGUI = editGUI;

        refreshGUI();
    }

    public void refreshGUI() {
        GUIUtils.fillAllSlots(getInventory(), GUIUtils.getBgItem());
        setItem(40, GUIUtils.createGuiItem("§aBack", Material.ARROW), player -> {
            close(player);
            editGUI.open(player);
        });
        if (materialInSlot == null) {
            setItem(13, null);
            setItem(22, GUIUtils.createGuiItem("§eChange Material", Material.RED_TERRACOTTA, "§7Place an item in the above slot", "§7to change this compressor's base", "§7item texture to that item!"));
        } else {
            setItem(13, new ItemStack(materialInSlot), player -> {
                materialInSlot = null;
                refreshGUI();
            });
            setItem(22, GUIUtils.createGuiItem("§aConfirm", Material.GREEN_TERRACOTTA, "§7This will change the compressor's", "§7material to this item.", "§7This is not permanent until you", "§7\"Save and Apply\" on the edit screen."), player -> {
                editGUI.setMaterial(materialInSlot);
                close(player);
                editGUI.open(player);
            });
        }
    }

    public void getNewMaterial(Material material) {
        materialInSlot = material;
        refreshGUI();
    }

}
