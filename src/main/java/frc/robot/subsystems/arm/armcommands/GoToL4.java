package frc.robot.subsystems.arm.armcommands;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.ArmConstants;


public class GoToL4 extends Command {

    private Arm armMotor;

    public GoToL4(Arm armMotor){
        this.armMotor = armMotor;
    }
    
    public void execute(){
        armMotor.goToL4();

    }

    public boolean isFinished(){
        return armMotor.getAngle() == ArmConstants.kLevelFourAngle;
    }

  
}
