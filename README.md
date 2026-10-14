# ChainTrain

Link up to five minecarts with iron chains and drive the whole train from the front cart.

**Paper 26.3 only · Java 25 · Beta · Maintained by vladnow**

## How it works

1. Stop your carts and make sure every passenger has stepped out.
2. Hold an iron chain in your main hand. Right-click the front cart, then the cart behind it.
3. A successful coupling consumes one iron chain. The first click and rejected links consume nothing.
4. To extend the train, right-click its rear cart, then the next cart behind it. Five carts total is the limit.
5. Put the chain away and enter the front cart. Powered rails move the train; no furnace cart is needed.
6. Leave the front cart and the entire train stops, including on powered rails.

Sneak-right-click a stopped, empty cart with an iron chain to detach it. Broken couplings return their chains. Visible iron chains follow the carts through turns and slopes without a resource pack or client mod.

## Ordinary and experimental physics

Minecart Improvements is optional. ChainTrain automatically keeps the world's native physics: ordinary carts retain their standard speed, while experimental carts follow the world's minecart speed rule, including a setting of 40 blocks per second. The plugin does not enable experiments or change gamerules.

Couple carts on the same connected rail line, with adjacent centres 1–4 blocks apart along the track. Branches, loops and trains longer than five carts are rejected. Trains travel front-first; reverse the coupling order to change direction. Couplings persist across restarts. Portals and external teleports are blocked while carts are coupled; detach them before moving between worlds.

## Install

Stop the server, place `ChainTrain-0.3.0-paper26.3-beta.jar` in `plugins`, and start the server. When upgrading, remove the previous ChainTrain JAR from `plugins` while preserving the `plugins/ChainTrain` folder.

Requires **Paper for Minecraft 26.3 and Java 25**. No additional plugin dependencies. Other Minecraft versions, Spigot, Bukkit, Purpur and Folia are not claimed as supported. This plugin uses version-specific Paper internals.

## Commands and permissions

| Command | Purpose |
|---|---|
| `/ct` or `/chaintrain` | Show coupling help |
| `/ct reset` | Recheck a train's track after a safety stop; look at a coupled cart |
| `/ct reload` | Reload configuration |

`chaintrain.use` is enabled for everyone by default. `chaintrain.admin` defaults to operators. In-game messages are currently in Russian; command names are ASCII.

The default configuration permits five carts, a 4-block linking distance, a minimum 1-block centre gap and a 30-second selection timeout. Chains are consumed in Creative by default. Set `visual-chains: false` to hide the models without removing couplings.

## Beta status

Validated with **17 automated tests and 3,694 real-Paper server simulation checks** across ordinary and experimental worlds, including curves, slopes, braking, chain models and full restarts. Drivers in these tests were artificial server-side players. Real-client gameplay, network smoothness, specific protection plugins, special cargo carts and large-server load remain unverified. Use a separate test world before adding it to a production server.

## Source, support and license

Maintainer: **vladnow**. MIT license. Project repository: [vladnow/ChainTrain](https://github.com/vladnow/ChainTrain). Report reproducible problems in [GitHub Issues](https://github.com/vladnow/ChainTrain/issues). These links become available when the prepared repository is published. No telemetry, external AI service or additional client installation is required by the plugin.

**Development disclosure:** the implementation and documentation were largely generated with AI from user-provided requirements and subsequently validated with automated tests and real Paper server simulations. Promotional artwork is an illustration, not a gameplay screenshot.

This is an unofficial Minecraft plugin. It is not affiliated with Mojang, Microsoft or PaperMC.

## Configuration and build

See [configuration](docs/CONFIGURATION.md), [build instructions](docs/BUILDING.md),
[validation and limitations](TESTING.md), and [changes](CHANGELOG.md).
The runtime artifact contains only ChainTrain code. Test servers and Paper dependencies are not redistributed.

The source archive preserves the tested Java implementation and resources. Public packaging adds English documentation,
Gradle Wrapper license notices and safeguards to the optional disposable-server test launcher.
The published runtime JAR remains the byte-identical tested 0.3.0 beta build.
