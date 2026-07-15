package nexus.slime.f3nperm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OpPermissionLevelTest {
    @Test
    void mapsPermissionLevelsToEntityStatuses() {
        assertEquals(24, OpPermissionLevel.NO_PERMISSIONS.toStatus());
        assertEquals(28, OpPermissionLevel.ADMIN_COMMANDS.toStatus());
    }

    @Test
    void resolvesKnownPermissionLevelsAndStatuses() {
        assertEquals(OpPermissionLevel.PLAYER_COMMANDS, OpPermissionLevel.fromLevel(3));
        assertEquals(OpPermissionLevel.PLAYER_COMMANDS, OpPermissionLevel.fromStatus(27));
    }

    @Test
    void rejectsUnknownPermissionLevelsAndStatuses() {
        assertNull(OpPermissionLevel.fromLevel(5));
        assertNull(OpPermissionLevel.fromStatus(29));
    }
}
