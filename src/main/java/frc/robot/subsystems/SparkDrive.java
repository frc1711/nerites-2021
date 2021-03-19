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
        super(  new SparkWheel(Constants.flSteerMotor, Constants.flDriveMotor, Constants.flSteerEncoder),
                new SparkWheel(Constants.frSteerMotor, Constants.frDriveMotor, Constants.frSteerEncoder),
                new SparkWheel(Constants.rlSteerMotor, Constants.rlDriveMotor, Constants.rlSteerEncoder),
                new SparkWheel(Constants.rrSteerMotor, Constants.rrDriveMotor, Constants.rrSteerEncoder),
                Constants.widthToHeightWheelbaseRatio);
        
        setMaxOutput(Constants.maxWheelSpeed);
        gyro = new AHRS();
    }
    
    public void fieldRelativeInputDrive (double strafeX, double strafeY, double steerX) {
        
        // Strafing
        final Vector strafeInput = new Vector(strafeX, strafeY);
        final Vector fieldStrafeInput = strafeInput.toRotationDegrees(fieldRelativeToRobotRelative(strafeInput.getRotationDegrees()));
        
        
        // NO FIELD RELATIVE TURNING
        // Turning
        // Gets the desired field-relative rotation of the robot
        // final Vector steerInput = new Vector(steerX, steerY);
        // double targetRotation = 0;
        // if (accountForDeadband(steerInput.getMagnitude()) != 0)
        //     targetRotation = steerInput.getRotationDegrees();
        
        // // Maps the rotational difference between current robot rotation
        // // and target (based on field relative input) on interval [0, 360)
        // // to interval [-1, 1), representing steering speed
        // double moveRotation = fieldRelativeToRobotRelative(targetRotation);
        // if (moveRotation >= 180) moveRotation -= 360;
        
        // // Whether or not moveRotation is negative (to prevent sqrt of negative number)
        // final int reverse = moveRotation < 0 ? -1 : 1;
        
        // moveRotation = Math.sqrt(reverse * accountForDeadband(steerInput.getMagnitude()) * moveRotation / 180);
        // moveRotation = Math.max(Math.min(reverse * moveRotation, 1), -1);
        
        // Runs input drive
        super.inputDrive(
                fieldStrafeInput.getX(),
                fieldStrafeInput.getY(),
                steerX);
    }
    
    public void resetAbsoluteEncoders () {
        ((SparkWheel)flWheel).configAbsoluteEncoder();
        ((SparkWheel)frWheel).configAbsoluteEncoder();
        ((SparkWheel)rlWheel).configAbsoluteEncoder();
        ((SparkWheel)rrWheel).configAbsoluteEncoder();
    }
    
    public void resetGyro () {
        gyro.reset();
    }
    
    private double fieldRelativeToRobotRelative (double rotation) {
        double moveRotation = rotation - gyro.getAngle();
        while (moveRotation < 0) moveRotation += 360;
        while (moveRotation >= 360) moveRotation -= 360;
        return moveRotation;
    }
    
}