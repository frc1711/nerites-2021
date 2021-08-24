// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.basic;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants;
import frc.robot.subsystems.Pulley;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {
	
	private final Pulley pulley;
	private final Shooter shooter;
	
	private int numCellsLeft;
	private final int zoneNumber;
	
	/**
	 * 0 - Pulling cell until it reaches the top sensor and getting shooter up to speed
	 * 1 - Running kicker and stopping both kicker and shooter
	 * 2 - Decide whether to continue or to finish the auton
	 * 3 - Finish the auton
	 */
	private int step = 0;
	
	public Shoot (Pulley pulley, Shooter shooter, int numCellsToShoot, int zoneNumber) {
		addRequirements(pulley, shooter);
		
		this.pulley = pulley;
		this.shooter = shooter;
		this.numCellsLeft = numCellsToShoot;
		this.zoneNumber = zoneNumber;
	}
	
	@Override
	public void initialize () {
		pulley.stop();
		shooter.stopShooter();
		shooter.stopKicker();
	}
	
	@Override
	public void execute () {
		
		runShooter(); // Shooter should constantly be running until stopped at the end
		
		switch (step) {
			case 0:
				prepareToShootStep();
				break;
			case 1:
				runKickerStep();
				break;
			case 2:
				decideNextAction();
				break;
		}
		
	}
	
	private static final int shooterUpToSpeedTime = 30, kickerTimeToRun = 15;
	
	private int shooterTimer, kickerTimer;
	
	/**
	 * Pulls the cell to the shooter and runs the shooter for awhile so it can get up to speed.
	 */
	private void prepareToShootStep () {
		// Keep running the pulleys until a cell arrives at the shooter, stopping when finished
		if (!pulley.getTopSensor()) pulley.run(Constants.pulleySpeed);
		else pulley.stop();
		
		System.out.println(pulley.getTopSensor());
		
		// Advance the timer
		shooterTimer ++;
		
		// If the shooter has run for long enough, and a ball is ready for the shooter, then move on to the next step
		if (shooterTimer >= shooterUpToSpeedTime && pulley.getTopSensor()) {
			step ++; // Advance the step
			shooterTimer = 0; // Reset timer
		}
	}
	
	/**
	 * Runs the kicker, with the shooter still running, then stops both
	 */
	private void runKickerStep () {
		// Keep running the kicker
		shooter.runKicker();
		
		// Advance the timer
		kickerTimer ++;
		
		// If the kicker has run for the full time, move on to the next step
		if (kickerTimer >= kickerTimeToRun) {
			// Stop kicker and shooter
			shooter.stopKicker();
			shooter.stopShooter();
			
			step ++; // Advance the step
			kickerTimer = 0; // Reset timer
			
			numCellsLeft --; // Indicate that we have shot the cell
		}
	}
	
	/**
	 * Decide whether to finish the auton with step 3 or to shoot another cell at step 0.
	 */
	private void decideNextAction () {
		if (numCellsLeft > 0) step = 0;
		else step = 3;
	}
	
	private void runShooter () {
		// Runs the shooter at the correct speed
		shooter.shoot(Constants.shooterSpeedModes[zoneNumber - 1]);
	}
	
	@Override
    public void end (boolean interrupted) {
		pulley.stop();
		shooter.stopShooter();
		shooter.stopKicker();
    }
	
	@Override
	public boolean isFinished () {
		return step == 3;
	}
	
}