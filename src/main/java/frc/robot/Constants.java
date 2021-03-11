// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
    
    // Speed and hardware constants
    public static final double
            driveSpeed = .08,
            turnSpeed = .05,
            maxWheelSpeed = .7,
            
            maxTurnInputSpeed = 0.6,
            turnInputSpeedScalar = 1.5,
            
            shooterSpeed = 8700,
            shooterUBound = shooterSpeed + 2000,
            shooterLBound = shooterSpeed - 125,
            
            widthToHeightWheelbaseRatio = .851,
            
            flyWheelSpeed = .75,
            
            pulleySpeed = .35,
            
            intakeSpeed = .45;
    
    // PID
    public static final double
            wheelkP = .6,
            wheelkI = 0,
            wheelkD = 0,
            
            shooterkP = .0075,
            shooterkI = .0001,
            shooterkD = shooterkP * 5,
            shooterkF = .09,
            
            pulleykP = 0.05,
            pulleykI = 0.05,
            pulleykD = pulleykP * 5,
            pulleykF = 0.45;
    
    // CAN IDs
    public static final int
            flRotationMotor = 12,
            flDirectionMotor = 2,
            
            frRotationMotor = 4,
            frDirectionMotor = 3,
            
            rlRotationMotor = 1,
            rlDirectionMotor = 13,
            
            rrRotationMotor = 14,
            rrDirectionMotor = 15,
            
            shooterLeft = 0,
            shooterRight = 5,
            
            flyWheel = 8,
            
            intake = 9,
            
            pulley = 7;
    
    // DIO
    public static final int
            bottomSensor = 0,
            middleSensor = 1,
            topSensor = 2;
    
    // OI
    public static final int
            directMoveXAxis = 0,
            directMoveXAxisScalar = 1,
            
            directMoveYAxis = 1,
            directMoveYAxisScalar = -1;
    
    public static final int
            rotateXAxis = 4,
            rotateXAxisScalar = 1,
            
            rotateYAxis = 5,
            rotateYAxisScalar = -1;
    
    public static final double
            joystickDeadzone = .08;
    
    public static final int mainController = 0;
    
}