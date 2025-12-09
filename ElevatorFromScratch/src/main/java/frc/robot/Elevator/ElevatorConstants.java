package frc.robot.Elevator;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorConstants extends SubsystemBase{

    public static Distance kStartingHeight = Inches.of(35);
    
    public static int kLeftMotorId = 1;
    public static int kRightMotorId = 2;

    public static Distance kElevatorConversion = Inches.of(1);

    public static Distance kElevatorMinHeight = Inches.of(35);
    public static Distance kElevatorMaxHeight = Inches.of(87.75);

    public static Boolean kLeftInverted = false;
    public static Boolean kRightInverted = false;

    public static Voltage kHomingVoltage = Volts.of(1);
    public static Current kHomingCurrent = Amps.of(1);
    public static LinearVelocity kHomingVelocity = MetersPerSecond.of(1);

}
