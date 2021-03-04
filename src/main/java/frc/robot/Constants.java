// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
    
    // Swerve drive
    public static final double
            driveSpeed = 0.2,
            turnSpeed = 0.2,
            maxWheelSpeed = 0.7,
            joystickDeadzone = 0.1,
            widthToHeightWheelbaseRatio = .851;
    
    public static final double
            kP = 0.5,
            kI = 0,
            kD = 0,
            tmpValue = 1.5;
    
    // Swerve Wheels
    public static final int
            flRotationMotor = 1,
            flDirectionMotor = 2,
            
            frRotationMotor = 12,
            frDirectionMotor = 13,
            
            rlRotationMotor = 4,
            rlDirectionMotor = 3,
            
            rrRotationMotor = 14,
            rrDirectionMotor = 15;
    
    
    // OI
    public static final int
            directMoveXAxis = 0,
            directMoveXAxisScalar = 1,
            
            directMoveYAxis = 1,
            directMoveYAxisScalar = -1;
    
    public static final int
            rotateAxis = 4,
            rotateAxisScalar = 1;
    
    public static final int mainController = 0;
    
}