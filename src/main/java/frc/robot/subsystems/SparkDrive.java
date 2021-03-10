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
    private boolean gyroReset;
    
    public SparkDrive () {
        super(new SparkWheel(Constants.flRotationMotor, Constants.flDirectionMotor),
                new SparkWheel(Constants.frRotationMotor, Constants.frDirectionMotor),
                new SparkWheel(Constants.rlRotationMotor, Constants.rlDirectionMotor),
                new SparkWheel(Constants.rrRotationMotor, Constants.rrDirectionMotor),
                Constants.widthToHeightWheelbaseRatio);
        
        setMaxOutput(Constants.maxWheelSpeed);
        gyro = new AHRS();
        gyroReset = false;
    }
    
    public void fieldRelativeInputDrive (double strafeX, double strafeY, double steerX, double steerY) {
        
        if (!gyroReset) {
            gyro.reset();
            gyroReset = true;
        }
        
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
        moveRotation /= 180;
        moveRotation *= Math.min(accountForDeadband(steerInput.getMagnitude()), 1);
        
        System.out.println("Gyro: " + gyro.getAngle());
        System.out.println("Magnitude: " + fieldStrafeInput.getMagnitude());
        System.out.println("Rotation: " + (int)fieldStrafeInput.getRotationDegrees());
        
        // Runs input drive
        super.inputDrive(
                0,// fieldStrafeInput.getX(),
                0,// fieldStrafeInput.getY(),
                steerX);
    }
    
    private double fieldRelativeToRobotRelative (double rotation) {
        double moveRotation = rotation - gyro.getAngle();
        while (moveRotation < 0) moveRotation += 360;
        while (moveRotation >= 360) moveRotation -= 360;
        return moveRotation;
    }
    
}