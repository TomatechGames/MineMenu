package dmillerw.menu.data.click;

import dmillerw.menu.network.PacketHandler;
import dmillerw.menu.network.packet.server.PacketUseItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;

public class ClickActionUseItem implements ClickAction.IClickAction {
    @Nonnull
    public final ItemStack stack;

    public ClickActionUseItem(@Nonnull ItemStack item) {
        this.stack = item;
    }

    @Override
    public ClickAction getClickAction() {
        return ClickAction.ITEM_USE;
    }

    @Override
    public void onClicked() {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);

            if (!stack.isEmpty() && this.stack.isItemEqual(stack)) {
                stack.useItemRightClick(player.world, player, Hand.MAIN_HAND);
                PacketHandler.CHANNEL.sendToServer(new PacketUseItem(i));
            }
        }
    }

    @Override
    public void onRemoved() {
    }
}