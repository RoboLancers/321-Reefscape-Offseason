package frc.robot.Elevator;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Volt;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase{
    
    public Distance targetHeight = ElevatorConstants.kStartingHeight;

    public TalonFX kLeftMotor = new TalonFX(ElevatorConstants.kLeftMotorId);
    public TalonFX kRightMotor = new TalonFX(ElevatorConstants.kRightMotorId);

    public PIDController pidController = new PIDController(0, 0, 0);


    public static Elevator create(){
        return new Elevator();
    }

    public Elevator() {
        setupMotors();

    }
    
    // Code Review: Ypu could condense the configurations more (Do you need seperate configs for left and right motors?)
    private void setupMotors() {
        TalonFXConfiguration configurationLeft =
            new TalonFXConfiguration()
                .withCurrentLimits(
                    new CurrentLimitsConfigs()
                        .withStatorCurrentLimit(null)
                        .withStatorCurrentLimitEnable(false)
                        .withSupplyCurrentLimit(null)
                        .withStatorCurrentLimit(null))
                .withMotorOutput(
                    new MotorOutputConfigs()
                        .withInverted(null)
                        .withNeutralMode(null))
                .withFeedback(
                    new FeedbackConfigs()
                        .withFeedbackRemoteSensorID(0)
                        .withFeedbackSensorSource(null))
                .withMotionMagic(
                    new MotionMagicConfigs()
                        .withMotionMagicCruiseVelocity(null)
                        .withMotionMagicAcceleration(null)
                );
    

        TalonFXConfiguration configurationRight =
            new TalonFXConfiguration()
                .withCurrentLimits(
                    new CurrentLimitsConfigs()
                        .withStatorCurrentLimit(null)
                        .withStatorCurrentLimitEnable(false)
                        .withSupplyCurrentLimit(null)
                        .withStatorCurrentLimit(null))
                .withMotorOutput(
                    new MotorOutputConfigs()
                        .withInverted(null)
                        .withNeutralMode(null))
                .withFeedback(
                    new FeedbackConfigs()
                        .withFeedbackRemoteSensorID(0)
                        .withFeedbackSensorSource(null))
                .withMotionMagic(
                    new MotionMagicConfigs()
                        .withMotionMagicCruiseVelocity(null)
                        .withMotionMagicAcceleration(null))
                .withSlot0(
                    new Slot0Configs()
                        .withGravityType(null)
                        .withStaticFeedforwardSign(null)
                );

            kLeftMotor.getConfigurator().apply(configurationLeft);
            kRightMotor.getConfigurator().apply(configurationRight);
    }   
    
    // Change the boolean constant to inverted later  for both setVoltage and goToPosition
    public void setVoltage(Voltage Volts) {
        kRightMotor.setVoltage(Volts.in(Volt));
        kLeftMotor.setControl(
            new Follower(kRightMotor.getDeviceID(), ElevatorConstants.kRightInverted));
    }

    public void goToPosition(Distance position) {
        kRightMotor.setControl(new MotionMagicVoltage(convertMetersToRot(position.in(Meters))));
        kLeftMotor.setControl(
            new Follower(kRightMotor.getDeviceID(), ElevatorConstants.kRightInverted));
    }

    public Distance getPosition() {
        Distance height =
            Meters.of(kRightMotor.getPosition().getValueAsDouble() * ElevatorConstants.kElevatorConversion.in(Meters));
        return height;
    }

    public void goToHeight(Distance targetHeight) {
        this.targetHeight = targetHeight;
        goToPosition(targetHeight);
    }

    public LinearVelocity getVelocity() {
        LinearVelocity velocity =
            MetersPerSecond.of(kRightMotor.getVelocity().getValueAsDouble()
                * ElevatorConstants.kElevatorConversion.in(Meters));
        return velocity;
    }

    public Current getCurrent() {
        Current current = 
            Amps.of(kRightMotor.getStatorCurrent().getValueAsDouble());
        return current;
    }

    public void resetEncoderPosition() {
        kLeftMotor.setPosition(convertMetersToRot((ElevatorConstants.kStartingHeight).in(Meters)));
        kRightMotor.setPosition(convertMetersToRot((ElevatorConstants.kStartingHeight).in(Meters)));
    }

    public double convertMetersToRot(double meters) {
        return meters / ElevatorConstants.kElevatorConversion.in(Meters);
    }

    public void setOnboardPID(double kP, double kI, double kD, double kS, double kG, double kV, double kA) {
        kRightMotor.
            getConfigurator().
                apply(
                    new Slot0Configs()
                    .withGravityType(null)
                    .withKP(0)
                    .withKI(0)
                    .withKD(0)
                    .withKG(0)
                    .withKS(0)
                    .withKV(0)
                    .withStaticFeedforwardSign(null));
    }

    

    //code review: make command for go to height, add a tuning command using our current command structure, make commands for go to L2-4
    // what is the point of lastTargetHeight?
}
