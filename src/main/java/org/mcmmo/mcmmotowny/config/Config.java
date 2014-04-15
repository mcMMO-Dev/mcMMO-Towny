package org.mcmmo.mcmmotowny.config;

public class Config extends ConfigLoader {
    private static Config instance;

    private Config() {
        super("config.yml");
        validate();
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }

        return instance;
    }

    @Override
    protected boolean validateKeys() {
        return true;
    }

    @Override
    protected void loadKeys() {}

    /* GENERAL */
    public boolean getStatsTrackingEnabled() { return config.getBoolean("General.Stats_Tracking", true); }
    public boolean getUpdateCheckEnabled() { return config.getBoolean("General.Update_Check", true); }
    public boolean getPreferBeta() { return config.getBoolean("General.Prefer_Beta", false); }

    /* TOWNY */
    public double getExperienceModifierGlobal() { return config.getDouble("Experience.Global_Modifier", 1.0); }
    public double getExperienceModifierTown(String townName) { return config.getDouble("Experience.Towns." + townName, 1.0); }
}
