package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Confused Encoder Thingy v2", group = "Concept")
// @Disabled
public class EncoderThingy extends LinearOpMode {

    // Define class members
    DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {

        // Connect to motor (Assume standard left wheel)
        // Change the text in quotes to match any motor name on your robot.
        motor = hardwareMap.get(DcMotor.class, "motorName");

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the start button
        telemetry.addData(">", "Press Start to run Motors." );
        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {
            double newSpeed = motor.getCurrentPosition() * 0.001;
            motor.setPower(newSpeed);
            Thread.sleep(1);
            motor.setPower(0);
            // Display the current value
            telemetry.addData("Motor Position Thingy", motor.getCurrentPosition());
            telemetry.addData("Speed Thingy", newSpeed);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();
            Thread.sleep(9);
        }

        // Turn off motor and signal done;
        motor.setPower(0);
        telemetry.addData(">", "Done");
        telemetry.update();

    }
}
