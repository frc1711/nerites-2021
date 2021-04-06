package frc.robot.commands.basic;

import frc.team1711.swerve.commands.AutonDrive;
import frc.team1711.swerve.commands.FrameOfReference;
import frc.team1711.swerve.subsystems.AutoSwerveDrive;

public class FastDrive {
    
    private static final int SQUARE_SIZE = 30; // 2.5 feet per square

    /**
     * Field-relative slow drive
     * 2.5 feet per square
     */
    public static AutonDrive make (AutoSwerveDrive swerveDrive, double squaresRight, double squaresForward) {
        AutonDrive drive = AutonDrive.fromMovement(
                swerveDrive,
                squaresRight*SQUARE_SIZE,
                squaresForward*SQUARE_SIZE,
                1.5,
                8,
                0.015,
                FrameOfReference.FIELD);
        drive.setEaseOut(24, 0.15);
        return drive;
    }

}