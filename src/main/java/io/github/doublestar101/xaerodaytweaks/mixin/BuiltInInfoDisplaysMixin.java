package io.github.doublestar101.xaerodaytweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import xaero.hud.minimap.info.BuiltInInfoDisplays;

@Mixin(BuiltInInfoDisplays.class)
public class BuiltInInfoDisplaysMixin {

    // Changes Xaero's day calculation from:
    // (int)(totalTime / 24000L) + 1
    // to:
    // (int)(totalTime / 24000L)
    // ordinal = 0 refers to the first ICONST_1 in lambda$static$14 for
    // Xaero Minimap 26.1.3 (MC 26.2).

    @ModifyConstant(

            method = "lambda$static$14(Lxaero/hud/minimap/info/InfoDisplay;Lxaero/hud/minimap/info/render/compile/InfoDisplayCompiler;Lxaero/hud/minimap/module/MinimapSession;ILnet/minecraft/core/BlockPos;)V",
            constant = @Constant(intValue = 1, ordinal = 0)
    )
    private static int adjustDayOffset(int constant) {
        return 0;
    }
}