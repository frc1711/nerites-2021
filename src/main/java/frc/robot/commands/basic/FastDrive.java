package frc.robot.commands.basic;

import frc.team1711.swerve.commands.AutonDrive;
import frc.team1711.swerve.commands.FrameOfReference;
import frc.team1711.swerve.subsystems.AutoSwerveDrive;

public class FastDrive {

    /**
     * Fast drive.
     */
    public static AutonDrive make (AutoSwerveDrive swerveDrive, double inchesRight, double inchesForward, boolean robotRelative) {
        AutonDrive drive = AutonDrive.fromMovement(
                swerveDrive,
                inchesRight,
                inchesForward,
                1.5,
                8,
                0.015,
                robotRelative ? FrameOfReference.ROBOT : FrameOfReference.FIELD);
        drive.setEaseOut(24, 0.15);
        return drive;
    }
	
	/**
	 * Field-relative fast drive.
	 */
	public static AutonDrive make (AutoSwerveDrive swerveDrive, double inchesRight, double inchesForward) {
		return make(swerveDrive, inchesRight, inchesForward, false);
	}

}