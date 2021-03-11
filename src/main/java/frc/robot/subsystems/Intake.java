/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

/**
 * Adapted from https://github.com/frc1711/Nerites/blob/master/src/main/java/frc/robot/subsystems/Intake.java.
 * @author Lou DeZeeuw (original)
 * @author Gabriel Seaver (adaptation)
 */
public class Intake extends SubsystemBase {
    
    private final WPI_TalonSRX intakeTalon; 
    
    public Intake () {
        intakeTalon = new WPI_TalonSRX(Constants.intake); 
    }
    
    public void stop () {
        intakeTalon.set(0); 
    }
    
    public void run (double speed) {
        intakeTalon.set(speed); 
    }
    
    @Override
    public void periodic () {}
    
}