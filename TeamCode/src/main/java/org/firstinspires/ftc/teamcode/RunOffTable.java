package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Make Robot Run Off Table", group = "Concept")
// @Disabled
public class RunOffTable extends LinearOpMode {

    // Define class members
    // DcMotor motor;
    // double  power   = 0;
    // boolean rampUp  = true;


    @Override
    public void runOpMode() {

        // Connect to motor (Assume standard left wheel)
        // Change the text in quotes to match any motor name on your robot.
        String[] leftDriveNames = {"frontLeft", "rearLeft"};
        String[] rightDriveNames = {"frontRight", "rearRight"};

        DcMotor[] leftDrives = new DcMotor[2];
        DcMotor[] rightDrives = new DcMotor[2];
        for (int i = 0; i < 2; i++) {
            leftDrives[i] = hardwareMap.get(DcMotor.class, leftDriveNames[i]);
        }
        for (int i = 0; i < 2; i++) {
            rightDrives[i] = hardwareMap.get(DcMotor.class, rightDriveNames[i]);
        }

        // Wait for the start button
        telemetry.addData(">", "Press Start to run Motors.");
        telemetry.update();
        waitForStart();

        // Ramp motor speeds till stop pressed.
        /* while(opModeIsActive()) {

            // Ramp the motors, according to the rampUp variable.
            if (rampUp) {
                // Keep stepping up until we hit the max value.
                power += INCREMENT ;
                if (power >= MAX_FWD ) {
                    power = MAX_FWD;
                    rampUp = !rampUp;   // Switch ramp direction
                }
            }
            else {
                // Keep stepping down until we hit the min value.
                power -= INCREMENT ;
                if (power <= MAX_REV ) {
                    power = MAX_REV;
                    rampUp = !rampUp;  // Switch ramp direction
                }
            }

            // Display the current value
            telemetry.addData("Motor Position Thingy", motor.getCurrentPosition());
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();

            // Set the motor to the new power and pause;
            motor.setPower(power);
            sleep(CYCLE_MS);
            idle();
        }

        // Turn off motor and signal done;
        motor.setPower(0);
        telemetry.addData(">", "Done");
        telemetry.update();*/
        while (true) {
            for (DcMotor motor : rightDrives) {
                motor.setPower(2.0);
            }
            for (DcMotor motor : leftDrives) {
                motor.setPower(-2.0);
            }
        }
    }
}
