/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.util.PIDHelp;

/**
 * Adapted from https://github.com/frc1711/Nerites/blob/master/src/main/java/frc/robot/subsystems/Shooter.java
 * @author Lou DeZeeuw
 * @author Gabriel Seaver
 */
public class Shooter extends SubsystemBase {
    
    private WPI_TalonSRX shooterTalonLeft;
    private WPI_TalonSRX shooterTalonRight;
    
    private WPI_TalonSRX flyWheel;
    
    public Shooter () {
        shooterTalonLeft = new WPI_TalonSRX(Constants.shooterLeft);
        shooterTalonRight = new WPI_TalonSRX(Constants.shooterRight);
        flyWheel = new WPI_TalonSRX(Constants.flyWheel);
        
        shooterTalonLeft.setSafetyEnabled(false);
        shooterTalonRight.setSafetyEnabled(false);
        
        shooterTalonRight.setInverted(true);
        shooterTalonRight.set(ControlMode.Follower, Constants.shooterLeft);
    }
    
    public void shoot (double speed) {
        PIDHelp.toVelocity(shooterTalonLeft, speed);
    }
    
    public void stopShooter () {
        shooterTalonLeft.set(0);
    }
    
    public void runFlyWheel () {
        flyWheel.set(Constants.flyWheelSpeed);
    }
    
    public void stopFlyWheel () {
        flyWheel.set(0);
    }
    
    @Override
    public void periodic () {}
    
}