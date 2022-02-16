package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Will The Robot", group="Noble")
public class WillTheRobot extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        WillHardware robot = new WillHardware(hardwareMap, WillHardware.Mode.TELEOP);
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

            robot.tankMovement(gamepad1.left_stick_y, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
