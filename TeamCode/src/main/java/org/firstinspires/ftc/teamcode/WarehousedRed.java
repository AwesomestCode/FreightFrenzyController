package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "Warehoused Red", group = "Concept")
// @Disabled
public class WarehousedRed extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.AUTONOMOUS);

        DcMotor motor = hardwareMap.get(DcMotor.class, "armMotor");

        waitForStart();

        motor.setTargetPosition(720);
        motor.setPower(0.2);

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.moveDistance(new HardwareRobot.DistanceVector(0, 2, 0, 1));

        TimeUnit.SECONDS.sleep(2);

        robot.moveDistance(new HardwareRobot.DistanceVector(5, 0, 0, 1));

        TimeUnit.SECONDS.sleep(10);
    }
}
