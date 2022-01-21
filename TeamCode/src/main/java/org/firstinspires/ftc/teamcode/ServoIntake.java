package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Servo Intake", group = "Concept")
// @Disabled
public class ServoIntake extends LinearOpMode {

    // Define class members
    Servo motor;

    @Override
    public void runOpMode() throws InterruptedException {

        // Connect to motor (Assume standard left wheel)
        // Change the text in quotes to match any motor name on your robot.
        motor = hardwareMap.get(Servo.class, "intakeServo");

        // get a reference to our digitalTouch object.
        DigitalChannel digitalTouch = hardwareMap.get(DigitalChannel.class, "intakeTouch");

        // set the digital channel to input.
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.dpad_up) {
                if(!digitalTouch.getState()) {
                    telemetry.addData("Digital Touch", "Refusing to run to avoid damage");
                    motor.setPosition(motor.getPosition());
                } else {
                    motor.setPosition(1.0);
                }
            } else if(gamepad1.dpad_down) {
                motor.setPosition(-1.0);
            } else if(gamepad1.left_bumper) {
                motor.setPosition(motor.getPosition());
            }
            telemetry.update();
        }

        // Turn off motor and signal done;
        telemetry.addData(">", "Done");
        telemetry.update();

    }
}
