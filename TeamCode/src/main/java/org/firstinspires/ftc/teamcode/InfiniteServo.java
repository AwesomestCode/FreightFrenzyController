package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Infinite Servo", group = "Concept")
public class InfiniteServo extends LinearOpMode {
    @Override
    public void runOpMode() {
        Servo servo = hardwareMap.get(Servo.class, "servoName");
        servo.getController().pwmEnable();

        waitForStart();

        while(opModeIsActive()) {
            servo.setDirection(gamepad1.right_bumper ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
            //servo.setPosition(1.0);
            // servo.
            // servo.setPosition(gamepad1.right_bumper ? 1.0 : -1.0);
        }
    }
}
