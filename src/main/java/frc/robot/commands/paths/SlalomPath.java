// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.SparkDrive;
import frc.robot.commands.basic.*;

public class SlalomPath extends SequentialCommandGroup {
    
    public SlalomPath (SparkDrive swerveDrive) {
        addCommands(
            SlowTurn.make(swerveDrive, 0),

            // Beginning L
            SlowDrive.make(swerveDrive, 0, 1.9),
            SlowTurn.make(swerveDrive, 0),
            SlowDrive.make(swerveDrive, -2, 0),
            SlowTurn.make(swerveDrive, 0),

            // Mid fast part
            FastDrive.make(swerveDrive, 0, 5.9),
            SlowTurn.make(swerveDrive, 0),

            // Loop at end
            SlowDrive.make(swerveDrive, 2.4, 0),
            SlowTurn.make(swerveDrive, 0),

            SlowDrive.make(swerveDrive, 0, 1.6),
            SlowTurn.make(swerveDrive, 0),

            SlowDrive.make(swerveDrive, -2, 0),
            SlowTurn.make(swerveDrive, 0),

            SlowDrive.make(swerveDrive, 0, -2.1),
            SlowTurn.make(swerveDrive, 0),

            SlowDrive.make(swerveDrive, 2, 0),
            SlowTurn.make(swerveDrive, 0),

            // Mid fast part (going back)
            FastDrive.make(swerveDrive, 0, -6.8),
            SlowTurn.make(swerveDrive, 0),

            // Final L
            SlowDrive.make(swerveDrive, -2, 0),
            SlowTurn.make(swerveDrive, 0),
            SlowDrive.make(swerveDrive, 0, -2),
            SlowTurn.make(swerveDrive, 0));
    }
    
}