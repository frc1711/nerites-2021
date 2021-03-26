// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.SparkDrive;

/**
 * @author Gabriel Seaver
 */
public class SwerveCommand extends CommandBase {
    
    private final SparkDrive swerveDrive;
    
    private final DoubleSupplier
            dirMoveXAxis,
            dirMoveYAxis,
            rotXAxis;
    
    private final BooleanSupplier
            turboMode,
            slowMode,
            resetGyro;
    
    public SwerveCommand (
            SparkDrive swerveDrive,
            DoubleSupplier dirMoveXAxis,
            DoubleSupplier dirMoveYAxis,
            DoubleSupplier rotXAxis,
            BooleanSupplier turboMode,
            BooleanSupplier slowMode,
            BooleanSupplier resetGyro) {
        
        this.swerveDrive = swerveDrive;
        this.dirMoveXAxis = dirMoveXAxis;
        this.dirMoveYAxis = dirMoveYAxis;
        this.rotXAxis = rotXAxis;
        this.turboMode = turboMode;
        this.slowMode = slowMode;
        this.resetGyro = resetGyro;

        addRequirements(swerveDrive);
    }
    
    @Override
    public void initialize () {
        swerveDrive.stop();
    }
    
    @Override
    public void execute () {

        if (resetGyro.getAsBoolean()) swerveDrive.resetGyro();

        if (slowMode.getAsBoolean()) {
            swerveDrive.setMaxOutput(Constants.maxWheelSpeeds[2]);
            swerveDrive.setDriveRelativeSpeed(Constants.driveRelativeSpeeds[2]);
            swerveDrive.setSteerRelativeSpeed(Constants.steerRelativeSpeeds[2]);
        } else if (turboMode.getAsBoolean()) {
            swerveDrive.setMaxOutput(Constants.maxWheelSpeeds[1]);
            swerveDrive.setDriveRelativeSpeed(Constants.driveRelativeSpeeds[1]);
            swerveDrive.setSteerRelativeSpeed(Constants.steerRelativeSpeeds[1]);
        } else {
            swerveDrive.setMaxOutput(Constants.maxWheelSpeeds[0]);
            swerveDrive.setDriveRelativeSpeed(Constants.driveRelativeSpeeds[0]);
            swerveDrive.setSteerRelativeSpeed(Constants.steerRelativeSpeeds[0]);
        }

        double directMoveXAxis = dirMoveXAxis.getAsDouble();
        double directMoveYAxis = dirMoveYAxis.getAsDouble();
        double rotateXAxis = rotXAxis.getAsDouble();
        
        if (Math.abs(directMoveXAxis) < Constants.joystickDeadzone && Math.abs(directMoveYAxis) < Constants.joystickDeadzone) {
            directMoveXAxis = 0;
            directMoveYAxis = 0;
        }
        if (Math.abs(rotateXAxis) < Constants.joystickDeadzone) {
            rotateXAxis = 0;
        }
        
        swerveDrive.fieldRelativeInputDrive(directMoveXAxis, directMoveYAxis, rotateXAxis, true);
    }
    
    @Override
    public void end (boolean interrupted) {
        swerveDrive.stop();
    }
    
    @Override
    public boolean isFinished () {
        return false;
    }
    
}