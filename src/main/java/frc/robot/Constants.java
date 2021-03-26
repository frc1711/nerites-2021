// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {

    // [0] -> Normal mode
    // [1] -> TUUURBO MODE
    // [2] -> Slow mode
    public static final double[]
            driveRelativeSpeeds =   {.6,    1,      .1},   // Strafing speed relative to turning speed
            steerRelativeSpeeds =   {.7,    .65,    .05}, // Turning speed relative to strafing speed
            maxWheelSpeeds =        {.6,    1,      .08};

    // Speed and hardware constants\
    public static final double
            widthToHeightWheelbaseRatio = .851,

            flyWheelSpeed = .75,

            pulleySpeed = .7,

            intakeSpeed = .45;

    public static final double[] shooterSpeedModes = {
        12000,  // 1st zone
        9500,   // 2nd zone
        10500,  // 3rd zone
        12000,  // 4th zone
    };

    // PID
    public static final double
            wheelkP = 1.5, // 1.5
            wheelkI = 0,
            wheelkD = 0,
            
            pulleykP = 0.05,
            pulleykI = 0.05,
            pulleykD = pulleykP * 5,
            pulleykF = 0.45;

    // CAN IDs
    public static final int
            flSteerMotor = 12,
            flDriveMotor = 2,
            flSteerEncoder = 17,

            frSteerMotor = 4, // 4
            frDriveMotor = 3, // 3
            frSteerEncoder = 19, // 19

            rlSteerMotor = 1,
            rlDriveMotor = 13,
            rlSteerEncoder = 16,

            rrSteerMotor = 14, // 14
            rrDriveMotor = 15, // 15
            rrSteerEncoder = 18, // 18

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
            rotateXAxisScalar = 1;

    public static final double
            joystickDeadzone = .08;

    public static final int
            driveController = 0,
            shootController = 1;

}
