package github.xqyzx3.discordachievements.blocksPlaced;

import net.minecraft.nbt.CompoundTag;

public class BlocksPlaced {
    private int blocksPlaced;

    public int getBlocksPlaced() {
        return blocksPlaced;
    }

    public void addBlocksPlaced(int add) {
        this.blocksPlaced += add;
    }

    public void copyFrom(BlocksPlaced source) {
        this.blocksPlaced = source.blocksPlaced;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("blocksPlaced", blocksPlaced);
    }

    public void loadNBTData(CompoundTag nbt) {
        blocksPlaced = nbt.getInt("blocksPlaced");
    }
}
