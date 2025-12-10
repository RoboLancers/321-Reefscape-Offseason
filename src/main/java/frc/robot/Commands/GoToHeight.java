package frc.robot.Commands;

import frc.robot.Elevator.Elevator;
import frc.robot.Elevator.ElevatorConstants;

import static edu.wpi.first.units.Units.Meters;

import java.util.function.Supplier;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;

public class GoToHeight extends Command{
    public Elevator elevator;
    public TalonFX kRightMotor;
    public TalonFX kLeftMotor;

public GoToHeight(Elevator elevator, TalonFX kRightMotor, TalonFX kLeftMotor) {
    this.elevator = elevator;
    this.kRightMotor = kRightMotor;
    this.kLeftMotor = kLeftMotor;
}

public void execute(Supplier<Distance> targetHeight) {
    double setpoint = 
        MathUtil.clamp(
            targetHeight.get().in(Meters),
            ElevatorConstants.kElevatorMinHeight.in(Meters),
            ElevatorConstants.kElevatorMaxHeight.in(Meters));
    elevator.goToHeight(Meters.of(setpoint));
}

public boolean isFinished() {
    return true;
}
    
}
