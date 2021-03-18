// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;
import frc.team1711.swerve.subsystems.AutoSwerveWheel;

/**
 * @author Gabriel Seaver
 */
public class SparkWheel extends AutoSwerveWheel {
    
    private static final double countsToInches = 12.566 / 8.16 / 42;
    
    private final CANSparkMax driveController, steerController;
    private final PIDController steerPID;
    private final CANCoder steerEncoder;
    private final CANEncoder driveEncoder;
    private final double absolutePositionOffset;
    
    public SparkWheel (int rotationID, int directionID, int steerEncoderID) {
        steerController = new CANSparkMax(rotationID, CANSparkMaxLowLevel.MotorType.kBrushless);
        driveController = new CANSparkMax(directionID, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        driveController.setIdleMode(IdleMode.kBrake);
        steerController.setIdleMode(IdleMode.kBrake);
        
        steerEncoder = new CANCoder(steerEncoderID);
        
        // Custom param 0 used to store getAbsolutePosition() offset,
        // with a scaling factor of 1:100 for more precision
        absolutePositionOffset = steerEncoder.configGetCustomParam(0) / 100.;
        
        steerEncoder.setPositionToAbsolute(Integer.MAX_VALUE);
        driveEncoder = driveController.getEncoder();
        
        steerPID = new PIDController(
            Constants.wheelkP,
            Constants.wheelkI,
            Constants.wheelkD);
    }
    
    @Override
    protected void resetDriveEncoder () {
        driveEncoder.setPosition(0);
    }
    
    @Override
    protected double getPositionDifference () {
        return driveEncoder.getPosition() * countsToInches;
    }
    
    @Override
    protected double getDirection () {
        double direction = getRawDirection() * 360;
        while (direction < 0) direction += 360;
        while (direction >= 360) direction -= 360;
        return direction;
    }
    
    @Override
    protected void setDirection (double targetDirection) {
        // Gets the desired change in direction, and places on the interval [-180, 180)
        double moveDirection = targetDirection - getRawDirection() * 360;
        while (moveDirection < -180) moveDirection += 360;
        while (moveDirection >= 180) moveDirection -= 360;
        
        // Sets the PID loop
        setRawDirection(moveDirection / 360 + getRawDirection());
    }
    
    private void setRawDirection (double dir) {
        double setSpeed = steerPID.calculate(getRawDirection(), dir);
        steerController.set(setSpeed);
    }
    
    private double getRawDirection () {
        return (steerEncoder.getPosition() - absolutePositionOffset) / 360;
    }
    
    public void configAbsoluteEncoder () {
        // CANCoder configurations
        CANCoderConfiguration config = new CANCoderConfiguration();
        config.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        config.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        config.sensorDirection = false;
        
        // Custom param 0 used to store getAbsolutePosition() offset,
        // this value should be set to the current absolute
        // position in order to "zero" the encoders (with scaling
        // factor of 1:100 for more precision)
        config.customParam0 = (int)(steerEncoder.getAbsolutePosition() * 100);
        
        steerEncoder.configAllSettings(config);
    }
    
    @Override
    protected void setDriveSpeed (double speed) {
        driveController.set(speed);
    }
    
    @Override
    protected void stopSteering () {
        steerController.set(0);
    }
    
}