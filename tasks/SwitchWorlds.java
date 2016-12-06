package scripts.spxaiolooter.tasks;

import org.tribot.api.General;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSModel;
import scripts.generalapi.util.ArrayUtil;
import scripts.spxaiolooter.data.Vars;
import scripts.task_framework.framework.Task;
import scripts.tribotapi.game.area.Area07;
import scripts.tribotapi.game.game.Game07;
import scripts.tribotapi.game.grounditems.GroundItems07;
import scripts.tribotapi.game.player.Player07;
import scripts.tribotapi.game.timing.Timing07;
import scripts.tribotapi.game.worldswitcher.GetSwitcherWorlds07;
import scripts.tribotapi.game.worldswitcher.WorldSwitcher07;
/**
 * Created by Sphiinx on 10/3/2016.
 */
public class SwitchWorlds implements Task {

    private RSGroundItem item_to_pickup;

    @Override
    public boolean validate() {
        if (!Vars.get().world_hop)
            return false;

        if (!Game07.isInGame())
            return false;

        final RSModel player_model = Player.getRSPlayer().getModel();
        if (player_model == null)
            return false;

        item_to_pickup = GroundItems07.getGroundItem(new Filter<RSGroundItem>() {
            @Override
            public boolean accept(RSGroundItem ground_item) {
                return Vars.get().loot_radius_center_tile.distanceTo(ground_item) <= Vars.get().looting_radius;
            }
        });

        return Vars.get().max_players_in_area > 0 && Player.getPosition().distanceTo(Vars.get().loot_radius_center_tile) <= Vars.get().looting_radius && Area07.getPlayersInArea(Vars.get().looting_radius) > Vars.get().max_players_in_area || item_to_pickup == null;
    }

    @Override
    public void execute() {
        if (!GetSwitcherWorlds07.isWorldSwitcherOpen())
            if (GetSwitcherWorlds07.openWorldSwitcher())
                Timing07.waitCondition(GetSwitcherWorlds07::isWorldSwitcherOpen, General.random(1500, 2000));

        final int[] worlds = GetSwitcherWorlds07.getWorlds(Vars.get().world_type.getTextureID());
        if (worlds == null)
            return;

        final int world = ArrayUtil.getRandomInt(worlds);
        if (world <= 0)
            return;

        if (WorldSwitcher07.switchWorld(world))
            Timing07.waitCondition(() -> Player07.getCurrentWorld() == world, General.random(6500, 7000));
    }

    @Override
    public String toString() {
        return "Switching worlds";
    }
}

