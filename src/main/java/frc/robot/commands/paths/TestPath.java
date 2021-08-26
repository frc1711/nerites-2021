package frc.robot.commands.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.Shoot;
import frc.robot.commands.basic.SlowDrive;
import frc.robot.commands.basic.SlowTurn;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SparkDrive;

public class TestPath extends SequentialCommandGroup {
	
	public TestPath (SparkDrive swerveDrive, Pulley pulley, Shooter shooter) {
        addCommands(
			SlowTurn.make(swerveDrive, 90, false));
			// SlowDrive.make(swerveDrive, 10, 12, false));
    }
	
}