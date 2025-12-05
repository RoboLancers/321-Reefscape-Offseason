package frc.robot.subsystems.arm;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.beans.Encoder;
import java.util.function.Supplier;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.arm.ArmConstants;
import frc.robot.util.TunableConstant;

import static edu.wpi.first.units.Units.Degrees;

public class Arm extends SubsystemBase {
    
    public TalonFX armMotor = new TalonFX(0);
    public PIDController pid = new PIDController (0,0,0);
    public ArmFeedforward armFeedForward = new ArmFeedforward(0,0,0);
    private TalonFXConfiguration talonConfigs = new TalonFXConfiguration( );
    private MotorOutputConfigs motorOutputConfigs = new MotorOutputConfigs();
    private CurrentLimitsConfigs currentLimitsConfigs = new CurrentLimitsConfigs();
    private VoltageConfigs voltageConfigs = new VoltageConfigs();
    private FeedbackConfigs feedbackConfigs = new FeedbackConfigs();
    private Slot0Configs slot0Configs =  new Slot0Configs();
    
    public double kP;
    public double kI;
    public double kD;
    public double kG;

    public Arm() {
        motorConfigurations();
    }

    /*Code Review(Michael): I would make the configs into one long list of configurations utilizing
      .with___ so you only need to set configurator to a motor once.*/
    private void motorConfigurations(){
        
        motorOutputConfigs.withInverted(InvertedValue.Clockwise_Positive);
        motorOutputConfigs.withNeutralMode(NeutralModeValue.Brake);
        currentLimitsConfigs.withStatorCurrentLimit(0) 
                            .withStatorCurrentLimitEnable(true);
        feedbackConfigs.withSensorToMechanismRatio(6.7);
        
        //PID configs
        slot0Configs.withKP(kP);
        slot0Configs.withKI(kI);
        slot0Configs.withKD(kD);
        slot0Configs.withKG(kG);

        armMotor.getConfigurator()
            .apply(motorOutputConfigs);

        armMotor.getConfigurator()
            .apply(currentLimitsConfigs);
        
        armMotor.getConfigurator()
            .apply(feedbackConfigs);

        armMotor.getConfigurator()
            .apply(slot0Configs);
    
    }

    public void setVoltage(Voltage Volts) {
        armMotor.setVoltage(0);
    }

    public void goToAngle(Angle angle){
        MotionMagicVoltage armVoltage = new MotionMagicVoltage(angle);
            armMotor.setControl(armVoltage);
    }

    public void goToL4(){
        MotionMagicVoltage armVoltage = new MotionMagicVoltage(ArmConstants.kLevelFourAngle);
            armMotor.setControl(armVoltage);
    }


    public void goToL3(){
        MotionMagicVoltage armVoltage = new MotionMagicVoltage(ArmConstants.kLevelThreeAngle);
            armMotor.setControl(armVoltage);
    }

    public void goToL2(){
        MotionMagicVoltage armVoltage = new MotionMagicVoltage(ArmConstants.kLevelTwoAngle);
            armMotor.setControl(armVoltage);
    }

    public Angle getAngle(){
        return Degrees.of(armMotor.getPosition().getValueAsDouble());
    }

    public void tune(){
        TunableConstant kP = new TunableConstant("/Arm/kP", 0);
        TunableConstant kI = new TunableConstant("/Arm/kI", 0);
        TunableConstant kD = new TunableConstant("/Arm/kD", 0);
        TunableConstant kG = new TunableConstant("/Arm/kG", 0);
        TunableConstant angle = new TunableConstant("/Angle/",0);

        this.kP = kP.get();
        this.kD = kD.get();
        this.kG = kG.get();

        goToAngle(Degrees.of(angle.get()));

    }

    //Degrees.of(double) = angle;
    //angle.in(Degrees) = double;    

    //kG, L2-3, Command for tuning
}
