package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

/**
 * @author Gabriel Seaver
 */
public class Climber extends SubsystemBase {
	
	private final CANSparkMax liftController;
	private final WPI_TalonSRX winchController;
	
	public Climber () {
		liftController = new CANSparkMax(Constants.lift, CANSparkMaxLowLevel.MotorType.kBrushless);
		winchController = new WPI_TalonSRX(Constants.winch);
	}
	
	public void stopLift () {
		liftController.set(0);
	}
	
	public void stopWinch () {
		winchController.set(0);
	}
	
	/**
	 * {@code speed} should be in the range {@code [-1, 1]}.
	 * {@code 1} represents up, and {@code -1} represents down.
	 * 
	 * @param speed
	 */
	public void runLift (double speed) {
		liftController.set(speed * Constants.liftSpeedScalar);
	}
	
	/**
	 * {@code speed} should be in the range {@code [-1, 1]}.
	 * {@code 1} represents up, and {@code -1} represents down.
	 * 
	 * @param speed
	 */
	public void runWinch (double speed) {
		winchController.set(speed * Constants.winchSpeedScalar);
	}
	
}