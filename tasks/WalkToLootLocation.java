package scripts.spxaiolooter.tasks;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import scripts.spxaiolooter.data.Vars;
import scripts.task_framework.framework.Task;

/**
 * Created by Sphiinx on 10/3/2016.
 */
public class WalkToLootLocation implements Task {


    @Override
    public boolean validate() {
        return !Inventory.isFull() && Player.getPosition().distanceTo(Vars.get().loot_radius_center_tile) > Vars.get().looting_radius;
    }

    @Override
    public void execute() {
        WebWalking.walkTo(Vars.get().loot_radius_center_tile, new Condition() {
            @Override
            public boolean active() {
                return Player.getPosition().distanceTo(Vars.get().loot_radius_center_tile) <= Vars.get().looting_radius;
            }
        }, 250);
    }

    @Override
    public String toString() {
        return "Walking to loot location";
    }
}

