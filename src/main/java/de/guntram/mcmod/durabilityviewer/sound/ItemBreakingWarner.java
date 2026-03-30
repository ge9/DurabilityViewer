/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.guntram.mcmod.durabilityviewer.sound;

import de.guntram.mcmod.durabilityviewer.DurabilityViewer;
import de.guntram.mcmod.durabilityviewer.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;


/**
 * @author gbl
 */
public class ItemBreakingWarner {
    private int lastDurability;
    private ItemStack lastStack;
    private static SoundEvent sound;

    public ItemBreakingWarner() {
        lastDurability = 1000;
        lastStack = null;
        Identifier location;

        if (sound == null) {
            location = Identifier.fromNamespaceAndPath(DurabilityViewer.MODID, "tool_breaking");
            sound = SoundEvent.createVariableRangeEvent(location);
        }
    }

    public boolean checkBreaks(ItemStack stack) {
        lastStack = stack;
        if (stack == null || !stack.isDamageableItem())
            return false;
        int newDurability = stack.getMaxDamage() - stack.getDamageValue();
        if (newDurability < lastDurability
                && newDurability < Configs.Settings.SoundBelowDurability.getIntegerValue()
                && newDurability * 100 / Configs.Settings.SoundBelowPercent.getIntegerValue() < stack.getMaxDamage()) {
            lastDurability = newDurability;
            return true;
        }
        lastDurability = newDurability;
        return false;
    }

    public static void playWarningSound() {
        Minecraft.getInstance().player.playSound(sound, 100, 100);
    }
}
