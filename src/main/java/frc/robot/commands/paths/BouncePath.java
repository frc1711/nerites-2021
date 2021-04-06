// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.SparkDrive;
import frc.robot.commands.basic.*;

public class BouncePath extends SequentialCommandGroup {
    
    public BouncePath (SparkDrive swerveDrive) {
        addCommands(
            SlowTurn.make(swerveDrive, 0),

            SlowDrive.make(swerveDrive, 0, 1.5));

    }
    
}