package io.tebex.plugin.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.tebex.plugin.TebexPlugin;
import io.tebex.sdk.request.response.ServerInformation;
import io.tebex.sdk.util.LunarClientUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class EmbeddedCheckoutUtil {

    public static final String CHECKOUT_CHANNEL = "tbx:checkout";

    private EmbeddedCheckoutUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Attempts to open an embedded checkout within Lunar Client.
     *
     * @param platform The Tebex plugin instance
     * @param player The player to open the checkout for
     * @param packageId The package ID to open the checkout for
     * @return Whether the checkout was successfully opened
     */
    public static boolean attemptEmbeddedCheckout(TebexPlugin platform, Player player, int packageId) {
        return attemptEmbeddedCheckout(platform, player, new ArrayList<>(packageId));
    }

    /**
     * Attempts to open an embedded checkout within Lunar Client.
     *
     * @param platform The Tebex plugin instance
     * @param player The player to open the checkout for
     * @param packageIdsList The list of package IDs to open the checkout for
     * @return Whether the checkout was successfully opened
     */
    public static boolean attemptEmbeddedCheckout(TebexPlugin platform, Player player, List<Integer> packageIdsList) {
        UUID playerUuid = player.getUniqueId();
        ServerInformation.Store store = platform.getStoreInformation().getStore();

        // TODO: Remove usage of Apollo
        if (!LunarClientUtil.isLunarClientUser(playerUuid)) {
            return false;
        }

        // Collect package IDs
        JsonArray packageIds = new JsonArray();
        for (Integer id : packageIdsList) {
            packageIds.add(id);
        }

        // Construct JSON
        JsonObject json = new JsonObject();
        json.addProperty("serverName", store.getName());
        json.addProperty("serverDomain", store.getDomainWithoutProtocol());
        json.add("packageIds", packageIds);

        player.sendPluginMessage(platform, CHECKOUT_CHANNEL, json.toString().getBytes());
        return true;
    }
}
