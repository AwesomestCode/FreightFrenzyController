package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.IOException;
import java.net.UnknownHostException;

@TeleOp(name = "Telemetry Testing", group = "Concept")
public class TelemetryTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        int port = 8887; // 843 flash policy port
        try {
            TelemetryService s = new TelemetryService(port);
            s.start();
            System.out.println("TelemetryService started on port: " + s.getPort());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        while (!isStopRequested()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
