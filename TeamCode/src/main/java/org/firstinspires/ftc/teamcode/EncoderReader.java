package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Encoder Reader", group = "Concept")
public class EncoderReader extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        DcMotor ferrisWheel = hardwareMap.get(DcMotor.class, "intakeFerrisWheel");
        DcMotor frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRightMotor = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor rearLeftMotor = hardwareMap.get(DcMotor.class, "rearLeft");
        DcMotor rearRightMotor = hardwareMap.get(DcMotor.class, "rearRight");

        armMotor.setTargetPosition(0);
        ferrisWheel.setTargetPosition(0);

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ferrisWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        armMotor.setPower(0);
        ferrisWheel.setPower(0);

        while(!isStopRequested()) {
            telemetry.addData("Arm Motor", armMotor.getCurrentPosition());
            telemetry.addData("Ferris Wheel", ferrisWheel.getCurrentPosition());
            telemetry.addData("Front Left", frontLeftMotor.getCurrentPosition());
            telemetry.addData("Front Right", frontRightMotor.getCurrentPosition());
            telemetry.addData("Back Left", rearLeftMotor.getCurrentPosition());
            telemetry.addData("Back Right", rearRightMotor.getCurrentPosition());

            telemetry.update();
        }
    }
}
