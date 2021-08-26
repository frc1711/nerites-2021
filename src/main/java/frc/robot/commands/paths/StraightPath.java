package frc.robot.commands.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.Shoot;
import frc.robot.commands.basic.SlowDrive;
import frc.robot.commands.basic.SlowTurn;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SparkDrive;

/**
 * The back of the robot (opposite side of the intake) should be centered on the initiation line
 * directly in front of the port.
 * 
 * Shoots directly forward from the initiation line, then drives 5.5 ft towards the port.
 * @author Gabriel Seaver
 */
public class StraightPath extends SequentialCommandGroup {
	
	public StraightPath (SparkDrive swerveDrive, Pulley pulley, Shooter shooter) {
        addCommands(
			SlowTurn.make(swerveDrive, 180), // Aims back of robot towards port
			new Shoot(pulley, shooter, 3, 3), // Shoots all 3 cells at zone 3
			SlowDrive.make(swerveDrive, 0, 5.5 * 12)); // Drives 5.5 ft off initiation line
    }
	
}