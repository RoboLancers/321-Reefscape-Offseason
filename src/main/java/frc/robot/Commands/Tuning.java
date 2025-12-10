package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Elevator.Elevator;
import util.TunableConstant;

import static edu.wpi.first.units.Units.Meters;


public class Tuning extends Command{

    public Elevator elevator;

    public TunableConstant kP = new TunableConstant("/Elevator/kP", 0);
    public TunableConstant kI = new TunableConstant("/Elevator/kI", 0);
    public TunableConstant kD = new TunableConstant("/Elevator/kD", 0);
    public TunableConstant kG = new TunableConstant("/Elevator/kG", 0);
    public TunableConstant kS = new TunableConstant("/Elevator/kS", 0);
    public TunableConstant kV = new TunableConstant("/Elevator/kV", 0);
    public TunableConstant kA = new TunableConstant("/Elevator/kA", 0);
    public TunableConstant targetHeight = new TunableConstant("/Elevator/targetHeight", 0);

public Tuning(Elevator elevator) {
    this.elevator = elevator;
}

public void init() {
    elevator.setOnboardPID(kP.get(), kI.get(), kD.get(), kG.get(), kS.get(), kV.get(), kA.get());
}

public void execute() {
    elevator.goToPosition(Meters.of(targetHeight.get()));
}

public boolean isFinished() {
    return true;
}

}
