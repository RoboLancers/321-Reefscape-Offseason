package frc.robot.subsystems.arm.armcommands;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.ArmConstants;


public class GoToL3 extends Command {

    private Arm armMotor;
    
    public GoToL3(Arm armMotor){
        this.armMotor = armMotor;
    }

    public void execute(){
        armMotor.goToL3();
    }

    public boolean isFinished(){
        return armMotor.getAngle() == ArmConstants.kLevelThreeAngle;
    }
 }
