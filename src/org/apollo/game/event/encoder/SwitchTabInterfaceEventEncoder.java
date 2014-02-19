package org.apollo.game.event.encoder;

import org.apollo.game.event.EventEncoder;
import org.apollo.game.event.impl.SwitchTabInterfaceEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link EventEncoder} for the {@link SwitchTabInterfaceEvent}.
 * @author Graham
 */
public final class SwitchTabInterfaceEventEncoder extends EventEncoder<SwitchTabInterfaceEvent> {

    public SwitchTabInterfaceEventEncoder(Class<SwitchTabInterfaceEvent> clazz) {
        super(clazz);
    }

    @Override
    public GamePacket encode(SwitchTabInterfaceEvent event) {
        GamePacketBuilder builder = new GamePacketBuilder(71);
        builder.put(DataType.SHORT, event.getInterfaceId());
        builder.put(DataType.BYTE, DataTransformation.ADD, event.getTabId());
        return builder.toGamePacket();
    }

}
