# ChainTrain 0.3.0 beta validation

Validation date: **2026-10-14**. Java 25; real **Paper 26.3-169-46d4834**.

| Check | Result |
|---|---|
| Automated geometry/topology tests | 17 passed; 0 failed, 0 errors, 0 skipped |
| Experimental minecart server simulation | 1,935 checks passed, including a full restart |
| Ordinary minecart server simulation | 1,759 checks passed, including a full restart |
| Driver | Artificial ServerPlayer; no connected Minecraft client |
| Experimental speed gamerule | 40; native powered-rail transient peak 41.2 blocks/s |
| Ordinary observed speed | Native 8 blocks/s |

Sanitized completion markers:

```text
CHAINTRAIN_SMOKE_PASS checks=1935 topSpeed=41.200000000000045 mode=experimental simulated-player=true restart=true
CHAINTRAIN_SMOKE_PASS checks=1759 topSpeed=8.000000000000043 mode=ordinary simulated-player=true restart=true
```

Checks include iron-chain consumption, rejection of copper chains/sixth carts/disconnected parallel rails,
cancelled protection events, five-cart driving without a furnace cart, curves, slopes, passenger-in-tail behaviour,
immediate braking on driver exit, chain-model endpoints and cleanup, detaching/refunds, middle-cart removal,
UUID/order persistence and visual restoration after full restarts.

## Limits

Real-client visuals, smoothness, keyboard input, network latency, special cargo carts, detector/activator rails,
specific protection/minecart plugins and production load remain unverified. Separate journeys across unloaded chunks
and migration of an existing world between experimental and ordinary mode have not been tested.
The same coupling format is used in both modes, but that is not a complete world-migration test.
This remains a **beta**. Only Paper 26.3 is claimed; no Folia or other platform/version support is claimed.

## Repeating integration checks

The optional harness changes blocks/entities and starts and stops its server twice.
Prepare two separate, disposable Paper 26.3 servers. Never use a production server directory.
First prepare the server files with the correct experiment before generating the fresh test world:

| Mode | server.properties settings |
|---|---|
| Experimental | `level-name=chaintrain-test`; `initial-enabled-packs=vanilla,minecart_improvements` |
| Ordinary | `level-name=chaintrain-ordinary-test`; `initial-enabled-packs=vanilla` |

Name the Paper launcher `paper.jar`. Review/accept EULA manually and set up the server once so libraries and versions exist.
Build ChainTrain first. JDK 25's javac, java and jar must be available via JAVA_HOME or PATH.
In each disposable server root, explicitly create the required acknowledgement file:

```powershell
'ChainTrain smoke tests may replace blocks and stop this server.' | Set-Content -LiteralPath 'D:/chaintrain-test-server/.chaintrain-smoke-disposable' -Encoding utf8
```

Then, from the source repository:

```powershell
./verification/run-smoke.ps1 -ServerDirectory 'D:/chaintrain-test-server'
./verification/run-smoke.ps1 -ServerDirectory 'D:/chaintrain-ordinary-test-server' -Ordinary
```

Create the marker separately in the ordinary server directory too. Use separate empty worlds and keep the test servers stopped before invoking the script.
The launcher verifies both the marker text and expected test world name before creating test artifacts or replacing a ChainTrain JAR.
Harness outputs go to `verification/results/`; generated servers/classes/logs are ignored and are not part of the public source archive.
Per-tick check counts can vary slightly with trip timing; the final PASS marker and exit status are the important results.

The public test launcher adds portability and disposable-server guards to the original tested harness.
Those packaging safeguards were parsed and checked during publication preparation; the 3,694 checks above are the existing gameplay validation of the byte-identical release JAR.
