// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Elevator.Elevator;
import frc.robot.Elevator.MoveElevator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final Elevator climber;
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    climber = new Elevator(30, 31);

    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    // Run the climber up with the right bumper
    m_driverController.rightBumper().whileTrue(new MoveElevator(climber, () -> 0.3));
    m_driverController.leftBumper().whileTrue((new MoveElevator(climber, () -> -0.3)));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Commands.print("Hello");
  }
}
