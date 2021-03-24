package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;
import frc.team1711.swerve.subsystems.AutoSwerveDrive;

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
        setDriveRelativeSpeed(Constants.driveRelativeSpeed);
        setSteerRelativeSpeed(Constants.steerRelativeSpeed);
    }
    
    public void resetAbsoluteEncoders () {
        ((SparkWheel)flWheel).configAbsoluteEncoder();
        ((SparkWheel)frWheel).configAbsoluteEncoder();
        ((SparkWheel)rlWheel).configAbsoluteEncoder();
        ((SparkWheel)rrWheel).configAbsoluteEncoder();
    }
    
    @Override
    public void resetGyro () {
        gyro.reset();
    }
    
	@Override
	public double getGyroAngle () {
		return gyro.getAngle();
	}
    
}