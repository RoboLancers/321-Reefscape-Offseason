package frc.robot.subsystems.arm.armcommands;

import java.util.concurrent.RejectedExecutionHandler;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.ArmConstants;

public class GoToL2 extends Command {
    
    private Arm armMotor;

    public GoToL2(Arm armMotor) {
        this.armMotor = armMotor;
    }

    public void execute(){
        armMotor.goToL2();
    }

    public boolean isFinished(){
        return armMotor.getAngle() == ArmConstants.kLevelTwoAngle;
    }
}
