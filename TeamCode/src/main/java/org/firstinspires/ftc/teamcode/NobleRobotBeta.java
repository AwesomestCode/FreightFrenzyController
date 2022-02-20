package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.concurrent.TimeUnit;

@TeleOp(name="NobleRobotBeta", group="Noble")
public class NobleRobotBeta extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.TELEOP);
        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        DcMotor ferrisMotor = hardwareMap.get(DcMotor.class, "intakeFerrisWheel");
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        DcMotor duckMotor = hardwareMap.get(DcMotor.class, "duckMotor");

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        duckMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armMotor.setTargetPosition(0);
        ferrisMotor.setTargetPosition(0);

        ferrisMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        // Normal Code
        armMotor.setPower(0.1);
        ferrisMotor.setPower(0.50);

        waitForStart();

        final int[][] motorPositions = {
                {0, 0}, // Ground Level
                {960, 90}, // Front Side, Top level
                {2120, -40}, // Back Side, Top level
                {1000, 70}, // Hub Cap
                {208, 36} // Front Side, intake
        };

        int adjustment = 0;
        int targetPosition = 0;

        boolean enabled = false;

        while(opModeIsActive()) {
            if(gamepad2.left_bumper) enabled = true;
            adjustment = (int) ((gamepad2.left_trigger) * 360);
            if(gamepad2.a && enabled) {
                targetPosition = motorPositions[0][0]; // Ground Level
                ferrisMotor.setTargetPosition(motorPositions[0][1]);
            }
            else if(gamepad2.x && enabled) {
                targetPosition = motorPositions[1][0]; // Front Side, Top level
                ferrisMotor.setTargetPosition(motorPositions[1][1]);
            }
            else if(gamepad2.y && enabled) {
                targetPosition = motorPositions[2][0] + adjustment; // Back Side, Top level
                ferrisMotor.setTargetPosition(motorPositions[2][1]);
            }

            else if(gamepad2.b && enabled) {
                targetPosition = motorPositions[3][0]; // Hub Cap
                ferrisMotor.setTargetPosition(motorPositions[3][1]);
            }
            else if(gamepad2.dpad_left) {
                targetPosition = motorPositions[4][0]; // Front Side, Intake
                ferrisMotor.setTargetPosition(motorPositions[4][1]);
            }
            // robot.movePower(new HardwareRobot.PowerVector(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x));
            armMotor.setTargetPosition(targetPosition + adjustment);

            if(gamepad2.right_bumper) {
                armMotor.setPower(0.75);
            } else {
                armMotor.setPower(0.2);
            }

            if(gamepad2.dpad_up) {
                intakeMotor.setPower(0.5);
            } else if(gamepad2.dpad_down) {
                intakeMotor.setPower(-1);
            } else if(gamepad2.dpad_right) {
                intakeMotor.setPower(0);
            }

            duckMotor.setPower(gamepad2.left_stick_y * 0.3);

            robot.movePower(new HardwareRobot.PowerVector(Math.min(gamepad1.left_stick_y, 0.8), Math.min(gamepad1.left_stick_x, 0.8), gamepad1.right_stick_x));
        }
    }
}
