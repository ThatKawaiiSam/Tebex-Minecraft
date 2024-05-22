package io.tebex.sdk.util;

import com.lunarclient.apollo.Apollo;

import java.util.UUID;

public final class LunarClientUtil {

    private LunarClientUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Check if a player is using Lunar Client
     * @param uuid The player's UUID
     * @return If the player is using Lunar Client
     */
    public static boolean isLunarClientUser(UUID uuid) {
        try {
            // TODO: Utalize an "Apollo-less" implementation by maintaining a list
            //       of users who have succcessfully registered within the custom
            //       protocol.
            return Apollo.getPlayerManager().hasSupport(uuid);
        } catch (Exception e) {
            // This will throw an exception if Apollo is not ready or not present
            return false;
        }
    }

}
