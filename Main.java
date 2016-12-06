package scripts.spxaiolooter;

import org.tribot.api2007.types.RSArea;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import scripts.spxaiolooter.data.Vars;
import scripts.spxaiolooter.tasks.DepositItems;
import scripts.spxaiolooter.tasks.PickupItem;
import scripts.spxaiolooter.tasks.SwitchWorlds;
import scripts.spxaiolooter.tasks.WalkToLootLocation;
import scripts.task_framework.framework.Task;
import scripts.tribotapi.AbstractScript;
import scripts.tribotapi.game.utiity.Utility07;
import scripts.tribotapi.gui.GUI;
import scripts.tribotapi.painting.paint.Calculations;
import scripts.tribotapi.painting.paint.enums.DataPosition;
import scripts.tribotapi.painting.projection.ProjectionManager;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sphiinx on 10/3/2016.
 */
@ScriptManifest(authors = "Shpiinx", category = "Money making", name = "[SPX] AIO Looter", version = 0.2)
public class Main extends AbstractScript implements Painting, MousePainting, MouseSplinePainting, EventBlockingOverride, MessageListening07, Ending {

    private ProjectionManager projection_manager = new ProjectionManager();

    @Override
    protected GUI getGUI() {
        try {
            return new GUI(new URL("http://www.spxscripts.com/spxaiolooter/GUI.fxml"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        Vars.reset();
        super.run();
    }

    @Override
    public void addTasks(Task... tasks) {
        super.addTasks(new DepositItems(), new WalkToLootLocation(), new SwitchWorlds(), new PickupItem());
    }

    @Override
    public void onPaint(Graphics g) {
        super.onPaint(g);
        paint_manager.drawGeneralData("Looted: ", Vars.get().items_looted + Calculations.getPerHour(Vars.get().items_looted, this.getRunningTime()), DataPosition.TWO, g);
        paint_manager.drawGeneralData("Status: ", task_manager.getStatus() + Utility07.getLoadingPeriods(), DataPosition.THREE, g);

        if (gui.isOpen()) {
            if (Vars.get().loot_radius_center_tile == null)
                return;

            final RSArea radius_area = new RSArea(Vars.get().loot_radius_center_tile, Vars.get().looting_radius);
            projection_manager.drawArea(radius_area, g);
            projection_manager.drawMinimapArea(Vars.get().loot_radius_center_tile, Vars.get().looting_radius, g);
        }
    }

    @Override
    public void serverMessageReceived(String s) {

    }

    @Override
    public void playerMessageReceived(String s, String s1) {

    }

    @Override
    public void duelRequestReceived(String s, String s1) {

    }

    @Override
    public void clanMessageReceived(String s, String s1) {

    }

    @Override
    public void tradeRequestReceived(String s) {

    }

    @Override
    public void personalMessageReceived(String s, String s1) {

    }

    @Override
    public void onEnd() {
        SignatureData.sendSignatureData(this.getRunningTime() / 1000, Vars.get().items_looted);
        super.onEnd();
    }

}

