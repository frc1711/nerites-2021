package frc.robot.commands.basic;

import frc.team1711.swerve.commands.AutonTurn;
import frc.team1711.swerve.commands.FrameOfReference;
import frc.team1711.swerve.subsystems.AutoSwerveDrive;

public class SlowTurn {
    
    /**
     * Slow turn.
     */
    public static AutonTurn make (AutoSwerveDrive swerveDrive, double direction, boolean robotRelative) {
        return new AutonTurn(
                swerveDrive,
                direction,
                0.04,
                1,
                robotRelative ? FrameOfReference.ROBOT : FrameOfReference.FIELD);
    }
	
	/**
	 * Field-relative slow turn.
	 */
	public static AutonTurn make (AutoSwerveDrive swerveDrive, double direction) {
		return make(swerveDrive, direction, false);
	}

}