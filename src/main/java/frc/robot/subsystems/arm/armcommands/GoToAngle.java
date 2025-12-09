package frc.robot.subsystems.arm.armcommands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Arm;


public class GoToAngle extends Command {

    private Arm armMotor;
    private Angle angle;

    public GoToAngle(Arm armMotor, Angle angle){
    this.armMotor = armMotor;
    this.angle = angle;
    }
    
    public void initialize(){

    }

    public void execute(){
        armMotor.goToAngle(angle);

    }

    public boolean isFinished(){
        return armMotor.getAngle() == angle;
    }

  
}
