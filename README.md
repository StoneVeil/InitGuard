# InitGuard

**InitGuard** is a lightweight, configurable Minecraft Forge mod for **1.20.1** that ensures players are safe during the vulnerable login or spawn-in phase.  
In heavily modded servers, it is common for the client to connect to the server before rendering is finished or the world is fully loaded. This can result in a player being technically "in-game"—and therefore vulnerable—while still staring at a loading screen. During this time, mobs may attack or kill the player before they can react.  
**InitGuard prevents this** by giving new or respawning players temporary protection until they actively engage with the world.

## 🔐 Features

- 🛡️ **Automatic Protection on Join**  
  Players become invisible to mobs and immune to all damage immediately after logging in.

- 🧭 **Protection Ends When:**
  - The player moves (configurable)
  - A configurable amount of time has passed
  - The player interacts with the world

- ⚙️ **Fully Configurable**
  - Enable/disable the mod entirely
  - Choose whether to check for movement, tick timeout, or both
  - Set timeout duration (in ticks)
  - Decide whether mobs should ignore players
  - Toggle invincibility (block all damage types)
  - Optional player chat message when protection ends

- 💬 **User Feedback**
  Players can receive a chat message when protection ends.

## ⚙️ Configuration

The mod creates a `initguard.toml` config file in the `config/` folder. All behavior is configurable and explained with comments for ease of use.  
**Each configuration option is clearly documented in the file, so users know exactly what it controls.**

Example config options:
```toml
enable_mod = true
check_movement = true
check_tick_count = false
tick_count_threshold = 100
check_invincible = true
check_mobignore = true
notify_on_guard_removal = false
```

All Rights Reserved.
