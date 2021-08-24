// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.SparkDrive;
import frc.team1711.swerve.commands.AutonDrive;
import frc.team1711.swerve.subsystems.AutoSwerveDrive;
import frc.robot.commands.basic.*;

public class SlalomPath extends SequentialCommandGroup {
	
    public SlalomPath (SparkDrive swerveDrive) {
        addCommands(
            SlowTurn.make(swerveDrive, 0),

            // Beginning L
            makeSlowDrive(swerveDrive, 0, 1.9),
            SlowTurn.make(swerveDrive, 0),
            makeSlowDrive(swerveDrive, -2, 0),
            SlowTurn.make(swerveDrive, 0),

            // Mid fast part
            makeFastDrive(swerveDrive, 0, 5.9),
            SlowTurn.make(swerveDrive, 0),

            // Loop at end
            makeSlowDrive(swerveDrive, 2.4, 0),
            SlowTurn.make(swerveDrive, 0),

            makeSlowDrive(swerveDrive, 0, 1.6),
            SlowTurn.make(swerveDrive, 0),

            makeSlowDrive(swerveDrive, -2, 0),
            SlowTurn.make(swerveDrive, 0),

            makeSlowDrive(swerveDrive, 0, -2.1),
            SlowTurn.make(swerveDrive, 0),

            makeSlowDrive(swerveDrive, 2, 0),
            SlowTurn.make(swerveDrive, 0),

            // Mid fast part (going back)
            makeFastDrive(swerveDrive, 0, -6.8),
            SlowTurn.make(swerveDrive, 0),

            // Final L
            makeSlowDrive(swerveDrive, -2, 0),
            SlowTurn.make(swerveDrive, 0),
            makeSlowDrive(swerveDrive, 0, -2),
            SlowTurn.make(swerveDrive, 0));
    }
	
	private AutonDrive makeSlowDrive (AutoSwerveDrive swerveDrive, double squaresRight, double squaresForward) {
		return SlowDrive.make(swerveDrive, squaresRight * 30, squaresForward * 30);
	}
	
	private AutonDrive makeFastDrive (AutoSwerveDrive swerveDrive, double squaresRight, double squaresForward) {
		return FastDrive.make(swerveDrive, squaresRight * 30, squaresForward * 30);
	}
    
}