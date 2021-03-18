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
public class PIDShooter extends SubsystemBase {
    
    private WPI_TalonSRX shooterTalonLeft;
    private WPI_TalonSRX shooterTalonRight;
    
    private WPI_TalonSRX flyWheel;
    
    public PIDShooter () {
        shooterTalonLeft = new WPI_TalonSRX(Constants.shooterLeft);
        shooterTalonRight = new WPI_TalonSRX(Constants.shooterRight);
        flyWheel = new WPI_TalonSRX(Constants.flyWheel);
        
        shooterTalonLeft.setSafetyEnabled(false);
        shooterTalonRight.setSafetyEnabled(false);
        
        shooterTalonRight.setInverted(true);
        shooterTalonRight.set(ControlMode.Follower, Constants.shooterLeft);
        
        // shooterTalonLeft.config_kP(0, Constants.shooterkP);
        // shooterTalonLeft.config_kI(0, Constants.shooterkI);
        // shooterTalonLeft.config_kD(0, Constants.shooterkD);
        // shooterTalonLeft.config_kF(0, Constants.shooterkF);
    }
    
    //SHOOTER MOTORS
    public void shoot (double speed) {
        shooterTalonLeft.set(speed);
    }
    
    public void stopShooter () {
        shooterTalonLeft.set(ControlMode.PercentOutput, 0);
    }
    
    public double getDutyCycle () {
        return shooterTalonLeft.getMotorOutputVoltage();
    }
    
    public double getRPM () {
        return PIDHelp.getRPM(shooterTalonLeft);
    }
    
    public double getVelocity () {
        return PIDHelp.getVelocity(shooterTalonLeft);
    }
    
    public void toVelocity (double velocity) {
        PIDHelp.toVelocity(shooterTalonLeft, velocity);
    }
    
    public void toRPM (double RPM) {
        PIDHelp.toRPM(shooterTalonLeft, RPM);
    }
    
    //FLY-WHEEL KICKER
    public void runFlyWheel () {
        flyWheel.set(Constants.flyWheelSpeed);
    }
    
    public void stopFlyWheel () {
        flyWheel.set(0);
    }
    
    @Override
    public void periodic () {}
    
}