package io.github.rainpaw.autocompressors.guis;

import io.github.rainpaw.autocompressors.utils.GUIUtils;
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
