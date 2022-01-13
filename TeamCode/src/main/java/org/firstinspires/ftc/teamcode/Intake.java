package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "Intake Testing", group = "Concept")
// @Disabled
public class Intake extends LinearOpMode {

    // Define class members
    DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {

        // Connect to motor (Assume standard left wheel)
        // Change the text in quotes to match any motor name on your robot.
        motor = hardwareMap.get(DcMotor.class, "intakeMotor");

        // get a reference to our digitalTouch object.
        DigitalChannel digitalTouch = hardwareMap.get(DigitalChannel.class, "intakeTouch");

        // set the digital channel to input.
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.dpad_up) {
                if(!digitalTouch.getState()) {
                    telemetry.addData("Digital Touch", "Refusing to run to avoid damage");
                    motor.setPower(0);
                } else {
                    motor.setPower(1.0);
                }
            } else if(gamepad1.dpad_down) {
                motor.setPower(-1.0);
            } else if(gamepad1.left_bumper) {
                motor.setPower(0);
            }
            telemetry.update();
        }

        // Turn off motor and signal done;
        motor.setPower(0);
        telemetry.addData(">", "Done");
        telemetry.update();

    }
}
