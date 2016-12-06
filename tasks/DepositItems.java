package scripts.spxaiolooter.tasks;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.task_framework.framework.Task;
import scripts.tribotapi.game.banking.Banking07;
import scripts.tribotapi.game.timing.Timing07;

/**
 * Created by Sphiinx on 10/3/2016.
 */
public class DepositItems implements Task {


    @Override
    public boolean validate() {
        return Inventory.isFull();
    }

    @Override
    public void execute() {
        if (Banking07.isBankItemsLoaded()) {
            if (Banking.depositAll() > 0)
                Timing07.waitCondition(() -> !Inventory.isFull(), General.random(1500, 2000));
        } else {
            if (Banking07.isInBank()) {
                if (Banking.openBank())
                    Timing07.waitCondition(Banking07::isBankItemsLoaded, General.random(1500, 2000));
            } else {
                WebWalking.walkToBank(new Condition() {
                    @Override
                    public boolean active() {
                        return Banking07.isInBank();
                    }
                }, 250);
            }
        }
    }

    @Override
    public String toString() {
        return "Depositing items";
    }
}

