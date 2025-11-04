package frc.robot.elevator;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.LinearAcceleration;

public class ElevatorConstants {
    //Elevator IDs
    public static final int kLeftMotorID = 13;
    public static final int kRightMotorID = 14;

    //Controller Configs
    public static final Distance kHeightTolerance = Meters.of(0.01);

    //Elevator Motor Configs
    public static final Current kStatorLimit = Amps.of(60);
    public static final Current kSupplyLimit = Amps.of(60);
    public static final boolean kLeftInverted = true;
    public static final boolean kRightInverted = true;
    public static final boolean kFollowerInverted = true;
    public static final LinearVelocity kMaxVelocity = MetersPerSecond.of(1.25);
    public static final LinearAcceleration kMaxAcceleration = MetersPerSecondPerSecond.of(5);

    //Elevator Physical Constants
    public static final double kElevatorGearing = 20;

    public static final Distance kElevatorConversion = Inches.of(0.375*2*13);

    //Constants for homeElevator
     public static final Voltage kHomingVoltage = Volts.of(-2);
     public static final Current kHomingCurrentThreshold = Amps.of(20);
     public static final LinearVelocity kHomingVelocityThreshold = MetersPerSecond.of(0.5);
     public static final Distance kElevatorMinimumHeight = Inches.of(35);
     public static final Distance kElevatorStartingHeight = kElevatorMinimumHeight;

     public static final Distance kElevatorDangerHeight = Meters.of(0.9);
} 
