package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Master Code v0.1.0", group = "Master")
// @Disabled
public class MasterRobot extends LinearOpMode {

    // Define class members
    DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {

        // Connect to motor (Assume standard left wheel)
        // Change the text in quotes to match any motor name on your robot.
        motor = hardwareMap.get(DcMotor.class, "armMotor");
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        DcMotor rearRight = hardwareMap.get(DcMotor.class, "rearRight");

        // Wait for the start button
        telemetry.addData(">", "Press Start to run Motors." );
        telemetry.update();
        waitForStart();

        motor.setTargetPosition(0);


        while(opModeIsActive()) {
            // ARM LOGIC

            telemetry.addData("Motor Position Thingy", motor.getCurrentPosition());
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();
            //Thread.sleep(100);

            if(gamepad2.x) {
                motor.setTargetPosition(720);
            } else if(gamepad2.y) {
                motor.setTargetPosition(990);
            } else if(gamepad2.b) {
                motor.setTargetPosition(1260);
            } else if (gamepad2.a){
                motor.setTargetPosition(0);
            }
            motor.setPower(gamepad2.right_bumper ? 0.75 : 0.25);

            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // DRIVE LOGIC

            telemetry.update();
            frontLeft.setPower(-gamepad1.left_stick_y);
            rearLeft.setPower(-gamepad1.left_stick_y);
            frontRight.setPower(gamepad1.right_stick_y);
            rearRight.setPower(gamepad1.right_stick_y);
        }

        // Turn off motor and signal done;
        motor.setPower(0);
        telemetry.addData(">", "Done");
        telemetry.update();

    }
}
