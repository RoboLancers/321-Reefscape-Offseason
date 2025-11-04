package frc.robot.elevator;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volt;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Voltage;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import com.ctre.phoenix6.signals.NeutralModeValue;


public class Elevator {

    private boolean isHomed = false;

    private Distance targetHeight = ElevatorConstants.kElevatorStartingHeight;

    private Distance lastTargetHeight = ElevatorConstants.kElevatorStartingHeight;

    //creates motor objects
    public TalonFX elevatorMotorLeft = new TalonFX(ElevatorConstants.kLeftMotorID);
    public TalonFX elevatorMotorRight = new TalonFX(ElevatorConstants.kRightMotorID);

    public Elevator() {
        setupMotors();
        setupPID();
    }
    
    private void setupMotors() {
        TalonFXConfiguration configurationLeft =
            new TalonFXConfiguration()
                .withCurrentLimits(
                    new CurrentLimitsConfigs()
                        .withStatorCurrentLimit(ElevatorConstants.kStatorLimit)
                        .withStatorCurrentLimitEnable(true)
                        .withSupplyCurrentLimit(ElevatorConstants.kSupplyLimit)
                        .withSupplyCurrentLimitEnable(true))
                .withMotorOutput(
                    new MotorOutputConfigs()
                        .withInverted(
                            ElevatorConstants.kLeftInverted
                                ?InvertedValue.Clockwise_Positive
                                :InvertedValue.CounterClockwise_Positive)
                        .withNeutralMode(
                            NeutralModeValue.Brake))
                //Uses the Motors internal rotor sensor to give feedback to the motors position
                .withFeedback(
                    new FeedbackConfigs()
                        .withSensorToMechanismRatio(ElevatorConstants.kElevatorGearing)
                        .withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor));

        TalonFXConfiguration configurationRight =
            new TalonFXConfiguration()
                .withCurrentLimits(
                    new CurrentLimitsConfigs()
                        .withStatorCurrentLimit(ElevatorConstants.kStatorLimit)
                        .withStatorCurrentLimitEnable(true)
                        .withSupplyCurrentLimit(ElevatorConstants.kSupplyLimit)
                        .withSupplyCurrentLimitEnable(true))
                .withMotorOutput(
                    new MotorOutputConfigs()
                        .withInverted(
                            ElevatorConstants.kRightInverted
                                ?InvertedValue.Clockwise_Positive
                                :InvertedValue.CounterClockwise_Positive)
                            .withNeutralMode(
                                NeutralModeValue.Brake))
                .withFeedback(
                    new FeedbackConfigs()
                        .withSensorToMechanismRatio(ElevatorConstants.kElevatorGearing)
                        .withFeedbackSensorSource(FeedbackSensorSourceValue.RotorSensor))
                .withMotionMagic(
                    new MotionMagicConfigs()
                        .withMotionMagicCruiseVelocity(
                            convertMetersToRotations(
                                ElevatorConstants.kMaxVelocity.in(MetersPerSecond)))
                        .withMotionMagicAcceleration(
                            convertMetersToRotations(
                                ElevatorConstants.kMaxAcceleration.in(MetersPerSecondPerSecond))))
                .withSlot0(
                    new Slot0Configs()
                        .withGravityType(GravityTypeValue.Elevator_Static)
                        .withStaticFeedforwardSign(StaticFeedforwardSignValue.UseClosedLoopSign));

        //Applies these configs to each respective motor
        elevatorMotorLeft.getConfigurator().apply(configurationLeft);
        elevatorMotorRight.getConfigurator().apply(configurationRight);
    }

    public void setVoltage(Voltage Volts) {
        elevatorMotorLeft.setVoltage(Volts.in(Volt));
        elevatorMotorRight.setControl(
            new Follower(elevatorMotorRight.getDeviceID(), ElevatorConstants.kFollowerInverted));
    }

    public void goToPosition(Distance position) {
        lastTargetHeight = position;
        elevatorMotorRight.setControl(new MotionMagicVoltage(convertMetersToRotations(position.in(Meters))));
        elevatorMotorLeft.setControl(
            new Follower(elevatorMotorRight.getDeviceID(), ElevatorConstants.kFollowerInverted));
    }

    public void goToHeight (Distance targetHeight) {
        this.targetHeight = targetHeight;
        goToPosition(targetHeight);
    }

    public void setupPID(){
        elevatorMotorRight
        .getConfigurator()
            .apply(
                //Applies these constants to the right motor
                new Slot0Configs()
                .withGravityType(GravityTypeValue.Elevator_Static)
                .withKP(50)
                .withKI(0)
                .withKD(0)
                .withKG(0)
                .withKS(0)
                .withKA(0.01)
                .withKV(0.25)
                .withStaticFeedforwardSign(StaticFeedforwardSignValue.UseClosedLoopSign)
            );
    }

    //Converts meters to rotations
    public double convertMetersToRotations(double meters){
        return meters / ElevatorConstants.kElevatorConversion.in(Meters);
    }
    public Current getCurrent(){
        Current current = Amps.of(elevatorMotorRight.getStatorCurrent().getValueAsDouble());
        return current;
    }

    public LinearVelocity getVelocity(){
        LinearVelocity velocity =
        MetersPerSecond.of(
            elevatorMotorRight.getVelocity().getValueAsDouble()
                * ElevatorConstants.kElevatorConversion.in(Meters));
        return velocity;
    }

    public void resetEncoderPosition() {
        elevatorMotorLeft.setPosition(
            convertMetersToRotations(ElevatorConstants.kElevatorStartingHeight.in(Meters)));
        elevatorMotorRight.setPosition(
            convertMetersToRotations(ElevatorConstants.kElevatorStartingHeight.in(Meters)));
    }

    public void setIsHomed(Boolean value){
        isHomed = value;
    }
    public boolean getIsHomed() {
        return isHomed;
    }

    public boolean inCollisionZone() {
        if (getHeight() == null) return false;
        return getHeight().compareTo(ElevatorConstants.kElevatorDangerHeight) < 0;
    }

    public boolean atSetpoint() {
        boolean atSetpoint =
            Math.abs(getHeight().in(Meters) - lastTargetHeight.in(Meters))
                < ElevatorConstants.kHeightTolerance.in(Meters);
        return atSetpoint;
    }

    public boolean atHeight(Distance height) {
        boolean atHeight =
            Math.abs(getHeight().in(Meters) - height.in(Meters))
                < ElevatorConstants.kHeightTolerance.in(Meters);
        return atHeight;
    }

    public Distance getTargetHeight() {
        return targetHeight;
    }

    public Distance getHeight() {
        Distance height =
        Meters.of(
            elevatorMotorRight.getPosition().getValueAsDouble()
                * ElevatorConstants.kElevatorConversion.in(Meters));
        return height;
    }
}