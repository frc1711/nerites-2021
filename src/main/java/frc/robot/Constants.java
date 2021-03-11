// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
    
    // Speed and hardware constants
    public static final double
            driveSpeed = .2,
            turnSpeed = .05,
            maxWheelSpeed = .7,
            
            flyWheelSpeed = .75,
            shooterSpeed = 8700,
            shooterSpeedBound = 200,
            
            widthToHeightWheelbaseRatio = .851;
    
    // PID
    public static final double
            wheelkP = .6,
            wheelkI = 0,
            wheelkD = 0,
            
            shooterkP = .0075,
            shooterkI = .0001,
            shooterkD = shooterkP * 5,
            shooterkF = .09;
    
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
            
            flyWheel = 8;
    
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