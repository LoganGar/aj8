package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.impl.UpdateSlottedItemsEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.SlottedItem;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;

/**
 * An {@link EventEncoder} for the {@link UpdateSlottedItemsEvent}.
 * @author Graham
 */
public final class UpdateSlottedItemsEventEncoder extends EventEncoder<UpdateSlottedItemsEvent> {

    public UpdateSlottedItemsEventEncoder(Class<UpdateSlottedItemsEvent> clazz) {
        super(clazz);
    }

    @Override
    public GamePacket encode(UpdateSlottedItemsEvent event) {
        GamePacketBuilder builder = new GamePacketBuilder(34, PacketType.VARIABLE_SHORT);
        SlottedItem[] items = event.getSlottedItems();

        builder.put(DataType.SHORT, event.getInterfaceId());

        for (SlottedItem slottedItem : items) {
            builder.putSmart(slottedItem.getSlot());

            Item item = slottedItem.getItem();
            int id = item == null ? -1 : item.getId();
            int amount = item == null ? 0 : item.getAmount();

            builder.put(DataType.SHORT, id + 1);

            if (amount > 254) {
                builder.put(DataType.BYTE, 255);
                builder.put(DataType.INT, amount);
            } else {
                builder.put(DataType.BYTE, amount);
            }
        }

        return builder.toGamePacket();
    }

}
