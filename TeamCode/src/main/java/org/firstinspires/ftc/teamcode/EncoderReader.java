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

        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ferrisWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(!isStopRequested()) {
            telemetry.addData("Arm Motor", armMotor.getCurrentPosition());
            telemetry.addData("Ferris Wheel", ferrisWheel.getCurrentPosition());

            telemetry.update();
        }
    }
}
