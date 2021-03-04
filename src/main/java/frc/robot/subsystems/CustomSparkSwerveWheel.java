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
import com.revrobotics.EncoderType;

import frc.robot.Constants;
import swerve.subsystems.FESwerveWheel;

public class CustomSparkSwerveWheel extends FESwerveWheel {
    
    private static final double revsToInches = 12.566 / 8.16;
    
    private final CANSparkMax directDrive, rotationDrive;
    private final CANPIDController rotationDrivePID;
    private final CANEncoder steerEncoder, driveEncoder;
    
    public CustomSparkSwerveWheel (int rotationID, int directionID) {
        rotationDrive = new CANSparkMax(rotationID, CANSparkMaxLowLevel.MotorType.kBrushless);
        directDrive = new CANSparkMax(directionID, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        steerEncoder = rotationDrive.getAlternateEncoder(AlternateEncoderType.kQuadrature, 4096);
        rotationDrivePID = rotationDrive.getPIDController();
        rotationDrivePID.setFeedbackDevice(steerEncoder);
        
        rotationDrivePID.setP(Constants.kP);
        rotationDrivePID.setI(Constants.kI);
        rotationDrivePID.setD(Constants.kD);
        
        driveEncoder = directDrive.getEncoder(EncoderType.kHallSensor, 42);
    }
    
    @Override
    public void resetDriveEncoder() {
        driveEncoder.setPosition(0);
    }
    
    @Override
    public double getPositionDifference() {
        return driveEncoder.getPosition() * revsToInches;
    }
    
    @Override
    public double getRawDirection() {
        return -steerEncoder.getPosition();
    }
    
    @Override
    public void setRawDirection(double targetDirection) {
        rotationDrivePID.setReference(-targetDirection, ControlType.kPosition);
    }
    
    @Override
    public void setDriveSpeed(double speed) {
        directDrive.set(speed);
    }
    
    @Override
    public void stopSteering() {
        rotationDrivePID.setReference(steerEncoder.getPosition(), ControlType.kPosition);
    }
    
    @Override
    public void resetSteerEncoder() {
        steerEncoder.setPosition(0);
    }
    
}