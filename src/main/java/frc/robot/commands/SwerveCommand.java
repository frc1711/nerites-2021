// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.SparkDrive;

public class SwerveCommand extends CommandBase {
    
    private final SparkDrive swerveDrive;
    
    private final DoubleSupplier
            dirMoveXAxis,
            dirMoveYAxis,
            rotXAxis,
            rotYAxis;
    
    public SwerveCommand (
            SparkDrive _swerveDrive,
            DoubleSupplier _dirMoveXAxis,
            DoubleSupplier _dirMoveYAxis,
            DoubleSupplier _rotXAxis,
            DoubleSupplier _rotYAxis) {
        
        swerveDrive = _swerveDrive;
        
        addRequirements(swerveDrive);
        
        dirMoveXAxis = _dirMoveXAxis;
        dirMoveYAxis = _dirMoveYAxis;
        rotXAxis = _rotXAxis;
        rotYAxis = _rotYAxis;
    }
    
    @Override
    public void initialize () {
        swerveDrive.stop();
    }
    
    @Override
    public void execute () {
        double directMoveXAxis = dirMoveXAxis.getAsDouble();
        double directMoveYAxis = dirMoveYAxis.getAsDouble();
        double rotateXAxis = rotXAxis.getAsDouble();
        double rotateYAxis = rotYAxis.getAsDouble();
        
        if (Math.abs(directMoveXAxis) < Constants.joystickDeadzone && Math.abs(directMoveYAxis) < Constants.joystickDeadzone) {
            directMoveXAxis = 0;
            directMoveYAxis = 0;
        }
        if (Math.abs(rotateXAxis) < Constants.joystickDeadzone && Math.abs(rotateYAxis) < Constants.joystickDeadzone) {
            rotateXAxis = 0;
            rotateYAxis = 0;
        }
        
        swerveDrive.fieldRelativeInputDrive(directMoveXAxis, directMoveYAxis, rotateXAxis, rotateYAxis);
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