package github.xqyzx3.discordachievements.blocksPlaced;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlocksPlacedProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<BlocksPlaced> BLOCKS_PLACED =
            CapabilityManager.get(new CapabilityToken<BlocksPlaced>() { });

    private BlocksPlaced blocksPlaced = null;
    private final LazyOptional<BlocksPlaced> optional = LazyOptional.of(this::createBlocksPlaced);

    private BlocksPlaced createBlocksPlaced() {
        if (this.blocksPlaced == null) {
            this.blocksPlaced = new BlocksPlaced();
        }
        return this.blocksPlaced;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == BLOCKS_PLACED) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createBlocksPlaced().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createBlocksPlaced().loadNBTData(nbt);
    }
}
