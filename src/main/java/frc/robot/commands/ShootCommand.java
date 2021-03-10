// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

/**
 * @author Gabriel Seaver
 */
public class ShootCommand extends CommandBase {
    
    private final Shooter shooter;
    private final BooleanSupplier runShooter,
            runFlyWheel;
    
    public ShootCommand (Shooter _shooter, BooleanSupplier _runShooter, BooleanSupplier _runFlyWheel) {
        shooter = _shooter;
        runShooter = _runShooter;
        runFlyWheel = _runFlyWheel;
        addRequirements(shooter);
    }
    
    @Override
    public void initialize () {
        shooter.stopShooter();
        shooter.stopFlyWheel();
    }
    
    @Override
    public void execute () {
        // Shooter
        if (runShooter.getAsBoolean()) {
            shooter.toVelocity(Constants.shooterSpeed);
        } else {
            shooter.toVelocity(0);
        }
        
        // Fly wheel
        if (runFlyWheel.getAsBoolean() && Math.abs(shooter.getVelocity() - Constants.shooterSpeed) <= Constants.shooterSpeedBound) {
            shooter.runFlyWheel();
        } else {
            shooter.stopFlyWheel();
        }
    }
    
    @Override
    public void end (boolean interrupted) {
        shooter.stopShooter();
        shooter.stopFlyWheel();
    }
    
    @Override
    public boolean isFinished () {
        return false;
    }
    
}