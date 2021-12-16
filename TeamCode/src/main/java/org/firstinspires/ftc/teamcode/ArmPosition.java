package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Arm Encoder Thingy v2", group = "Concept")
// @Disabled
public class ArmPosition extends LinearOpMode {

    // Define class members
    DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {

        // Connect to motor (Assume standard left wheel)
        // Change the text in quotes to match any motor name on your robot.
        motor = hardwareMap.get(DcMotor.class, "motorName");

        // Wait for the start button
        telemetry.addData(">", "Press Start to run Motors." );
        telemetry.update();
        waitForStart();

        motor.setTargetPosition(0);


        while(opModeIsActive()) {

            telemetry.addData("Motor Position Thingy", motor.getCurrentPosition());
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();
            //Thread.sleep(100);

            if(gamepad1.x) {
                motor.setTargetPosition(720);
            } else if(gamepad1.y) {
                motor.setTargetPosition(990);
            } else if(gamepad1.b) {
                motor.setTargetPosition(1260);
            } else {
                motor.setTargetPosition(0);
            }
            motor.setPower(gamepad1.right_stick_button ? 0.75 : 0.25);

            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        // Turn off motor and signal done;
        motor.setPower(0);
        telemetry.addData(">", "Done");
        telemetry.update();

    }
}
