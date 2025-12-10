package frc.robot.Commands;

import static edu.wpi.first.units.Units.Amp;
import static edu.wpi.first.units.Units.MetersPerSecond;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Elevator.Elevator;
import frc.robot.Elevator.ElevatorConstants;
import com.ctre.phoenix6.hardware.TalonFX;

public class HomeEncoder extends Command{
    public Elevator elevator;
    public TalonFX kLeftMotor;
    public TalonFX kRightMotor;

public HomeEncoder(Elevator elevator, TalonFX kLeftMotor, TalonFX kRightMotor){
    this.elevator = elevator;
    this.kLeftMotor = kLeftMotor;
    this.kRightMotor = kRightMotor;
}

public void execute(){
    elevator.setVoltage(ElevatorConstants.kHomingVoltage);
}

public boolean isFinished(){
    return (elevator.getCurrent().in(Amp) > ElevatorConstants.kHomingCurrent.in(Amp))
        && (elevator.getVelocity().in(MetersPerSecond) < ElevatorConstants.kHomingVelocity.in(MetersPerSecond));
}

}
