// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.controller.PIDController;

import frc.robot.Constants;
import frc.team1711.swerve.subsystems.AutoSwerveWheel;
import frc.team1711.swerve.util.Angles;

/**
 * @author Gabriel Seaver
 */
public class SparkWheel extends AutoSwerveWheel {
    
    private static final double unitsToInches = .143333;
    
    private final CANSparkMax driveController, steerController;
    private final PIDController steerPID;
    private final CANCoder steerEncoder;
    private final CANEncoder driveEncoder;
    private final double absolutePositionOffset;
    private int tempCounter; // TODO: Delete this
    
    public SparkWheel (int rotationID, int directionID, int steerEncoderID) {
        steerController = new CANSparkMax(rotationID, CANSparkMaxLowLevel.MotorType.kBrushless);
        driveController = new CANSparkMax(directionID, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        driveController.setIdleMode(IdleMode.kBrake);
        steerController.setIdleMode(IdleMode.kBrake);
        
        steerEncoder = new CANCoder(steerEncoderID);
        
        // Custom param 0 used to store getAbsolutePosition() offset,
        // with a scaling factor of 1:100 for more precision
        absolutePositionOffset = getAbsoluteOffset(steerEncoder.configGetCustomParam(0, 100));
        System.out.println(absolutePositionOffset);
        /*
        79.98
        58.88
        106.69
        172.61
        */

        steerEncoder.setPositionToAbsolute(Integer.MAX_VALUE);
        driveEncoder = driveController.getEncoder();
        
        steerPID = new PIDController(
            Constants.wheelkP,
            Constants.wheelkI,
            Constants.wheelkD);
    }
    
    
    
    // Configuration stuff for absolute encoders
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
        config.customParam0 = createNewAbsoluteOffset(steerEncoder.getAbsolutePosition());
        
        steerEncoder.configAllSettings(config, 100);
    }
    
    private double getAbsoluteOffset (int storedOffsetValue) {
        return storedOffsetValue / 100.;
    }
    
    private int createNewAbsoluteOffset (double absolutePosition) {
        return (int)(absolutePosition * 100);
    }
    
    
    
    // Driving
    @Override
    protected void setDriveSpeed (double speed) {
        driveController.set(speed);
    }
    
    @Override
    protected void resetDriveEncoder () {
        driveEncoder.setPosition(0); // TODO: This probably doesn't work
        driveEncoder.setPosition(0);
        driveEncoder.setPosition(0);
        if (driveEncoder.setPosition(0) != CANError.kOk)
            System.out.println("CANError encountered resetting CANEncoder.");
        System.out.println(tempCounter);
        tempCounter ++;
    }
    
    @Override
    protected double getPositionDifference () {
        return driveEncoder.getPosition() * unitsToInches;
    }
    
    
    
    // Steering
    @Override
    protected void stopSteering () {
        steerController.set(0);
    }
    
    @Override
    protected double getDirection () {
        return Angles.wrapDegrees(getRawDirection());
    }
    
    private double getRawDirection () {
        return -(steerEncoder.getAbsolutePosition() - absolutePositionOffset);
    }
    
    @Override
    protected void setDirection (double targetDirection) {
        // Gets the desired change in direction, and places on the interval [-180, 180)
        double moveDirection = Angles.wrapDegreesZeroCenter(targetDirection - getDirection());
        
        // Sets the PID loop
        final double setSpeed = steerPID.calculate(0, moveDirection / 360);
        steerController.set(-setSpeed);
    }
    
}