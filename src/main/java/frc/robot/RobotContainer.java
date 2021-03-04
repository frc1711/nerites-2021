// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auton.AutonDriveCommand;
import frc.robot.commands.teleop.SwerveCommand;
import frc.robot.subsystems.CustomSparkSwerveWheel;
import swerve.drive.FESwerveDrive;

public class RobotContainer {
    
    //private final SwerveCommand swerveCommand;
    private final CustomSparkSwerveWheel
            flWheel,
            frWheel,
            rlWheel,
            rrWheel;
    
    private final Joystick controller;
    
    public RobotContainer () {
        
        controller = new Joystick(Constants.mainController);
        
        flWheel = new CustomSparkSwerveWheel(Constants.flRotationMotor, Constants.flDirectionMotor);
        frWheel = new CustomSparkSwerveWheel(Constants.frRotationMotor, Constants.frDirectionMotor);
        rlWheel = new CustomSparkSwerveWheel(Constants.rlRotationMotor, Constants.rlDirectionMotor);
        rrWheel = new CustomSparkSwerveWheel(Constants.rrRotationMotor, Constants.rrDirectionMotor);
        
        // swerveCommand = new SwerveCommand(
        //         flWheel, frWheel, rlWheel, rrWheel,
        //         () -> controller.getRawAxis(Constants.directMoveXAxis) * Constants.directMoveXAxisScalar,
        //         () -> controller.getRawAxis(Constants.directMoveYAxis) * Constants.directMoveYAxisScalar,
        //         () -> controller.getRawAxis(Constants.rotateAxis) * Constants.rotateAxisScalar);
        
        // flWheel.setDefaultCommand(swerveCommand);
    }
    
    public Command getAutonomousCommand () {
        FESwerveDrive swerveDrive = new FESwerveDrive(flWheel, frWheel, rlWheel, rrWheel,
                Constants.widthToHeightWheelbaseRatio);
        swerveDrive.setSafetyEnabled(false);
        return new SequentialCommandGroup(
                new AutonDriveCommand(swerveDrive, 180, 24*10, 0.4),
                new AutonDriveCommand(swerveDrive, 90, 48*10, 0.4)
        );
        
    }
    
}