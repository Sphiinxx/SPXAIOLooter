package scripts.spxaiolooter;

import com.allatori.annotations.DoNotRename;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Player;
import scripts.generalapi.PostRequest;
import scripts.spxaiolooter.data.Vars;
import scripts.tribotapi.Client;
import scripts.tribotapi.game.worldswitcher.enums.SwitcherWorldType;
import scripts.tribotapi.gui.AbstractGUIController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import scripts.tribotapi.util.Utility;

/**
 * Created by Sphiinx on 10/3/2016.
 */
@DoNotRename
public class GUIController extends AbstractGUIController {

    @FXML
    @DoNotRename
    private ResourceBundle resources;

    @FXML
    @DoNotRename
    private URL location;

    @FXML
    @DoNotRename
    private TextField item_names;

    @FXML
    @DoNotRename
    private ComboBox<SwitcherWorldType> world_type;

    @FXML
    @DoNotRename
    private CheckBox world_hop;

    @FXML
    @DoNotRename
    private Spinner<Integer> max_players_in_area;

    @FXML
    @DoNotRename
    private Spinner<Integer> looting_radius;

    @FXML
    @DoNotRename
    private TextArea bug_clientdebug;

    @FXML
    @DoNotRename
    private TextArea bug_stacktrace;

    @FXML
    @DoNotRename
    private TextArea bug_description;

    @FXML
    @DoNotRename
    private TextArea bug_botdebug;

    @FXML
    @DoNotRename
    private Button send_report;

    @FXML
    @DoNotRename
    private Label report_sent;

    @FXML
    @DoNotRename
    private Label report_spam;

    @FXML
    @DoNotRename
    private Hyperlink join_discord;

    @FXML
    @DoNotRename
    private Hyperlink add_skype;

    @FXML
    @DoNotRename
    private Hyperlink private_message;

    @FXML
    @DoNotRename
    private Hyperlink website_link;

    @FXML
    @DoNotRename
    private Button start;

    @FXML
    @DoNotRename
    void initialize() {
        world_type.getItems().setAll(SwitcherWorldType.values());
        world_type.getSelectionModel().select(0);

        join_discord.setOnAction((event) -> Utility.openURL("https://discordapp.com/invite/0yCbdv5qTOWmxUD5"));

        add_skype.setOnAction((event -> Utility.openURL("http://hatscripts.com/addskype/?sphiin.x")));

        private_message.setOnAction((event -> Utility.openURL("https://tribot.org/forums/profile/176138-sphiinx/")));

        website_link.setOnAction((event -> Utility.openURL("http://spxscripts.com/")));

        send_report.setOnAction((event) -> {
            if (PostRequest.LAST_SENT_TIME <= 0 || Timing.timeFromMark(PostRequest.LAST_SENT_TIME) > 60000) {
                if (!PostRequest.sendReportData(Client.getManifest(Main.class).name(), Client.getManifest(Main.class).version(), bug_description.getText(), bug_stacktrace.getText(), bug_clientdebug.getText(), bug_botdebug.getText()))
                    report_sent.setText("UH OH! There seems to have been an error with your report!");

                report_sent.setOpacity(1);
                report_spam.setOpacity(0);
                PostRequest.LAST_SENT_TIME = Timing.currentTimeMillis();
            } else {
                report_sent.setOpacity(0);
                report_spam.setOpacity(1);
            }
        });

        looting_radius.valueProperty().addListener((observable, oldValue, newValue) -> {
            Vars.get().looting_radius = looting_radius.getValue();
            Vars.get().loot_radius_center_tile = Player.getPosition();
        });

        start.setOnAction((event) -> {
            Vars.get().item_names = item_names.getText().split(", ");
            Vars.get().looting_radius = looting_radius.getValue();
            Vars.get().max_players_in_area = max_players_in_area.getValue();
            Vars.get().loot_radius_center_tile = Player.getPosition();
            Vars.get().world_type = world_type.getSelectionModel().getSelectedItem();
            Vars.get().world_hop = world_hop.isSelected();
            getGUI().close();
        });
    }
}

