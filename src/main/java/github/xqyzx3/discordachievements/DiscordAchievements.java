package github.xqyzx3.discordachievements;


import github.xqyzx3.discordachievements.blocksPlaced.BlocksPlaced;
import github.xqyzx3.discordachievements.blocksPlaced.BlocksPlacedProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(DiscordAchievements.MODID)
public class DiscordAchievements {
    public static final String MODID = "discordachievements";

    @Mod.EventBusSubscriber(modid = DiscordAchievements.MODID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void blockPlaced(BlockEvent.EntityPlaceEvent e) {
            if (e.getEntity() instanceof Player player) {
                player.getCapability(BlocksPlacedProvider.BLOCKS_PLACED).ifPresent(blocksPlaced -> {
                    blocksPlaced.addBlocksPlaced(1);
                    player.sendSystemMessage(Component.literal(player.getName().getString() + " placed" + blocksPlaced.getBlocksPlaced() + " block!"));
                });

            }
        }

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> e) {
            if (e.getObject() instanceof Player) {
                if (!e.getObject().getCapability(BlocksPlacedProvider.BLOCKS_PLACED).isPresent()) {
                    e.addCapability(new ResourceLocation(DiscordAchievements.MODID, "properties"), new BlocksPlacedProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone e) {
            if(e.isWasDeath()) {
                e.getOriginal().getCapability(BlocksPlacedProvider.BLOCKS_PLACED).ifPresent(oldStore -> {
                    e.getOriginal().getCapability(BlocksPlacedProvider.BLOCKS_PLACED).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent e) {
            e.register(BlocksPlaced.class);
        }
    }
}
