// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.commands.ShootCommand;
import frc.robot.commands.SwerveCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SparkDrive;
import frc.team1711.swerve.commands.AutonDrive;

public class RobotContainer {
    
    private final SwerveCommand swerveCommand;
    private final ShootCommand shootCommand;
    
    private final SparkDrive swerveDrive;
    private final Shooter shooter;
    
    private final Joystick controller;
    
    public RobotContainer () {
        controller = new Joystick(Constants.mainController);
        
        swerveDrive = new SparkDrive();
        shooter = new Shooter();
        
        swerveCommand = new SwerveCommand(
                swerveDrive,
                () -> controller.getRawAxis(Constants.directMoveXAxis) * Constants.directMoveXAxisScalar,
                () -> controller.getRawAxis(Constants.directMoveYAxis) * Constants.directMoveYAxisScalar,
                () -> controller.getRawAxis(Constants.rotateXAxis) * Constants.rotateXAxisScalar);
                // () -> controller.getRawAxis(Constants.rotateYAxis) * Constants.rotateYAxisScalar
        
        shootCommand = new ShootCommand(
                shooter,
                () -> controller.getRawButton(1),
                () -> controller.getRawButton(2));
        
        swerveDrive.setDefaultCommand(swerveCommand);
        shooter.setDefaultCommand(shootCommand);
    }
    
    public Command getAutonomousCommand () {
        return new AutonDrive(swerveDrive, 0, 12, 0.3);
    }
    
}