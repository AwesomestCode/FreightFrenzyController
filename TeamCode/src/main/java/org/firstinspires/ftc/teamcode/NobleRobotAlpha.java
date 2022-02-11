package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="NobleRobotAlpha", group="Noble")
public class NobleRobotAlpha extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.TELEOP);
        DcMotor motor = hardwareMap.get(DcMotor.class, "intakeMotor");
        DcMotor ferrisMotor = hardwareMap.get(DcMotor.class, "intakeFerrisWheel");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while(opModeIsActive()) {
            if(gamepad1.a) {
                motor.setPower(1);
            }
            else if(gamepad1.b) {
                motor.setPower(-1);
            }
            if(gamepad1.x) {
                ferrisMotor.setPower(0.25);
            }
            else if(gamepad1.y) {
                ferrisMotor.setPower(-0.25);
            }
            // robot.movePower(new HardwareRobot.PowerVector(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x));
        }
    }
}
