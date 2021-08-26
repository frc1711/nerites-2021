package frc.robot.commands.paths;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.commands.basic.Shoot;
import frc.robot.commands.basic.SlowDrive;
import frc.robot.commands.basic.SlowTurn;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SparkDrive;

/**
 * The middle of the robot should start on the center of the initiation line, 10 ft to the left of the
 * center of the port.
 * 
 * Drives towards the port, aims, shoots, and drives further to move off the line.
 * @author Gabriel Seaver
 */
public class LeftPath extends SequentialCommandGroup {
	
	public LeftPath (SparkDrive swerveDrive, Pulley pulley, Shooter shooter) {
        addCommands(
			SlowDrive.make(swerveDrive, 2 * 12, 2 * 12), // Drives ~2.8 ft towards the port
			SlowTurn.make(swerveDrive, 225), // Turns robot to aim back towards port
			new Shoot(pulley, shooter, 3, 3), // Shoots all 3 cells at zone 3
			SlowDrive.make(swerveDrive, 2 * 12, 2 * 12)); // Drives another ~2.8 ft towards the port
    }
	
}