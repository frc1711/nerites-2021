// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;

import frc.robot.Constants;
import frc.team1711.swerve.subsystems.AutoSwerveWheel;

/**
 * @author Gabriel Seaver
 */
public class SparkWheel extends AutoSwerveWheel {
    
    private static final double countsToInches = 12.566 / 8.16 / 42;
    
    private final CANSparkMax directDrive, rotationDrive;
    private final CANPIDController rotationDrivePID;
    private final CANEncoder steerEncoder, driveEncoder;
    
    public SparkWheel (int rotationID, int directionID) {
        rotationDrive = new CANSparkMax(rotationID, CANSparkMaxLowLevel.MotorType.kBrushless);
        directDrive = new CANSparkMax(directionID, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        steerEncoder = rotationDrive.getAlternateEncoder(AlternateEncoderType.kQuadrature, 4096);
        rotationDrivePID = rotationDrive.getPIDController();
        rotationDrivePID.setFeedbackDevice(steerEncoder);
        
        rotationDrivePID.setP(Constants.wheelkP);
        rotationDrivePID.setI(Constants.wheelkI);
        rotationDrivePID.setD(Constants.wheelkD);
        
        driveEncoder = directDrive.getEncoder();
    }
    
    @Override
    protected void resetDriveEncoder () {
        driveEncoder.setPosition(0);
    }
    
    @Override
    protected double getPositionDifference () {
        // TODO: .getPosition() may only get the counts
        // for the neo encoder, check github, do a full rev
        // and watch counts
        return driveEncoder.getPosition() * countsToInches;
    }
    
    @Override
    protected double getDirection () {
        double direction = -steerEncoder.getPosition() * 360;
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
        rotationDrivePID.setReference(-dir, ControlType.kPosition);
    }
    
    private double getRawDirection () {
        return -steerEncoder.getPosition();
    }
    
    @Override
    protected void setDriveSpeed (double speed) {
        directDrive.set(speed);
    }
    
    @Override
    protected void stopSteering () {
        rotationDrivePID.setReference(steerEncoder.getPosition(), ControlType.kPosition);
    }
    
}