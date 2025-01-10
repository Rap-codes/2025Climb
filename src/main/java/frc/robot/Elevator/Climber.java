// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.Elevator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
/** Add your docs here. */
public class Climber extends SubsystemBase{
    private static final int leftMotorID = 1;
    private static final int rigthMotorID = 2;

    private final CANSparkMax rightMotor;
    private final CANSparkMax leftMotor;


    public Climber(int leftMotorID, int rightMotorID) {
        rightMotor = new CANSparkMax(rigthMotorID, MotorType.kBrushless);
        leftMotor = new CANSparkMax(leftMotorID, MotorType.kBrushless);

        leftMotor.setInverted(false);

        rightMotor.follow(leftMotor, true);
    }

    // Method to set the speed of the left motor (right motor follows)
    public void setSpeed(double speed) {
        leftMotor.set(speed);
    }

    public void stop() {
        setSpeed(leftMotorID);
    }
}
