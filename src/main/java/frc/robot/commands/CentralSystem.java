/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;
import frc.robot.util.ballSystem.PowerCell;
import frc.robot.util.ballSystem.PowerCellHandler;

/**
 * Adapted from https://github.com/frc1711/Nerites/blob/master/src/main/java/frc/robot/commands/CentralSystem.java.
 *
 * @author Lou DeZeeuw (original)
 * @author Gabriel Seaver (adaptation)
 */
public class CentralSystem extends CommandBase {

    private static final int
            shooterButton = 1,           // A
            flyWheelButton = 2,          // B
            pulleyButton = 3,            // X
            reversePulleyButton = 4,     // Y
            intakeButton = 5,            // Left bumper
            outtakeButton = 6,           // Right bumper
            changeShooterModeButton = 7, // (Left version of the menu button) OR back button
            manualToggleButton = 8;      // Menu button OR start button

    private final Pulley pulley;
    private final Shooter shooter;
    private final PowerCellHandler powerCellHandler;
    private final Intake intake;
    private final Joystick stick;

    private boolean pastMiddle;
    private boolean shootMode;
    private boolean hold;
    private boolean created;
    private boolean reverse;
    private boolean destroyed;
    private boolean manual;

    private int timeout;
    private int timeSinceStart;
    private int shooterSpeedIndex = 0;

    public CentralSystem(Pulley pulley, Shooter shooter, Intake intake, Joystick stick) {

        addRequirements(pulley, shooter, intake);

        this.pulley = pulley;
        this.intake = intake;
        this.shooter = shooter;
        this.stick = stick;

        this.timeout = 100;
        this.manual = false;
        this.powerCellHandler = new PowerCellHandler();

        defaultButtons();

    }

    @Override
    public void initialize() {

        timeSinceStart = 0;
        pulley.stop();
        shooter.stopShooter();
        shooter.stopKicker();

    }

    private void flipButtons() {

        if (stick.getRawButtonReleased(reversePulleyButton)) reverse = !reverse;

        if (stick.getRawButtonReleased(manualToggleButton)) {

            manual = !manual;
            defaultButtons();

        }

    }

    private void automatedPulley() {

        if (pulley.getBottomSensor() && !created) {

            powerCellHandler.addPowerCell(new PowerCell());
            created = true;
            timeSinceStart = 0;

        }

        PowerCell lastPowerCell = powerCellHandler.getLatestPowerCell();

        if (pulley.getMiddleSensor()) {

            if (!pastMiddle && powerCellHandler.count() > 0) {

                lastPowerCell.setPastSensor(true);
                pastMiddle = true;

            }

        } else pastMiddle = false;

        if (powerCellHandler.count() == 1) {

            if (!lastPowerCell.isPastSensor()) pulley.run(.25);
            else {

                pulley.stop();
                created = false;

            }

        } else if (
            powerCellHandler.count() > 1 &&
            powerCellHandler.getLatestPowerCell().isPastSensor() &&
            !lastPowerCell.isPastSensor()) pulley.run(Constants.pulleySpeed);
        else {

            pulley.stop();
            created = false;

        }

        if (timeSinceStart > timeout) pulley.stop();

    }

    private void automatedShooter() {

        if (stick.getRawButtonReleased(shooterButton)) {

            shootMode = !shootMode;
            hold = !hold;

        }

        if (hold) {

            shooter.shoot(getShooterSpeed());
            shootMode = true;

        } else shooter.stopShooter();

        if (!pulley.getTopSensor()) {

            destroyed = false;

            if (shootMode) pulley.run(Constants.pulleySpeed + .1);

        }

        if (pulley.getTopSensor()) {

            pulley.stop();

            if (!destroyed) {

                powerCellHandler.removeLastPowerCellHandled();
                destroyed = true;

            }

        }

        if (powerCellHandler.count() == 0) shootMode = false;
    }

    private double getShooterSpeed() {

        return Constants.shooterSpeedModes[shooterSpeedIndex];

    }

    private void nextShooterSpeedMode() {

        shooterSpeedIndex++;
        shooterSpeedIndex %= Constants.shooterSpeedModes.length;

        System.out.println("ENTERING SHOOTER ZONE #" + (shooterSpeedIndex + 1));

    }

    private void flyWheel() {

        if (!manual && stick.getRawButton(flyWheelButton)) shooter.runKicker();
        else shooter.stopKicker();

    }

    private void removeAllPowerCells(Boolean bool) {

        if (bool) powerCellHandler.removeLastPowerCellHandled();

    }

    private void intake() {

        if (!pulley.getBottomSensor() || manual) {

            if (stick.getRawButton(intakeButton)) intake.run(Constants.intakeSpeed);
            else if (stick.getRawButton(outtakeButton)) intake.run(-.75 * Constants.intakeSpeed);
            else intake.stop();

        } else if (stick.getRawButton(outtakeButton)) intake.run(-.75 * Constants.intakeSpeed);
        else intake.stop();
    }

    private void reverse() {

        pulley.run(-1 * Constants.pulleySpeed);

        if (pulley.getBottomSensor() && !destroyed) {

            powerCellHandler.removeLastPowerCellHandled();
            destroyed = true;

        }

        if (!pulley.getBottomSensor()) destroyed = false;

    }

    private void manualPulley() {

        if (stick.getRawButton(reversePulleyButton)) pulley.run(-1 * Constants.pulleySpeed);
        else if (stick.getRawButton(pulleyButton)) pulley.run(Constants.pulleySpeed);
        else pulley.run(0);

    }

    private void manualShooter() {

        if (stick.getRawButton(shooterButton)) shooter.shoot(getShooterSpeed());
        else shooter.shoot(0);

    }

    private void defaultButtons() {

        shootMode = false;
        destroyed = false;
        created = false;
        pastMiddle = false;
        reverse = false;
        hold = false;

    }

    @Override
    public void execute() {

        timeSinceStart++;
        flipButtons();
        removeAllPowerCells(stick.getRawButton(pulleyButton));
        intake();
        flyWheel();

        // Goes to next shooter speed mode
        if (stick.getRawButtonReleased(changeShooterModeButton)) nextShooterSpeedMode();

        if (!manual) {

            if (!reverse) {

                automatedPulley();
                automatedShooter();

            } else reverse();

        } else {

            manualPulley();
            manualShooter();
            removeAllPowerCells(true);

        }

    }

    @Override
    public void end(boolean interrupted) {

        pulley.stop();

    }

    @Override
    public boolean isFinished() {

        return false;

    }

}
