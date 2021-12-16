package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Joyful Arm Thingy", group="Pushbot")
public class JoyfulArm extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();

        DcMotor motor = hardwareMap.get(DcMotor.class, "motorName");

        while(opModeIsActive()) {
            telemetry.addData("Analogue Stick Position", -gamepad1.right_stick_y + 1);
            telemetry.update();
            motor.setPower(-gamepad1.right_stick_y * (gamepad1.right_bumper ? -0.1 : 0.5));
        }
    }
}
