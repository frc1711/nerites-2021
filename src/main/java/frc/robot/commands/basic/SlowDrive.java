package frc.robot.commands.basic;

import frc.team1711.swerve.commands.AutonDrive;
import frc.team1711.swerve.commands.FrameOfReference;
import frc.team1711.swerve.subsystems.AutoSwerveDrive;

public class SlowDrive {

    /**
     * Field-relative slow drive.
     */
    public static AutonDrive make (AutoSwerveDrive swerveDrive, double inchesRight, double inchesForward) {
        AutonDrive drive = AutonDrive.fromMovement(
                swerveDrive,
                inchesRight,
                inchesForward,
                0.4,
                8,
                0.015,
                FrameOfReference.FIELD);
        drive.setEaseOut(6, 0.15);
        return drive;
    }

}