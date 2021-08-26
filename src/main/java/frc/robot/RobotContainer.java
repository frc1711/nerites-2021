// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.CentralSystem;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.SwerveCommand;
import frc.robot.commands.basic.Shoot;
import frc.robot.commands.basic.SlowDrive;
import frc.robot.commands.basic.SlowTurn;
import frc.robot.commands.paths.LeftPath;
import frc.robot.commands.paths.StraightPath;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SparkDrive;

public class RobotContainer {
    
    private final SwerveCommand swerveCommand;
    private final CentralSystem centralSystem;
	private final ClimberCommand climberCommand;
    
    private final SparkDrive swerveDrive;
    private final Shooter shooter;
    private final Intake intake;
    private final Pulley pulley;
	private final Climber climber;
    
    private final Joystick driveController, shootController;
    
    public RobotContainer () {
        driveController = new Joystick(Constants.driveController);
        shootController = new Joystick(Constants.shootController);
        
        swerveDrive = new SparkDrive();
        shooter = new Shooter();
        intake = new Intake();
        pulley = new Pulley();
		climber = new Climber();
        
        swerveCommand = new SwerveCommand(
                swerveDrive,
                () -> driveController.getRawAxis(Constants.directMoveXAxis) * Constants.directMoveXAxisScalar,
                () -> driveController.getRawAxis(Constants.directMoveYAxis) * Constants.directMoveYAxisScalar,
                () -> driveController.getRawAxis(Constants.rotateXAxis) * Constants.rotateXAxisScalar,
                () -> driveController.getRawAxis(3) > .8, // RT
                () -> driveController.getRawAxis(2) > .8, // LT
                () -> driveController.getRawButtonReleased(5) && driveController.getRawButtonReleased(6)); // RB and LB
        
        centralSystem = new CentralSystem(pulley, shooter, intake, shootController);
		
		climberCommand = new ClimberCommand(
				climber,
				() -> shootController.getRawAxis(Constants.liftAxis) * Constants.liftAxisScalar,
				() -> shootController.getRawAxis(Constants.winchAxis) * Constants.winchAxisScalar);
        
        swerveDrive.setDefaultCommand(swerveCommand);
        shooter.setDefaultCommand(centralSystem);
		climber.setDefaultCommand(climberCommand);
    }
    
    public Command getAutonomousCommand () {
		return new LeftPath(swerveDrive, pulley, shooter);
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