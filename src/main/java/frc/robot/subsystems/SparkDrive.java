package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;
import frc.team1711.swerve.subsystems.AutoSwerveDrive;
import frc.team1711.swerve.util.Vector;

/**
 * @author Gabriel Seaver
 */
public class SparkDrive extends AutoSwerveDrive {
    
    private final AHRS gyro;
    
    public SparkDrive () {
        super(new SparkWheel(Constants.flRotationMotor, Constants.flDirectionMotor),
                new SparkWheel(Constants.frRotationMotor, Constants.frDirectionMotor),
                new SparkWheel(Constants.rlRotationMotor, Constants.rlDirectionMotor),
                new SparkWheel(Constants.rrRotationMotor, Constants.rrDirectionMotor),
                Constants.widthToHeightWheelbaseRatio);
        
        setMaxOutput(Constants.maxWheelSpeed);
        gyro = new AHRS();
    }
    
    public void fieldRelativeInputDrive (double strafeX, double strafeY, double steerX, double steerY) {
        
        // Strafing
        final Vector strafeInput = new Vector(strafeX, strafeY);
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
        
        // Whether or not moveRotation is negative (to prevent sqrt of negative number)
        final int reverse = moveRotation < 0 ? -1 : 1;
        
        moveRotation = Math.sqrt(reverse * accountForDeadband(steerInput.getMagnitude()) * moveRotation / 180);
        moveRotation = Math.max(Math.min(reverse * moveRotation, 1), -1);
        
        // Runs input drive
        super.inputDrive(
                fieldStrafeInput.getX(),
                fieldStrafeInput.getY(),
                moveRotation);
    }
    
    public void resetGyro () {
        gyro.reset();
    }
    
    public void resetEncoders () {
        ((SparkWheel)flWheel).resetEncoders();
        ((SparkWheel)frWheel).resetEncoders();
        ((SparkWheel)rlWheel).resetEncoders();
        ((SparkWheel)rrWheel).resetEncoders();
    }
    
    private double fieldRelativeToRobotRelative (double rotation) {
        double moveRotation = rotation - gyro.getAngle();
        while (moveRotation < 0) moveRotation += 360;
        while (moveRotation >= 360) moveRotation -= 360;
        return moveRotation;
    }
    
}