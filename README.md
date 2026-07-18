# F3NPerm PacketEvents

![F3NPerm logo](assets/Logo.png)

F3NPerm controls access to the Minecraft client hotkeys unlocked by the
operator permission-level entity status packet, including F3 + N. This fork
replaces the original ProtocolLib and version-specific NMS/Netty providers with
PacketEvents and handles F3 + F4 game mode changes for authorized players.

## Requirements

- Paper 1.21.11
- Java 21 or newer
- PacketEvents 2.13.0 or newer
- LuckPerms 5.5 (optional, for immediate permission recalculation updates)

This branch intentionally targets Paper 1.21.11. It does not retain the
original project's compatibility layer for older Bukkit, Spigot, or Paper
versions.

## Installation

1. Install Paper 1.21.11.
2. Install PacketEvents 2.13.0 or newer in the server's `plugins` directory.
3. Place `F3NPerm-4.0.1.jar` in the `plugins` directory.
4. Restart the server.

PacketEvents is a required external plugin and is not bundled in the F3NPerm
jar.

## Permissions

- `f3nperm.use`: allows the configured client hotkeys when permission checks
  are enabled.
- `f3nperm.admin`: allows `/f3nperm reload` and `/f3nperm forceupdate`.
- `f3nperm.*`: grants both permissions.

With `enable-permission-check: false` (the default), every player receives the
configured permission level and may use F3 + F4. Set it to `true` to require
`f3nperm.use` or operator status. The configured `op-permission-level` must be
at least `2` for the client game mode switcher.

## Building

```bash
./gradlew build
```

The plugin jar is written to `build/libs/F3NPerm-4.0.1.jar`.

## Provenance

This repository is derived from
[SlimeNexus/F3NPerm](https://github.com/SlimeNexus/F3NPerm), version 3.6.1.
The original Git history is preserved and the `upstream` remote points to the
source repository.

## License

This derivative project is distributed under the GNU General Public License
version 3 only (`GPL-3.0-only`). See [LICENSE](LICENSE).

The original F3NPerm source was released under the MIT License. Its complete
copyright and permission notice is preserved in [LICENSES/MIT.txt](LICENSES/MIT.txt).
See [NOTICE](NOTICE) for attribution and dependency details.
