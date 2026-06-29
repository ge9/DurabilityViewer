package de.guntram.mcmod.durabilityviewer.mixin;

import de.guntram.mcmod.durabilityviewer.client.gui.GuiItemDurability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.DeltaTracker;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public abstract class PotionEffectsMixin {

    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private static GuiItemDurability gui;

    @Inject(method = "extractEffects", at = @At("RETURN"))
    private void afterRenderStatusEffects(GuiGraphicsExtractor context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (gui == null)
            gui = new GuiItemDurability();
        gui.afterRenderStatusEffects(context, 0);
    }

    @Inject(method = "extractHotbarAndDecorations", at = @At(value = "RETURN", opcode = Opcodes.GETFIELD, args = {"log=false"}))
    private void beforeRenderDebugScreen(GuiGraphicsExtractor context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (gui == null)
            gui = new GuiItemDurability();
        gui.onRenderGameOverlayPost(context, 0);
    }
}
