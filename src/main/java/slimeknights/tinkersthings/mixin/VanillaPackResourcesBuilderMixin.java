package slimeknights.tinkersthings.mixin;

import dev.gigaherz.jsonthings.JsonThings;
import net.minecraft.server.packs.VanillaPackResourcesBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.net.URL;

/** Lets vanilla locate the custom pack root inside Json Things' isolated mod classloader. */
@Mixin(VanillaPackResourcesBuilder.class)
public abstract class VanillaPackResourcesBuilderMixin {
  @Redirect(
    method = "lambda$static$1",
    at = @At(value = "INVOKE", target = "Ljava/lang/Class;getResource(Ljava/lang/String;)Ljava/net/URL;")
  )
  private static URL tinkersThings$findPackRoot(Class<?> owner, String path) {
    if ("/things/.mcassetsroot".equals(path)) {
      return JsonThings.class.getResource(path);
    }
    return owner.getResource(path);
  }
}
