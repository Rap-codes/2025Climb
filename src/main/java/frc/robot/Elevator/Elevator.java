// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.Elevator;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;;
/** Add your docs here. */
public class Elevator extends SubsystemBase{
    private final SparkMax rightMotor;
    private final SparkMax leftMotor;
    private final AbsoluteEncoder leftAbsEncoder;

    double offset = 0;
    


    public Elevator(int leftMotorID, int rightMotorID) {
        leftMotor = new SparkMax(leftMotorID, MotorType.kBrushless);
        SparkMaxConfig leftMotorConfig = new SparkMaxConfig();
        leftMotorConfig
            .inverted(false)
            .idleMode(IdleMode.kBrake);
        leftMotorConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder);
        leftMotorConfig.absoluteEncoder.positionConversionFactor(Constants.Elevator.inPerRot);
        leftMotor.configure(leftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        rightMotor = new SparkMax(rightMotorID, MotorType.kBrushless);
        SparkMaxConfig rightMotorConfig = new SparkMaxConfig();
        rightMotorConfig
            // .inverted(true)
            .idleMode(IdleMode.kBrake)
            .follow(leftMotorID, true);

        rightMotor.configure(rightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        leftAbsEncoder = leftMotor.getAbsoluteEncoder();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("/Elevator/Height", getPosition());
        SmartDashboard.putNumber("/Elevator/Velocity", leftAbsEncoder.getVelocity());
    }

    // private boolean canMove(double speed) {
    //     double currentHeight = leftEncoder.getPosition() / ENCODER_TICKS_PER_INCH;
    //     if (speed > 0 && currentHeight >= MAX_HEIGHT) {
    //         return false; //prevnt moving up if already at max height
    //     }
    //     if (speed < 0 && currentHeight <= 0) {
    //         return false; //prevent moving down if already at min height
    //     }
    //     return true; //safe movement
    // }

      // Method to set the speed of the left motor (right motor follows)
    public void setSpeed(double speed) {
        // if (canMove(speed)) {
            leftMotor.set(speed);
        // } else {
            // stop();
        // }
    }

    public double getPosition() {
        return leftAbsEncoder.getPosition() - offset;
    }

    public void resetEncoder () {
        offset = getPosition();
    }

    public void stop() {
        setSpeed(0);
    }
}
