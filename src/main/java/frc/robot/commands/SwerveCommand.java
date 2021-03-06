// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.team1711.swerve.subsystems.FESwerveDrive;

public class SwerveCommand extends CommandBase {
    
    private final FESwerveDrive swerveDrive;
    
    private final DoubleSupplier
            dirMoveXAxis,
            dirMoveYAxis,
            rotAxis;
    
    public SwerveCommand (
            FESwerveDrive _swerveDrive,
            DoubleSupplier _dirMoveXAxis,
            DoubleSupplier _dirMoveYAxis,
            DoubleSupplier _rotAxis) {
        
        swerveDrive = _swerveDrive;
        
        addRequirements(swerveDrive);
        
        dirMoveXAxis = _dirMoveXAxis;
        dirMoveYAxis = _dirMoveYAxis;
        rotAxis = _rotAxis;
    }
    
    @Override
    public void initialize () {
        swerveDrive.stop();
    }
    
    @Override
    public void execute () {
        double directMoveXAxis = dirMoveXAxis.getAsDouble();
        double directMoveYAxis = dirMoveYAxis.getAsDouble();
        double rotateAxis = rotAxis.getAsDouble();
        
        if (Math.abs(directMoveXAxis) < Constants.joystickDeadzone && Math.abs(directMoveYAxis) < Constants.joystickDeadzone) {
            directMoveXAxis = 0;
            directMoveYAxis = 0;
        }
        if (Math.abs(rotateAxis) < Constants.joystickDeadzone) rotateAxis = 0;
        
        swerveDrive.inputDrive(directMoveXAxis, directMoveYAxis, rotateAxis);
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