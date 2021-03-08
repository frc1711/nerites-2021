package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;
import frc.team1711.swerve.subsystems.AutoSwerveDrive;
import frc.team1711.swerve.subsystems.AutoSwerveWheel;
import frc.team1711.swerve.util.Vector;

public class SparkDrive extends AutoSwerveDrive {
    
    private final AHRS gyro;
    
    public SparkDrive (AutoSwerveWheel _flWheel, AutoSwerveWheel _frWheel, AutoSwerveWheel _rlWheel, AutoSwerveWheel _rrWheel) {
        super(_flWheel, _frWheel, _rlWheel, _rrWheel, Constants.widthToHeightWheelbaseRatio);
        setMaxOutput(Constants.maxWheelSpeed);
        gyro = new AHRS();
    }
    
    public void fieldRelativeInputDrive (double strafeX, double strafeY, double steerX, double steerY) {
        
        // Strafing
        final Vector strafeInput = new Vector(strafeX, steerY);
        final Vector fieldStrafeInput = strafeInput.toRotationDegrees(fieldRelativeToRobotRelative(strafeInput.getRotationDegrees()));
        
        // Turning
        // Gets the desired field-relative rotation of the robot
        final Vector steerInput = new Vector(steerX, steerY);
        double targetRotation = 0;
        if (accountForDeadband(steerInput.getMagnitude()) != 0)
            targetRotation = steerInput.getRotationDegrees();
        
        // Maps the rotational difference between current robot rotation
        // and target (based on field relative input) on interval [0, 360)
        // to interval [-1, 1), representing steering speed
        double moveRotation = fieldRelativeToRobotRelative(targetRotation);
        if (moveRotation >= 180) moveRotation -= 360;
        moveRotation /= 180;
        
        // Runs input drive
        super.inputDrive(fieldStrafeInput.getX(), fieldStrafeInput.getY(), moveRotation);
    }
    
    private double fieldRelativeToRobotRelative (double rotation) {
        double moveRotation = rotation - gyro.getAngle();
        while (moveRotation < 0) moveRotation += 360;
        while (moveRotation >= 360) moveRotation -= 360;
        return moveRotation;
    }
    
}