package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareRobot {

    private final HardwareMap hardwareMap;

    DcMotor armMotor;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor rearLeft;
    DcMotor rearRight;

    public HardwareRobot(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        armMotor = hardwareMap.get(DcMotor .class, "armMotor");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRight = hardwareMap.get(DcMotor.class, "rearRight");
    }

    public void moveDistance(DistanceVector vector) {

    }

    public void movePower(PowerVector vector) {

    }

    public class DistanceVector {
        double x;
        double z;
        double rotation;

        public DistanceVector(double x, double z, double rotation) {
            this.x = x;
            this.z = z;
            this.rotation = rotation;
        }
    }

    public class PowerVector {
        double x;
        double z;
        double rotation;

        public PowerVector(double x, double z, double rotation) {
            this.x = x;
            this.z = z;
            this.rotation = rotation;
        }
    }
}
