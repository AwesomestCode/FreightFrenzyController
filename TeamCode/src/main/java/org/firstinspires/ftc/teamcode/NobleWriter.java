package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@TeleOp(name="NobleAuthor", group="Noble")
public class NobleWriter extends LinearOpMode {

    List<Position> positions = new ArrayList<>();

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor armMotor = hardwareMap.get(DcMotor .class, "armMotor");
        DcMotor ferrisMotor = hardwareMap.get(DcMotor .class, "intakeFerrisWheel");
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        DcMotor rearRight = hardwareMap.get(DcMotor.class, "rearRight");

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ferrisMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.TELEOP);

        waitForStart();

        /*armMotor.setTargetPosition(0);
        ferrisMotor.setTargetPosition(0);
        frontLeft.setTargetPosition(0);
        frontRight.setTargetPosition(0);
        rearLeft.setTargetPosition(0);
        rearRight.setTargetPosition(0);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ferrisMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armMotor.setPower(0);
        ferrisMotor.setPower(0);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        rearLeft.setPower(0);
        rearRight.setPower(0); */

        Timer writeTimer = new Timer();
        Timer getController = new Timer();

        writeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                positions.add(new Position(0, armMotor.getCurrentPosition(), ferrisMotor.getCurrentPosition(), frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(), rearLeft.getCurrentPosition(), rearRight.getCurrentPosition(), frontLeft.getPower(), frontRight.getPower(), rearLeft.getPower(), rearRight.getPower()));
            }
        }, 0, 50);//put here time 1000 milliseconds=1 second

        telemetry.addData("Status", "Write Timer Started");
        telemetry.update();

        while(opModeIsActive())  {
            robot.movePower(new HardwareRobot.PowerVector(Math.min(gamepad1.left_stick_y, 0.8), Math.min(gamepad1.left_stick_x, 0.8), gamepad1.right_stick_x));
            if(gamepad1.left_bumper) break;
        }

        writeTimer.cancel();

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ferrisMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armMotor.setTargetPosition(0);
        ferrisMotor.setTargetPosition(0);
        frontLeft.setTargetPosition(0);
        frontRight.setTargetPosition(0);
        rearLeft.setTargetPosition(0);
        rearRight.setTargetPosition(0);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ferrisMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        final int[] i = {0};
        Timer readTimer = new Timer();
        readTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                armMotor.setTargetPosition(positions.get(i[0]).armPosition);
                ferrisMotor.setTargetPosition(positions.get(i[0]).ferrisPosition);
                frontLeft.setTargetPosition(positions.get(i[0]).frontLeftWheel);
                frontRight.setTargetPosition(positions.get(i[0]).frontRightWheel);
                rearLeft.setTargetPosition(positions.get(i[0]).backLeftWheel);
                rearRight.setTargetPosition(positions.get(i[0]).backRightWheel);
                i[0]++;
                armMotor.setPower(0.8);
                ferrisMotor.setPower(0.8);
                frontLeft.setPower(positions.get(i[0]).frontLeftPower - 0.1);
                frontRight.setPower(positions.get(i[0]).frontRightPower - 0.1);
                rearLeft.setPower(positions.get(i[0]).backLeftPower - 0.1);
                rearRight.setPower(positions.get(i[0]).backRightPower - 0.1);
            }
        }, 0, 50);//put here time 1000 milliseconds=1 second

        TimeUnit.MILLISECONDS.sleep(50L * positions.size() + 1000);
    }
    private class Position {
        int timestamp;
        int armPosition;
        int ferrisPosition;
        int frontLeftWheel;
        int frontRightWheel;
        int backLeftWheel;
        int backRightWheel;
        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        public Position(int timestamp, int armPosition, int ferrisPosition, int frontLeftWheel, int frontRightWheel, int backLeftWheel, int backRightWheel, double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
            this.timestamp = timestamp;
            this.armPosition = armPosition;
            this.ferrisPosition = ferrisPosition;
            this.frontLeftWheel = frontLeftWheel;
            this.frontRightWheel = frontRightWheel;
            this.backLeftWheel = backLeftWheel;
            this.backRightWheel = backRightWheel;
            this.frontLeftPower = frontLeftPower;
            this.frontRightPower = frontRightPower;
            this.backLeftPower = backLeftPower;
            this.backRightPower = backRightPower;
        }

    }
}
