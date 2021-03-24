/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.util.PIDHelp;

/**
 * Subsystem class responsible for managing the shooter on the robot.
 *
 * @author Lou DeZeeuw
 * @author Gabriel Seaver
 * Adapted from https://github.com/frc1711/Nerites/blob/master/src/main/java/frc/robot/subsystems/Shooter.java
 */
public class Shooter extends SubsystemBase {

    /**
     * The TalonSRX motor controller responsible for controlling the left shooter motor.
     */
    private final WPI_TalonSRX shooterTalonLeft;

    /**
     * The TalonSRX motor controller responsible for controlling the right shooter motor.
     */
    private final WPI_TalonSRX shooterTalonRight;

    /**
     * A semantic TalonSRX motor controller responsible for controlling both shooter motors.
     *
     * This is internally equivalent to Shooter#shooterTalonLeft, but semantically, it is the master controller for the
     * shooter motors.
     */
    private final WPI_TalonSRX shooterTalons;

    /**
     * The TalonSRX motor controller responsible for controlling the kicker wheels.
     */
    private final WPI_TalonSRX kicker;

    /**
     * Initializes a new Shooter instance.
     */
    public Shooter() {

        shooterTalonLeft = new WPI_TalonSRX(Constants.shooterLeft);
        shooterTalonRight = new WPI_TalonSRX(Constants.shooterRight);
        shooterTalons = shooterTalonLeft;
        kicker = new WPI_TalonSRX(Constants.flyWheel);

        shooterTalonLeft.setSafetyEnabled(false);
        shooterTalonRight.setSafetyEnabled(false);

        shooterTalonRight.setInverted(true);
        shooterTalonRight.set(ControlMode.Follower, Constants.shooterLeft);

    }

    /**
     * Sets the shooter motors to spin at the specified speed.
     *
     * @param speed
     */
    public void shoot(double speed) {

        PIDHelp.toVelocity(shooterTalonLeft, speed);

    }

    /**
     * Stops the shooter motors.
     */
    public void stopShooter() {

        shooterTalons.set(0);

    }

    /**
     * Runs the kicker motor.
     */
    public void runKicker() {

        kicker.set(Constants.flyWheelSpeed);

    }

    /**
     * Stops the kicker motor.
     */
    public void stopKicker() {

        kicker.set(0);

    }

}
