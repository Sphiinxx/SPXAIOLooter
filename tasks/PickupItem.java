package scripts.spxaiolooter.tasks;

import org.tribot.api.General;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import scripts.generalapi.util.ArrayUtil;
import scripts.spxaiolooter.data.Vars;
import scripts.task_framework.framework.Task;
import scripts.tribotapi.game.grounditems.GroundItems07;
import scripts.tribotapi.game.inventory.Inventory07;
import scripts.tribotapi.game.timing.Timing07;
import scripts.tribotapi.util.Logging;

import java.util.Arrays;

/**
 * Created by Sphiinx on 10/3/2016.
 */
public class PickupItem implements Task {

    private RSGroundItem item_to_pickup;

    @Override
    public boolean validate() {
        item_to_pickup = GroundItems07.getGroundItem(new Filter<RSGroundItem>() {
            @Override
            public boolean accept(RSGroundItem ground_item) {
                final RSItemDefinition item_definition = ground_item.getDefinition();
                if (item_definition == null)
                    return false;

                final String item_name = item_definition.getName();
                if (item_name == null)
                    return false;

                return Arrays.asList(Vars.get().item_names).contains(item_name) && Vars.get().loot_radius_center_tile.distanceTo(ground_item) <= Vars.get().looting_radius;
            }
        });
        return item_to_pickup != null;
    }

    @Override
    public void execute() {
        if (item_to_pickup == null)
            return;

        final RSItem[] inventory_cache = Inventory.getAll();
        if (GroundItems07.pickUpGroundItem(item_to_pickup))
            if (Timing07.waitCondition(() -> Player.isMoving() || Inventory07.hasInventoryChanged(inventory_cache), General.random(4000, 4500)))
                Vars.get().items_looted++;
    }

    @Override
    public String toString() {
        final RSItemDefinition item_definition = item_to_pickup.getDefinition();
        if (item_definition == null)
            return "Picking up items";

        final String item_name = item_definition.getName();
        if (item_name == null)
            return "Picking up items";

        return "Picking up " + item_name;
    }
}

