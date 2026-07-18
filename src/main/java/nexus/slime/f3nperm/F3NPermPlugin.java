package nexus.slime.f3nperm;

import nexus.slime.f3nperm.hooks.Hook;
import nexus.slime.f3nperm.hooks.LuckPermsHook;
import nexus.slime.f3nperm.provider.PacketEventsProvider;
import nexus.slime.f3nperm.provider.Provider;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public final class F3NPermPlugin extends JavaPlugin {
    private final Hook[] hooks = new Hook[] {
            new LuckPermsHook()
    };

    private Provider provider;
    private boolean providerRegistered;
    private Settings settings;
    private List<Hook> registeredHooks;

    @Override
    public void onLoad() {
        loadSettings();
        provider = new PacketEventsProvider();
    }

    @Override
    public void onEnable() {
        F3NPermCommand f3nPermCommand = new F3NPermCommand(this);
        PluginCommand command = Objects.requireNonNull(
                getCommand("f3nperm"),
                "f3nperm command is missing from plugin.yml"
        );
        command.setExecutor(f3nPermCommand);
        command.setTabCompleter(f3nPermCommand);

        getServer().getPluginManager().registerEvents(new F3NPermListener(this), this);

        provider.register(this);
        providerRegistered = true;

        loadHooks();

        getLogger().info("F3NPerm enabled with PacketEvents");
    }

    @Override
    public void onDisable() {
        if (registeredHooks != null) {
            for (Hook hook : registeredHooks) {
                hook.unregister(this);
            }
        }

        if (providerRegistered) {
            provider.unregister();
            providerRegistered = false;
        }
    }

    public void reloadPlugin() {
        for (Hook hook : registeredHooks) {
            hook.unregister(this);
        }

        loadSettings();
        loadHooks();

        for (Player player : getServer().getOnlinePlayers()) {
            provider.update(player);
        }
    }

    private void loadSettings() {
        try {
            settings = Settings.loadSettings(this, getDataFolder().toPath());
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Error loading configuration!", e);
            throw new IllegalStateException("Could not load F3NPerm configuration", e);
        }
    }

    private void loadHooks() {
        registeredHooks = new ArrayList<>();

        for (Hook hook : hooks) {
            if (getSettings().getHooks().contains(hook.getName())) {
                hook.register(this);
                registeredHooks.add(hook);
            }
        }
    }

    public OpPermissionLevel getF3NPermPermissionLevel(Player player) {
        if (!settings.isEnablePermissionCheck() ||
                player.hasPermission("f3nperm.use") ||
                player.hasPermission("F3NPerm.use") ||
                player.isOp()) {
            return settings.getOpPermissionLevel();
        }

        return OpPermissionLevel.NO_PERMISSIONS;
    }

    public boolean canUseGameModeSwitcher(Player player) {
        return getF3NPermPermissionLevel(player).allowsGameModeSwitcher();
    }

    public Provider getProvider() {
        return provider;
    }

    public Settings getSettings() {
        return settings;
    }
}
