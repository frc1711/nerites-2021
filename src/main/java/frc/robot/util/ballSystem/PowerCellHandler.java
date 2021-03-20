/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util.ballSystem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Managerial class responsible for maintaining information relating to the power cells and their locations within the
 * robot.
 *
 * @author Lou DeZeeuw (original)
 * @author Gabriel Seaver (adaptation)
 * Adapted from https://github.com/frc1711/Nerites/blob/master/src/main/java/frc/robot/helper_classes/BallHandler.java.
 */
public class PowerCellHandler {

    /**
     * The data structure responsible for internally managing the list of PowerCells.
     */
    private final ArrayList<PowerCell> powerCellList;

    /**
     * Initializes a new PowerCellHandler instance.
     */
    public PowerCellHandler() {

        powerCellList = new ArrayList<>();

    }

    /**
     * Introduces a new PowerCell to the robot via the intake.
     *
     * @param powerCell The PowerCell to add to the robot.
     */
    public void addPowerCell(PowerCell... powerCell) {

        powerCellList.addAll(Arrays.asList(powerCell));

    }

    /**
     * Removes the last power cell in the PowerCellHandler if such a power cell exists.
     */
    public void removeLastPowerCellHandled() {

        if (powerCellList.size() != 0) powerCellList.remove(powerCellList.size() - 1);

    }

    /**
     * Return the number of power cells currently being manipulated by the robot.
     *
     * @return The number of power cells currently being manipulated by the robot.
     */
    public int count() {

        return powerCellList.size();

    }

    /**
     * Returns the PowerCell that was most recently introduced to the robot via the intake.
     *
     * @return The PowerCell that was most recently introduced to the robot via the intake.
     */
    public PowerCell getLatestPowerCell() {

        return this.getLatestPowerCell(0);

    }

    /**
     * Returns the n-th latest PowerCell that was introduced to the robot via the intake.
     *
     * @return The n-th latest PowerCell that was introduced to the robot via the intake.
     */
    public PowerCell getLatestPowerCell(int n) {

        if (powerCellList.size() >= ++n) return powerCellList.get(powerCellList.size() - n);
        else return null;

    }

}
