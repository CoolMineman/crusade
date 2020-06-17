package io.github.CoolMineman.crusade.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin {
	//public Double[] speedBuffer = new Double[]{0.0D, 0.0D, 0.0D, 0.0D, 0.0D};

	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		//System.out.println("This line is printed by an example mod mixin!");
		//System.out.println(speedBuffer);
	}
}
