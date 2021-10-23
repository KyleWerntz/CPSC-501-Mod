package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ExampleClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ExampleMod.VAULT_SCREEN_HANDLER, VaultScreen::new);
    }
}