package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="NobleRobotBeta", group="Noble")
public class NobleRobotBeta extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.TELEOP);
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
                {2000, 0}, // Front Side, Top level
                {2500, 0}, // Back Side, Top level
                {3000, 0} // Back Side, Bottom level
        };

        while(opModeIsActive()) {
            if(gamepad1.a) {
                armMotor.setTargetPosition(motorPositions[0][0]); // Ground Level
                ferrisMotor.setTargetPosition(motorPositions[0][1]);
            }
            else if(gamepad1.x) {
                armMotor.setTargetPosition(motorPositions[1][0]); // Front Side, Top level
                ferrisMotor.setTargetPosition(motorPositions[1][1]);
            }
            else if(gamepad1.y) {
                armMotor.setTargetPosition(motorPositions[2][0]); // Back Side, Top level
                ferrisMotor.setTargetPosition(motorPositions[2][1]);
            }
            else if(gamepad1.b) {
                armMotor.setTargetPosition(motorPositions[3][0]); // Back Side, Bottom level
                ferrisMotor.setTargetPosition(motorPositions[3][1]);
            }
            // robot.movePower(new HardwareRobot.PowerVector(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x));

            if(gamepad1.right_bumper) {
                armMotor.setPower(0.5);
            } else {
                armMotor.setPower(0.1);
            }

            if(gamepad1.dpad_up) {
                intakeMotor.setPower(1);
            } else if(gamepad1.dpad_down) {
                intakeMotor.setPower(-1);
            } else if(gamepad1.dpad_right) {
                intakeMotor.setPower(0);
            }
        }
    }
}
