/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util.ballSystem;

/**
 * Represents one physical power cell that can be manipulated by the robot.
 *
 * @author Lou DeZeeuw (original)
 * @author Gabriel Seaver (adaptation)
 * Adapted from https://github.com/frc1711/Nerites/blob/master/src/main/java/frc/robot/helper_classes/Ball.java.
 */
public class PowerCell {

    /**
     * The total count of created (initialized) power cells.
     */
    private static int count = 0;

    /**
     * The ID of this PowerCell.
     */
    private final int id;

    /**
     * Boolean value equal to 'true' if this PowerCell has moved beyond the middle sensor.
     */
    private boolean pastSensor;

    /**
     * Initializes a new PowerCell instance.
     */
    public PowerCell() {

        this.id = count++;
        this.pastSensor = false;

    }

    /**
     * Returns the ID of this PowerCell instance.
     *
     * @return The ID of this PowerCell instance.
     */
    public int getID() {

        return this.id;

    }

    /**
     * Returns true if this PowerCell is beyond the middle sensor.
     * @return true if this PowerCell is beyond the middle sensor.
     */
    public boolean isPastSensor() {

        return this.pastSensor;

    }

    /**
     * Sets whether or not this PowerCell is beyond the middle sensor.
     *
     * true indicates that this PowerCell is beyond the middle sensor, while false indicates that it is not.
     *
     * @param pastSensor whether or not this PowerCell is beyond the middle sensor.
     */
    public void setPastSensor(boolean pastSensor) {

        this.pastSensor = pastSensor;

    }

}
