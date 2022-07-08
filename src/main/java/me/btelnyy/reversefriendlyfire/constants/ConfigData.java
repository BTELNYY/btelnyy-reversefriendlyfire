package me.btelnyy.reversefriendlyfire.constants;

import me.btelnyy.reversefriendlyfire.service.file_manager.Configuration;

public class ConfigData {
    private static ConfigData instance;

    public boolean pluginEnabled = true;
    public String overridePermission = "btelnyy.friendlyfirereverse.override";

    public void load(Configuration config) {
        config.getBoolean("enable_plugin");
        config.getString("override_permission");
        instance = this;
    }
    public static ConfigData getInstance(){
        return instance;
    }
}
