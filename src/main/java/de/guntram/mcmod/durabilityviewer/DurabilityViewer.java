package de.guntram.mcmod.durabilityviewer;

import de.guntram.mcmod.durabilityviewer.client.gui.GuiItemDurability;
import de.guntram.mcmod.durabilityviewer.config.Configs;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;

public class DurabilityViewer implements ClientModInitializer {
    public static final String MODID = "durabilityviewer";
    public static final String MODNAME = "Durability Viewer";

    public static DurabilityViewer instance;
    private static String changedWindowTitle;
    private KeyMapping showHide;
    public static final Logger LOGGER = LogManager.getLogger("DurabilityViewer");
    public static KeyMapping.Category DurabilityViewerCat = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("durabilityviewer","keys"));

    @Override
    public void onInitializeClient() {
        setKeyBindings();
        changedWindowTitle = null;

        Configs.loadFromFile();

        new Events().init(); //init Fabric Events
    }

    public static void setWindowTitle(String s) {
        changedWindowTitle = s;
    }

    public static String getWindowTitle() {
        return changedWindowTitle;
    }

    public void processKeyBinds() {
        if (showHide.consumeClick()) {
            GuiItemDurability.toggleVisibility();
        }
    }

    public void setKeyBindings() {
        KeyMappingHelper.registerKeyMapping(showHide = new KeyMapping("key.durabilityviewer.showhide", InputConstants.Type.KEYSYM, GLFW_KEY_H, DurabilityViewerCat));
        ClientTickEvents.END_CLIENT_TICK.register(e -> processKeyBinds());
    }
}
