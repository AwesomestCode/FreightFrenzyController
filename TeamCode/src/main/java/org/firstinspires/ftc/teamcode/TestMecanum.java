package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TestMecanum", group="Test")
public class TestMecanum extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.TELEOP);
        waitForStart();
        while (opModeIsActive()) {
            robot.movePower(new HardwareRobot.PowerVector(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x));
        }
    }
}
