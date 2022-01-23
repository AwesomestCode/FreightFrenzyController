package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "Primitive Autonomous", group = "Concept")
// @Disabled
public class PrimitiveAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.AUTONOMOUS);
        robot.moveDistance(new HardwareRobot.DistanceVector(1, 0, 0, 1));
        TimeUnit.SECONDS.sleep(5);
        robot.moveDistance(new HardwareRobot.DistanceVector(-1, 0, 0, 1));
        TimeUnit.SECONDS.sleep(5);
        robot.moveDistance(new HardwareRobot.DistanceVector(1, 1, 0, 1));
        TimeUnit.SECONDS.sleep(5);
        robot.moveDistance(new HardwareRobot.DistanceVector(-1, -1, 0, 1));
        TimeUnit.SECONDS.sleep(5);
        robot.moveDistance(new HardwareRobot.DistanceVector(1, -1, 0, 1));
        TimeUnit.SECONDS.sleep(5);
        robot.moveDistance(new HardwareRobot.DistanceVector(-1, 1, 0, 1));
    }
}
