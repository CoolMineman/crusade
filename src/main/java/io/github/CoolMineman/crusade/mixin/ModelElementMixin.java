package io.github.CoolMineman.crusade.mixin;

import com.google.gson.JsonObject;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.model.json.ModelElement;
import net.minecraft.client.util.math.Vector3f;

@Mixin(ModelElement.Deserializer.class)
public abstract class ModelElementMixin {
    @Shadow
    abstract Vector3f deserializeVec3f(JsonObject object, String name);

    @Inject(method = "net/minecraft/client/render/model/json/ModelElement$Deserializer.deserializeTo(Lcom/google/gson/JsonObject;)Lnet/minecraft/client/util/math/Vector3f;", at = @At("HEAD"), cancellable = true)
    private void deserializeTo(JsonObject object, CallbackInfoReturnable<Vector3f> cb) {
        cb.setReturnValue(deserializeVec3f(object, "to"));
    }

    @Inject(method = "net/minecraft/client/render/model/json/ModelElement$Deserializer.deserializeFrom(Lcom/google/gson/JsonObject;)Lnet/minecraft/client/util/math/Vector3f;", at = @At("HEAD"), cancellable = true)
    private void deserializeFrom(JsonObject object, CallbackInfoReturnable<Vector3f> cb) {
        cb.setReturnValue(deserializeVec3f(object, "from"));
    }
}