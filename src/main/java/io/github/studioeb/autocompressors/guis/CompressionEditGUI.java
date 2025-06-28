/*
GUI to edit a compression's features
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

import io.github.studioeb.autocompressors.AutoCompressors;
import io.github.studioeb.autocompressors.conversations.ItemAmountPrompt;
import io.github.studioeb.autocompressors.conversations.PromptCanceller;
import io.github.studioeb.autocompressors.items.Compression;
import io.github.studioeb.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.inventory.ItemStack;

public class CompressionEditGUI extends BaseGUI {

    private final CompressionViewGUI viewGUI;
    private final ConversationFactory cf;

    private final int index;

    private final GUIUtils.GUIMode guiMode;

    private ItemStack currentStartItem;
    private ItemStack currentFinalItem;
    private int currentStartItemAmount = 0;
    private int currentFinalItemAmount = 0;

    public CompressionEditGUI(CompressionViewGUI viewGUI, AutoCompressors plugin, Compression compression, int index, GUIUtils.GUIMode mode) {
        super(27, mode.equals(GUIUtils.GUIMode.EDIT) ? "Edit Compression" : "Create Compression", GUIUtils.GUIType.NORMAL);

        cf = new ConversationFactory(plugin);
        this.viewGUI = viewGUI;
        this.index = index;
        guiMode = mode;

        if (compression != null) {
            currentStartItem = compression.getStartItem();
            currentFinalItem = compression.getFinalItem();
            currentStartItemAmount = compression.getStartItemAmount();
            currentFinalItemAmount = compression.getFinalItemAmount();
        }

        refreshGUI();
    }

    public void refreshGUI() {
        GUIUtils.drawGrayGlassBorder(getInventory());
        setItem(21, GUIUtils.createGuiItem("§cBack", Material.ARROW, "§c§lWARNING: THIS WILL DELETE UNSAVED CHANGES!"), player -> {
            close(player);
            viewGUI.open(player);
        });

        if (areFieldsFull()) {
            setItem(23, GUIUtils.createGuiItem("§aSave", Material.GREEN_TERRACOTTA, "§7Saves your changes to this compression.", "§7You will still have to save the compressor", "§7itself on the edit screen!", "", "§aClick to save!"), player -> {
                if (guiMode.equals(GUIUtils.GUIMode.CREATE)) {
                    viewGUI.addCompression(new Compression(currentStartItem, currentFinalItem, currentStartItemAmount, currentFinalItemAmount, index));
                } else {
                    viewGUI.changeCompression(index, new Compression(currentStartItem, currentFinalItem, currentStartItemAmount, currentFinalItemAmount, index));
                }
                close(player);
                viewGUI.open(player);
            });
        } else {
            setItem(23, GUIUtils.createGuiItem("§cSave", Material.RED_TERRACOTTA, "§7Saves your changes to this compression.", "§7You will still have to save the compressor", "§7itself on the edit screen!", "", "§cAll fields must be filled before you", "§ccan save!"));
        }

        setItem(10, GUIUtils.getBgItem());
        setItem(11, GUIUtils.createGuiItem("§aStart Item", currentStartItem == null ? Material.WHITE_CONCRETE : currentStartItem.getType(), "§7The item that will be compressed into", "§7the new item.", "", "§7Current item: " + (currentStartItem == null ? "§cNone" : GUIUtils.getItemName(currentStartItem)), "", "§aClick to change!"), player -> {
            ItemInsertGUI gui = new ItemInsertGUI(this, "start");
            close(player);
            gui.open(player);
        });
        setItem(12, GUIUtils.createGuiItem("§aStart Item Amount", Material.OAK_SIGN, "§7The amount of the start item that will", "§7be compressed into the new item.", "", "§7Current amount: " + "§f" + currentStartItemAmount, "", "§aClick to change!"), player -> {
            cf.withFirstPrompt(new ItemAmountPrompt("start", player, this)).withLocalEcho(false).withConversationCanceller(new PromptCanceller(this, player)).buildConversation(player).begin();
            close(player);
        });
        setItem(13, GUIUtils.getBgItem());
        setItem(14, GUIUtils.createGuiItem("§aFinal Item", currentFinalItem == null ? Material.WHITE_CONCRETE : currentFinalItem.getType(), "§7The item that will be received from the", "§7compression.", "", "§7Current item: " + (currentFinalItem == null ? "§cNone" : GUIUtils.getItemName(currentFinalItem)), "", "§aClick to change!"), player -> {
            ItemInsertGUI gui = new ItemInsertGUI(this, "final");
            close(player);
            gui.open(player);
        });
        setItem(15, GUIUtils.createGuiItem("§aFinal Item Amount", Material.OAK_SIGN, "§7The amount of the final item that will be", "§7received when the compression occurs.", "", "§7Current amount: " + "§f" + currentFinalItemAmount, "", "§aClick to change!"), player -> {
            cf.withFirstPrompt(new ItemAmountPrompt("final", player, this)).withLocalEcho(false).withConversationCanceller(new PromptCanceller(this, player)).buildConversation(player).begin();
            close(player);
        });
        setItem(16, GUIUtils.getBgItem());
    }

    private boolean areFieldsFull() {
        return currentStartItem != null && currentFinalItem != null && currentStartItemAmount != 0 && currentFinalItemAmount != 0;
    }

    public void changeStartItem(ItemStack newItem) {
        currentStartItem = newItem;
        refreshGUI();
    }

    public void changeFinalItem(ItemStack newItem) {
        currentFinalItem = newItem;
        refreshGUI();
    }

    public void setStartItemAmount(int amount) {
        currentStartItemAmount = amount;
        refreshGUI();
    }

    public void setFinalItemAmount(int amount) {
        currentFinalItemAmount = amount;
        refreshGUI();
    }
}
