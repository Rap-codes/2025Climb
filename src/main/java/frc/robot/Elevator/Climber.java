// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.Elevator;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkClosedLoopController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;;
/** Add your docs here. */
public class Climber extends SubsystemBase{
    private final SparkMax rightMotor;
    private final SparkMax leftMotor;

    private final RelativeEncoder leftEncoder;

    private static final double MAX_HEIGHT = 62.5;
    private static final double ENCODER_TICKS_PER_INCH = 1.0; //Ticks per inch = Distance per rotation / Encoder ticks per rotation
    


    public Climber(int leftMotorID, int rightMotorID) {
        SparkMax rightMotor = new SparkMax(rightMotorID, MotorType.kBrushless);
        SparkMax leftMotor = new SparkMax(leftMotorID, MotorType.kBrushless);

        SparkMaxConfig leftMotorConfig = new SparkMaxConfig();
        leftMotorConfig.inverted(false);        

        SparkMaxConfig rightConfig = new SparkMaxConfig();
        rightConfig.inverted(true);
        rightConfig.follow(leftMotorID);

        leftEncoder = leftMotorID.getEncoder();

    }
    

    private boolean canMove(double speed) {
        double currentHeight = leftEncoder.getPosition() / ENCODER_TICKS_PER_INCH;
        if (speed > 0 && currentHeight >= MAX_HEIGHT) {
            return false; //prevnt moving up if already at max height
        }
        if (speed < 0 && currentHeight <= 0) {
            return false; //prevent moving down if already at min height
        }
        return true; //safe movement
    }

      // Method to set the speed of the left motor (right motor follows)
    public void setSpeed(double speed) {
        if (canMove(speed)) {
            leftMotor.set(speed);
        } else {
            stop();
        }
    }

    public void resetEncoder () {
        leftEncoder.setPosition(0);
    }

    public void stop() {
        setSpeed(0);
    }
}
