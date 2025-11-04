package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.elevator.Elevator;
import frc.robot.elevator.ElevatorConstants;

import static edu.wpi.first.units.Units.Amp;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

import com.ctre.phoenix6.hardware.TalonFX;

public class HomeEncoder extends Command {
    private Elevator elevator;
    private TalonFX elevatorMotorLeft;
    private TalonFX elevatorMotorRight;

    public HomeEncoder(Elevator elevator, TalonFX elevatorMotorLeft, TalonFX elevatorMotorRight){
        this.elevator = elevator;
        this.elevatorMotorLeft = elevatorMotorLeft;
        this.elevatorMotorRight = elevatorMotorRight;
    }
    public void execute(){
        elevator.setVoltage(ElevatorConstants.kHomingVoltage);

        //if something catastrophic happens like no encoder...
        if(elevator.getCurrent() == null){
        end(true); //end cancelled
    
        }
    }

    @Override
    public boolean isFinished(){
        // When the elevator reaches the bottom it stalls the motor and causes a current spike.
        return (elevator.getCurrent().in(Amp) > ElevatorConstants.kHomingCurrentThreshold.in(Amp)
                    && Math.abs(elevator.getVelocity().in(MetersPerSecond))
                        < ElevatorConstants.kHomingVelocityThreshold.in(MetersPerSecond));
    }

    @Override
    public void end(boolean interrupted){

        if(interrupted == false){
            elevator.resetEncoderPosition();
            elevator.setIsHomed(true);
        }else{
            //TODO: interrupt behaviour
            elevatorMotorLeft.setVoltage(0);
            elevatorMotorRight.setVoltage(0);
        }
    }
}