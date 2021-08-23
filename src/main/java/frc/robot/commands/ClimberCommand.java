// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

/**
 * @author Gabriel Seaver
 */
public class ClimberCommand extends CommandBase {
	
	private final Climber climber;
	
	private final DoubleSupplier
			liftSpeed,
			winchSpeed;
	
	/**
	 * Creates a climber command using buttons to control the winch and an axis to control the lift.
	 * 
	 * @param climber
	 * @param liftSpeed The lift speed, where {@code 1} represents up, and {@code -1} represents down.
	 * @param moveWinchUp Whether or not to move the winch upwards.
	 * @param moveWinchDown Whether or not to move the winch downwards.
	 */
	public ClimberCommand (Climber climber, DoubleSupplier liftSpeed, BooleanSupplier moveWinchUp, BooleanSupplier moveWinchDown) {
		this(	climber,
				liftSpeed,
				() -> moveWinchUp.getAsBoolean() ? 1 : (moveWinchDown.getAsBoolean() ? -1 : 0));
	}
	
	/**
	 * Creates a climber command using axis to control both the winch and the lift.
	 * 
	 * @param climber
	 * @param liftSpeed The lift speed, where {@code 1} represents up, and {@code -1} represents down.
	 * @param winchSpeed The winch speed, where {@code 1} represents up, and {@code -1} represents down.
	 */
	public ClimberCommand (Climber climber, DoubleSupplier liftSpeed, DoubleSupplier winchSpeed) {
		addRequirements(climber);
		
		this.climber = climber;
		
		this.liftSpeed = liftSpeed;
		this.winchSpeed = winchSpeed;
	}
	
	@Override
	public void initialize () {
		climber.stopLift();
		climber.stopWinch();
	}
	
	@Override
	public void execute () {
		climber.runLift(liftSpeed.getAsDouble());
		climber.runWinch(winchSpeed.getAsDouble());
	}
	
	@Override
	public void end (boolean interrupted) {
		climber.stopLift();
		climber.stopWinch();
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
	
}