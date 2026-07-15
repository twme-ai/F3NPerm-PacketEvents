package nexus.slime.f3nperm;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public final class F3NPermCommand implements CommandExecutor, TabCompleter {
    private final F3NPermPlugin plugin;

    public F3NPermCommand(F3NPermPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPlugin();
            sender.sendMessage(Component.text("Plugin successfully reloaded!", NamedTextColor.GREEN));
            return true;
        }

        if (args[0].equalsIgnoreCase("forceupdate")) {
            if (args.length < 2) {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    plugin.getProvider().update(player);
                }

                sender.sendMessage(Component.text("Updated all online players", NamedTextColor.GREEN));
                return true;
            }

            Player target = plugin.getServer().getPlayerExact(args[1]);

            if (target == null) {
                sender.sendMessage(Component.text(
                        "A player named " + args[1] + " was not found!",
                        NamedTextColor.RED
                ));
                return true;
            }

            plugin.getProvider().update(target);
            sender.sendMessage(Component.text("Updated player " + args[1], NamedTextColor.GREEN));
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> candidates = null;

        if (args.length == 1) {
            candidates = List.of("reload", "forceupdate");
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("forceupdate")) {
            candidates = plugin.getServer().getOnlinePlayers().stream()
                    .map(Player::getName)
                    .toList();
        }

        if (candidates == null) {
            return List.of();
        }

        return candidates.stream()
                .filter(c -> c.toLowerCase(Locale.ROOT)
                        .startsWith(args[args.length - 1].toLowerCase(Locale.ROOT)))
                .sorted()
                .toList();
    }
}
