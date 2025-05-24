package com.stoneveil.initguard;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = InitGuard.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    //bool activate mod
    public static final ForgeConfigSpec.BooleanValue ENABLE_MOD = BUILDER
            .comment("""
            Master toggle for enabling or disabling the InitGuard mod.
            If disabled, all features of the mod (invisibility, protection, etc.)
            will be turned off and have no effect.
        """)
            .define("settings.enable_mod", true);


    //bool notify player
    public static final ForgeConfigSpec.BooleanValue NOTIFY_ON_GUARD_REMOVAL = BUILDER
            .comment("""
            If enabled, players will receive a chat message when their protection
            from mob targeting and damage is removed.
        """)
            .define("settings.notify_on_guard_removal", true);

    //bool check movement
    public static final ForgeConfigSpec.BooleanValue CHECK_MOVEMENT = BUILDER
            .comment("""
                If enabled, the player will lose protection
                as soon as they move (forward, backward, sideways or interaction).
                Disable this if you want movement to be ignored.
                
            """)
            .define("settings.check_movement", true);

    //bool check tick
    public static final ForgeConfigSpec.BooleanValue CHECK_TICK_COUNT = BUILDER
            .comment("""
            If enabled, protection ends after a set time, even without movement or interaction.
            Works independently of CHECK_MOVEMENT — whichever triggers first will remove protection.
        """)
            .define("settings.check_tick_count", false);

    //tick amount
    public static final ForgeConfigSpec.IntValue TICK_COUNT_THRESHOLD = BUILDER
            .comment("""
                Time in ticks before invisibility is automatically removed.
                20 ticks = 1 second. Default is 100 ticks (5 seconds).
                Only applies if 'check_tick_count' is enabled.
            """)
            .defineInRange("settings.tick_count_threshold", 100, 1, Integer.MAX_VALUE);

    //invincible
    public static final ForgeConfigSpec.BooleanValue CHECK_INVINCIBLE = BUILDER
            .comment("""
            If enabled, players are completely invincible and immune to all types of damage.
            Useful to grant full protection during the login phase or controlled scenarios.
            """)
            .define("settings.check_invincible", true);

    public static final ForgeConfigSpec.BooleanValue CHECK_MOBIGNORE = BUILDER
            .comment("""
        If enabled, mobs won't target players while they are protected.
        """)
            .define("settings.check_mobignore", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    // Cached config values for runtime access
    public static boolean enableMod;
    public static boolean notifyOnGuardRemoval;
    public static boolean checkMovement;
    public static boolean checkMobIgnore;
    public static boolean checkInvincible;
    public static boolean checkTickCount;
    public static int tickCountThreshold;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        enableMod = ENABLE_MOD.get();
        notifyOnGuardRemoval = NOTIFY_ON_GUARD_REMOVAL.get();
        checkMovement = CHECK_MOVEMENT.get();
        checkMobIgnore = CHECK_MOBIGNORE.get();
        checkInvincible = CHECK_INVINCIBLE.get();
        checkTickCount = CHECK_TICK_COUNT.get();
        tickCountThreshold = TICK_COUNT_THRESHOLD.get();
    }
}