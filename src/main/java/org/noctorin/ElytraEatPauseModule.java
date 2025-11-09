package org.noctorin;

import net.minecraft.core.component.DataComponents;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.core.event.subscribe.Subscribe;

public class ElytraEatPauseModule extends ToggleableModule {
    private ToggleableModule elytraFly;
    private boolean wasEnabled;

    public ElytraEatPauseModule() {
        super("ElytraEatPause", "Pauses ElytraFly while eating", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEnable() {
        cacheElytraFly();
    }

    @Override
    public void onDisable() {
        if (elytraFly != null && wasEnabled && !elytraFly.isToggled()) {
            elytraFly.setToggled(true);
        }
        wasEnabled = false;
    }

    @Subscribe
    private void onUpdate(EventUpdate event) {
        if (mc.player == null) {
            return;
        }
        cacheElytraFly();
        if (elytraFly == null) {
            return;
        }
        boolean eating = false;
        if (mc.player.isUsingItem()) {
            var stack = mc.player.getUseItem();
            eating = !stack.isEmpty() && stack.get(DataComponents.FOOD) != null;
        }
        if (eating) {
            if (elytraFly.isToggled()) {
                elytraFly.setToggled(false);
                wasEnabled = true;
            }
        } else if (wasEnabled) {
            elytraFly.setToggled(true);
            wasEnabled = false;
        }
    }

    private void cacheElytraFly() {
        elytraFly = RusherHackAPI.getModuleManager().getFeature("ElytraFly")
                .filter(ToggleableModule.class::isInstance)
                .map(ToggleableModule.class::cast)
                .orElse(null);
    }
}

