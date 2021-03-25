// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.commands.CentralSystem;
import frc.robot.commands.SwerveCommand;
import frc.robot.commands.TestCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SparkDrive;

public class RobotContainer {
    
    private final SwerveCommand swerveCommand;
    private final CentralSystem centralSystem;
    
    private final SparkDrive swerveDrive;
    private final Shooter shooter;
    private final Intake intake;
    private final Pulley pulley;
    
    private final Joystick driveController, shootController;
    
    public RobotContainer () {
        driveController = new Joystick(Constants.driveController);
        shootController = new Joystick(Constants.shootController);
        
        swerveDrive = new SparkDrive();
        shooter = new Shooter();
        intake = new Intake();
        pulley = new Pulley();
        
        swerveCommand = new SwerveCommand(
                swerveDrive,
                () -> driveController.getRawAxis(Constants.directMoveXAxis) * Constants.directMoveXAxisScalar,
                () -> driveController.getRawAxis(Constants.directMoveYAxis) * Constants.directMoveYAxisScalar,
                () -> driveController.getRawAxis(Constants.rotateXAxis) * Constants.rotateXAxisScalar,
                () -> driveController.getRawButton(1),
                () -> driveController.getRawButtonReleased(5));
        
        centralSystem = new CentralSystem(pulley, shooter, intake, shootController);
        
        swerveDrive.setDefaultCommand(swerveCommand);
        shooter.setDefaultCommand(centralSystem);
    }
    
    public Command getAutonomousCommand () {
        return new TestCommand(swerveDrive);
    }
    
    public void onTestInit () {
        swerveDrive.resetGyro();
        swerveDrive.resetAbsoluteEncoders();
        System.out.println("The gyro has been reset and the CANCoders");
        System.out.println("have been reconfigured (the absolute positions");
        System.out.println("have been zeroed to their currect directions).");
        System.out.println("\nPlease turn the robot off and on again.");
    }
    
}