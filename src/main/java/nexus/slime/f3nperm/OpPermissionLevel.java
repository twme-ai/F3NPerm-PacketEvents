package nexus.slime.f3nperm;

public enum OpPermissionLevel {
    NO_PERMISSIONS(0),
    ACCESS_SPAWN(1),
    WORLD_COMMANDS(2),
    PLAYER_COMMANDS(3),
    ADMIN_COMMANDS(4);

    private final int level;

    OpPermissionLevel(int level) {
        this.level = level;
    }

    public int toStatus() {
        final int baseOffset = 24;
        return level + baseOffset;
    }

    public int getLevel() {
        return level;
    }

    private static final OpPermissionLevel[] values = values();

    public static OpPermissionLevel fromLevel(int level) {
        for (OpPermissionLevel value : values) {
            if (value.getLevel() == level) {
                return value;
            }
        }

        return null;
    }

    public static OpPermissionLevel fromStatus(int status) {
        for (OpPermissionLevel value : values) {
            if (value.toStatus() == status) {
                return value;
            }
        }

        return null;
    }
}
