package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="NobleRobotGamma", group="Noble")
public class NobleRobotGamma extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        WillHardware robot = new WillHardware(hardwareMap, WillHardware.Mode.TELEOP);
        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        DcMotor ferrisMotor = hardwareMap.get(DcMotor.class, "intakeFerrisWheel");
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ferrisMotor.setTargetPosition(0);
        armMotor.setTargetPosition(0);

        ferrisMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armMotor.setPower(0.1);
        ferrisMotor.setPower(0.30);

        waitForStart();

        final int[][] motorPositions = {
                {0, 0}, // Ground Level
                {960, 90}, // Front Side, Top level
                {2120, -40}, // Back Side, Top level
                {2800, -15}, // Back Side, Bottom level
                {208, 36} // Front Side, intake
        };

        int adjustment = 0;
        int targetPosition = 0;

        while(opModeIsActive()) {
            adjustment = (int) ((gamepad2.left_trigger) * 360);
            if(gamepad2.a && gamepad2.left_bumper) {
                targetPosition = motorPositions[0][0]; // Ground Level
                ferrisMotor.setTargetPosition(motorPositions[0][1]);
            }
            else if(gamepad2.x && gamepad2.left_bumper) {
                targetPosition = motorPositions[1][0]; // Front Side, Top level
                ferrisMotor.setTargetPosition(motorPositions[1][1]);
            }
            else if(gamepad2.y && gamepad2.left_bumper) {
                targetPosition = motorPositions[2][0] + adjustment; // Back Side, Top level
                ferrisMotor.setTargetPosition(motorPositions[2][1]);
            }
            else if(gamepad2.b && gamepad2.left_bumper) {
                targetPosition = motorPositions[3][0]; // Back Side, Bottom level
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
                intakeMotor.setPower(1);
            } else if(gamepad2.dpad_down) {
                intakeMotor.setPower(-1);
            } else if(gamepad2.dpad_right) {
                intakeMotor.setPower(0);
            }

            robot.tankMovement(gamepad1.left_stick_y, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        }
    }
}
