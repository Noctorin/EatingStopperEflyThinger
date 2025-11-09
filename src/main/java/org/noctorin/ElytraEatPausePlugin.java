package org.noctorin;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class ElytraEatPausePlugin extends Plugin {
    @Override
    public void onLoad() {
        getLogger().info(getName() + " loaded!");
        RusherHackAPI.getModuleManager().registerFeature(new ElytraEatPauseModule());
    }

    @Override
    public void onUnload() {
        getLogger().info(getName() + " unloaded!");
    }

    public String getName() {
        return "ElytraEatPausePlugin";
    }
}

