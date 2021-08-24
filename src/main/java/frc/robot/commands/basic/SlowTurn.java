package frc.robot.commands.basic;

import frc.team1711.swerve.commands.AutonTurn;
import frc.team1711.swerve.commands.FrameOfReference;
import frc.team1711.swerve.subsystems.AutoSwerveDrive;

public class SlowTurn {
    
    /**
     * Field-relative slow turn.
     */
    public static AutonTurn make (AutoSwerveDrive swerveDrive, double direction) {
        return new AutonTurn(
                swerveDrive,
                direction,
                0.04,
                1,
                FrameOfReference.FIELD);
    }

}