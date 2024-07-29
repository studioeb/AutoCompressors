package io.github.rainpaw.autocompressors.guis;

import io.github.rainpaw.autocompressors.items.ModifiableCompressor;
import io.github.rainpaw.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialEnterGUI extends BaseGUI {

    private final CompressorEditGUI editGUI;
    private Material materialInSlot = null;
    private final ModifiableCompressor compressor;

    public MaterialEnterGUI(CompressorEditGUI editGUI, ModifiableCompressor compressor) {
        super(45, "Change Material", GUIUtils.GUIType.MATERIAL_ENTER);

        this.editGUI = editGUI;
        this.compressor = compressor;

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
                compressor.setMaterial(materialInSlot);
                editGUI.refreshGUI();
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
