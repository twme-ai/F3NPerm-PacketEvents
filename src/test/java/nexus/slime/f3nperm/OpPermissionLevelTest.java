package nexus.slime.f3nperm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void requiresPermissionLevelTwoForGameModeSwitcher() {
        assertFalse(OpPermissionLevel.NO_PERMISSIONS.allowsGameModeSwitcher());
        assertFalse(OpPermissionLevel.ACCESS_SPAWN.allowsGameModeSwitcher());
        assertTrue(OpPermissionLevel.WORLD_COMMANDS.allowsGameModeSwitcher());
        assertTrue(OpPermissionLevel.ADMIN_COMMANDS.allowsGameModeSwitcher());
    }
}
