// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Add your docs here. */
public class Climber extends SubsystemBase{
    private static final int leftMotorID = 1;
    private static final int rigthMotorID = 2;

    private final CANSparkMax rightMotor;
    private final CANSparkMax leftMotor;

    private final XboxController controller;



    public Climber(XboxController controller) {
        this.controller = controller;
        rightMotor = new CANSparkMax(rigthMotorID, MotorType.kBrushless);
        leftMotor = new CANSparkMax(leftMotorID, MotorType.kBrushless);

        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();

        leftMotor.setInverted(false); // Need to ask if I need to adjust
        rightMotor.setInverted(false);
    }

    @Override
    public void periodic(){
             // Control climber motors using Xbox controller bumpers
        if (controller.getRightBumper()) {
            // Right bumper pressed: Climber goes up
            leftMotor.set(0.5);
            rightMotor.set(0.5);
        } else if (controller.getLeftBumper()) {
            // Left bumper pressed: Climber goes down
            leftMotor.set(-0.5);
            rightMotor.set(-0.5);
        } else {
            // Stop the motors if no bumpers are pressed
            leftMotor.set(0);
            rightMotor.set(0);
        }
    }
}
