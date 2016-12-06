package scripts.spxaiolooter.data;

import org.tribot.api2007.types.RSTile;
import scripts.tribotapi.game.worldswitcher.enums.SwitcherWorldType;

/**
 * Created by Sphiinx on 10/3/2016.
 */
public class Vars {

    public static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }

    public static void reset() {
        vars = null;
    }

    public boolean world_hop;

    public int looting_radius;
    public int items_looted;
    public int max_players_in_area;

    public String[] item_names;

    public RSTile loot_radius_center_tile;

    public SwitcherWorldType world_type;

}

