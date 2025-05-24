package com.stoneveil.initguard;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = InitGuard.MODID)
public class InvisibilityHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!Config.enableMod) return;

        InitGuard.INVISIBLE_PLAYERS.add(event.getEntity().getUUID());
        LOGGER.info("Granted protection to {}", event.getEntity().getName().getString());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !Config.enableMod) return;
        Player player = event.player;
        if (!InitGuard.INVISIBLE_PLAYERS.contains(player.getUUID())) return;

        boolean moved = Config.checkMovement && (player.zza != 0 || player.xxa != 0);
        boolean timedOut = Config.checkTickCount && Config.tickCountThreshold < player.tickCount;

        if (moved || timedOut) {
            removeGuard(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        if (!Config.enableMod) return;
        removeGuard(event.getEntity());

    }

    public static void removeGuard(Player playerobj) {
        if (!Config.enableMod) return;

        UUID uuid = playerobj.getUUID();
        if (!InitGuard.INVISIBLE_PLAYERS.contains(uuid)) return;

        InitGuard.INVISIBLE_PLAYERS.remove(uuid);
        LOGGER.info("Guard removed for player: {}", playerobj.getDisplayName().getString());

        if(Config.notifyOnGuardRemoval){
        ServerPlayer player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(uuid);
        if (player != null) {
            player.sendSystemMessage(
                    Component.literal("[")
                            .append(Component.literal("InitGuard").withStyle(ChatFormatting.AQUA))
                            .append("] ")
                            .append(Component.literal("You are no longer protected.").withStyle(ChatFormatting.WHITE))
            );
        }

        }
    }

    @SubscribeEvent
    public static void onMobTarget(LivingChangeTargetEvent event) {
        if (!Config.enableMod) return;

        LivingEntity newTarget = event.getNewTarget();

        if (Config.checkMobIgnore && newTarget instanceof Player player &&
                InitGuard.INVISIBLE_PLAYERS.contains(player.getUUID())) {
            event.setCanceled(true);
        }
    }
/**/
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player) || Config.enableMod) return;
        if (Config.checkInvincible && InitGuard.INVISIBLE_PLAYERS.contains(player.getUUID())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingTarget(LivingChangeTargetEvent event) {
        if (!Config.enableMod) return;
        if (Config.checkMovement && event.getNewTarget() instanceof Player player &&
                InitGuard.INVISIBLE_PLAYERS.contains(player.getUUID())) {
            event.setCanceled(true);
        }
    }
}
