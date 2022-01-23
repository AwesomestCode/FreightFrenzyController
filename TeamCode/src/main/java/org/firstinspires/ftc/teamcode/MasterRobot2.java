package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Master Code v0.2.0", group = "Master")
// @Disabled
public class MasterRobot2 extends LinearOpMode {

    // Define class members
    DcMotor armMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.TELEOP);

        // Connect to motor (Assume standard left wheel)
        // Change the text in quotes to match any motor name on your robot.
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        // Wait for the start button
        telemetry.addData(">", "Press Start to run Motors." );
        telemetry.update();
        waitForStart();

        armMotor.setTargetPosition(0);

        Servo intakeMotor = hardwareMap.get(Servo.class, "intakeServo");
        DcMotor duckMotor = hardwareMap.get(DcMotor.class, "duckMotor");

        // get a reference to our digitalTouch object.
        DigitalChannel digitalTouch = hardwareMap.get(DigitalChannel.class, "intakeTouch");

        // set the digital channel to input.
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        waitForStart();

        int adjustment;
        int targetPosition = 0;

        while(opModeIsActive()) {
            // ARM LOGIC

            telemetry.addData("Motor Position Thingy", armMotor.getCurrentPosition());
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();
            //Thread.sleep(100);

            adjustment = (int) ((gamepad2.left_trigger) * 120);

            if(gamepad2.x) {
                targetPosition = 720;
            } else if(gamepad2.y) {
                targetPosition = 990;
            } else if(gamepad2.b) {
                targetPosition = 1260;
            } else if (gamepad2.a){
                targetPosition = 0;
            }

            armMotor.setTargetPosition(targetPosition + adjustment);

            armMotor.setPower(gamepad2.right_bumper ? 0.25 : 0.05);

            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // DRIVE LOGIC

            telemetry.update();
            robot.movePower(new HardwareRobot.PowerVector(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x));

            // INTAKE LOGIC
            if(gamepad2.dpad_up) {
                if(!digitalTouch.getState()) {
                    telemetry.addData("Digital Touch", "Refusing to run to avoid damage");
                    intakeMotor.setPosition(intakeMotor.getPosition());
                } else {
                    intakeMotor.setPosition(1.0);
                }
            } else if(gamepad2.dpad_down) {
                intakeMotor.setPosition(-1.0);
            } else if(gamepad2.left_bumper) {
                intakeMotor.setPosition(intakeMotor.getPosition());
            }
            telemetry.update();

            // DUCK LOGIC
            duckMotor.setPower(gamepad2.right_trigger);

            // Connect to motor (Assume standard left wheel)
            // Change the text in quotes to match any motor name on your robot.

        }

        // Turn off motor and signal done;
        armMotor.setPower(0);
        telemetry.addData(">", "Done");
        telemetry.update();

    }
}
