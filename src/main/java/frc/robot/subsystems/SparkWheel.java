// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
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
    
    public SparkWheel (int rotationID, int directionID, int steerEncoderID) {
        steerController = new CANSparkMax(rotationID, CANSparkMaxLowLevel.MotorType.kBrushless);
        driveController = new CANSparkMax(directionID, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        driveController.setIdleMode(IdleMode.kBrake);
        steerController.setIdleMode(IdleMode.kBrake);
        
        steerEncoder = new CANCoder(steerEncoderID);
        final CANCoderConfiguration steerEncoderConfig = new CANCoderConfiguration();
        steerEncoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        steerEncoderConfig.sensorDirection = false;
        steerEncoder.configAllSettings(steerEncoderConfig);
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
        return steerEncoder.getAbsolutePosition() / 360;
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