// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.SwerveCommand;
import frc.robot.subsystems.SparkDrive;
import frc.robot.subsystems.SparkWheel;
import frc.team1711.swerve.commands.AutonDrive;

public class RobotContainer {
    
    private final SwerveCommand swerveCommand;
    private final SparkDrive swerveDrive;
    
    private final Joystick controller;
    
    public RobotContainer () {
        
        controller = new Joystick(Constants.mainController);
        
        swerveDrive = new SparkDrive(
                new SparkWheel(Constants.flRotationMotor, Constants.flDirectionMotor),
                new SparkWheel(Constants.frRotationMotor, Constants.frDirectionMotor),
                new SparkWheel(Constants.rlRotationMotor, Constants.rlDirectionMotor),
                new SparkWheel(Constants.rrRotationMotor, Constants.rrDirectionMotor));
        
        swerveCommand = new SwerveCommand(
                swerveDrive,
                () -> controller.getRawAxis(Constants.directMoveXAxis) * Constants.directMoveXAxisScalar,
                () -> controller.getRawAxis(Constants.directMoveYAxis) * Constants.directMoveYAxisScalar,
                () -> controller.getRawAxis(Constants.rotateXAxis) * Constants.rotateXAxisScalar,
                () -> controller.getRawAxis(Constants.rotateYAxis) * Constants.rotateYAxisScalar);
        
        swerveDrive.setDefaultCommand(swerveCommand);
    }
    
    public Command getAutonomousCommand () {
        return new SequentialCommandGroup(
                new AutonDrive(swerveDrive, 180, 24*10, 0.4),
                new AutonDrive(swerveDrive, 90, 48*10, 0.4));
    }
    
}