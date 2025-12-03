package frc.robot.subsystems.arm.armcommands;

import com.ctre.phoenix6.configs.Slot0Configs;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.Arm;

public class ArmTuning extends Command {
    
    private Slot0Configs slot0Configs;
    private Arm arm;
 
    public ArmTuning(){
        this.slot0Configs = slot0Configs;
        this.arm = arm;
    }

    public void execute(){
       arm.tune();
    }
}