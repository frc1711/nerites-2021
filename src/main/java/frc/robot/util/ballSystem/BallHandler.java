/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util.ballSystem;

import java.util.ArrayList;

/**
 * Adapted from https://github.com/frc1711/Nerites/blob/master/src/main/java/frc/robot/helper_classes/BallHandler.java.
 * @author Lou DeZeeuw (original)
 * @author Gabriel Seaver (adaptation)
 */
public class BallHandler {
    
    ArrayList<Ball> ballList;
    
    public BallHandler () {
        ballList = new ArrayList<Ball>();
    }
    
    public void addBall (Ball ball) {
        ballList.add(ball);
    }
    
    public void addBall (Ball... ball) {
        for (Ball b : ball)
            ballList.add(b);
    }
    
    public void removeBall (int ballID) {
        for (Ball b : ballList)
            if (b.getID() == ballID)
                ballList.remove(ballList.indexOf(b));
    }
    
    public void removeHighestBall () {
        if (ballList.size() != 0)
            ballList.remove(ballList.size() -1);
    }
    
    public void removeAllBalls () {
        for (int i = 0; i < ballList.size(); i ++) {
            ballList.remove(i);
            System.out.println(i);
        }
    }
    
    public int numBallsInRobot () {
        return ballList.size();
    }
    
    public int totNumBallsHandled () {
        return Ball.getTotBall();
    }

    public Ball getLastBallHandled() {
        if (ballList.size() >= 1)
            return ballList.get(ballList.size() -1);
        return null;
    }

    public Ball getSecondToLastBallHandled() {
        if (ballList.size() >= 2)
            return ballList.get(ballList.size() -2);
        return null;
    }

}