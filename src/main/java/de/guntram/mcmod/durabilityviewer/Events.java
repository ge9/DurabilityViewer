package de.guntram.mcmod.durabilityviewer;

import de.guntram.mcmod.durabilityviewer.config.Configs;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

public class Events {

    public Events() {
    }

    public void init() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!Configs.Settings.SetWindowTitle.getBooleanValue())
                return;
            MinecraftClient mc = MinecraftClient.getInstance();
            ServerInfo serverData = mc.getCurrentServerEntry();
            String serverName;
            if (serverData == null)
                serverName = "local game";
            else
                serverName = serverData.name;
            if (serverName == null)
                serverName = "unknown server";
            DurabilityViewer.setWindowTitle(mc.getSession().getUsername() + " on " + serverName);
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (!Configs.Settings.SetWindowTitle.getBooleanValue())
                return;
            MinecraftClient mc = MinecraftClient.getInstance();
            DurabilityViewer.setWindowTitle(mc.getSession().getUsername() + " not connected");
        });
    }
}
