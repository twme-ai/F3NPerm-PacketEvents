package nexus.slime.f3nperm;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Settings {
    public static Settings loadSettings(F3NPermPlugin plugin, Path dataFolder) throws IOException {
        Path configFile = dataFolder.resolve("config.yml");

        if (!Files.isRegularFile(configFile)) {
            try (InputStream config = plugin.getResource("config.yml")) {
                if (config == null) {
                    throw new IOException("Default configuration was not found in jar!");
                }

                Files.createDirectories(dataFolder);
                Files.copy(config, configFile);
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile.toFile());

        return new Settings(config);
    }

    private final OpPermissionLevel opPermissionLevel;
    private final boolean enablePermissionCheck;
    private final Set<String> hooks;

    private Settings(FileConfiguration config) throws IOException {
        int opPermissionLevelInt = config.getInt("op-permission-level", OpPermissionLevel.ADMIN_COMMANDS.getLevel());
        opPermissionLevel = OpPermissionLevel.fromLevel(opPermissionLevelInt);

        if (opPermissionLevel == null) {
            throw new IOException("OpPermissionLevel " + opPermissionLevelInt + " is not recognized!");
        }

        enablePermissionCheck = config.getBoolean("enable-permission-check", false);

        hooks = new HashSet<>(config.getStringList("hooks"));
    }

    public boolean isEnablePermissionCheck() {
        return enablePermissionCheck;
    }

    public OpPermissionLevel getOpPermissionLevel() {
        return opPermissionLevel;
    }

    public Set<String> getHooks() {
        return hooks;
    }
}
