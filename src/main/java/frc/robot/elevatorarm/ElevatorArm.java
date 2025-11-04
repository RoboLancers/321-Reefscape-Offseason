package frc.robot.elevatorarm;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorArm extends SubsystemBase{
    
    private PIDController pidController = new PIDController(0.25, 0, 0.002);
    private ArmFeedforward armFeedforward = new ArmFeedforward(0, 0.33, 0, 0);
    private TrapezoidProfile profile;
    private TrapezoidProfile.State setpointState = new TrapezoidProfile.State();
    private double goalAngle;
    private boolean hasSeeded = false;

}

public static ElevatorArm create(){
    return RobotBase.isReal()
}