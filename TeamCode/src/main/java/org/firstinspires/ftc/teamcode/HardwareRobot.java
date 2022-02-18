package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.concurrent.TimeUnit;

public class HardwareRobot {

    private final static double MULTIPLIER = 1440;

    private final HardwareMap hardwareMap;
    private final Mode mode;

    DcMotor armMotor;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor rearLeft;
    DcMotor rearRight;

    public HardwareRobot(HardwareMap hardwareMap, HardwareRobot.Mode mode) {
        this.hardwareMap = hardwareMap;

        armMotor = hardwareMap.get(DcMotor .class, "armMotor");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        rearRight = hardwareMap.get(DcMotor.class, "rearRight");
        this.mode = mode;

        if (mode == Mode.AUTONOMOUS) {
            armMotor.setTargetPosition(0);
            frontLeft.setTargetPosition(0);
            frontRight.setTargetPosition(0);
            rearLeft.setTargetPosition(0);
            rearRight.setTargetPosition(0);

            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (mode == Mode.TELEOP) {
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else if(mode == Mode.DYNAMIC) {
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            throw new RuntimeException("Invalid mode");
        }
    }

    public void moveDistance(DistanceVector vector) {
        double rawFrontLeft = vector.x;
        double rawFrontRight = vector.x;
        double rawRearLeft = vector.x;
        double rawRearRight = vector.x;

        rawFrontRight -= vector.z;
        rawRearLeft -= vector.z;
        rawFrontLeft += vector.z;
        rawRearRight += vector.z;

        frontLeft.setTargetPosition((int) (rawFrontLeft * -MULTIPLIER) + frontLeft.getCurrentPosition());
        frontRight.setTargetPosition((int) (rawFrontRight * MULTIPLIER) + frontRight.getCurrentPosition());
        rearLeft.setTargetPosition((int) (rawRearLeft * -MULTIPLIER) + rearLeft.getCurrentPosition());
        rearRight.setTargetPosition((int) (rawRearRight * MULTIPLIER) + rearRight.getCurrentPosition());

        frontLeft.setPower(vector.speed);
        frontRight.setPower(vector.speed);
        rearLeft.setPower(vector.speed);
        rearRight.setPower(vector.speed);
    }

    public void movePower(PowerVector vector) {
        double rawFrontLeft = vector.x;
        double rawFrontRight = vector.x;
        double rawRearLeft = vector.x;
        double rawRearRight = vector.x;

        rawFrontLeft -= vector.z; rawFrontRight += vector.z;
        rawRearLeft += vector.z;  rawRearRight -= vector.z;

        rawFrontRight += vector.rotation;
        rawRearRight += vector.rotation;
        rawFrontLeft -= vector.rotation;
        rawRearLeft -= vector.rotation;

        frontLeft.setPower(rawFrontLeft);
        frontRight.setPower(-rawFrontRight);
        rearLeft.setPower(rawRearLeft);
        rearRight.setPower(-rawRearRight);

        System.out.println(rawFrontLeft);
        System.out.println(rawFrontRight);
        System.out.println(rawRearLeft);
        System.out.println(rawRearRight);
    }

    public void initialise() throws InterruptedException {
        moveDistance(new DistanceVector(-0.5, 0, 0, 1));
        TimeUnit.SECONDS.sleep(2);
        DcMotor armMotor = hardwareMap.get(DcMotor.class , "armMotor");
        armMotor.setTargetPosition(500);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(0.5);
        TimeUnit.SECONDS.sleep(3);





        DcMotor ferrisWheel = hardwareMap.get(DcMotor.class, "intakeFerrisWheel");
        ferrisWheel.setTargetPosition(-300);
        ferrisWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ferrisWheel.setPower(0.5);
        TimeUnit.SECONDS.sleep(1);
        armMotor.setPower(0.1);
        TimeUnit.SECONDS.sleep(1);
        armMotor.setPower(0);
        ferrisWheel.setPower(0);
    }

    public static class DistanceVector {
        double x;
        double z;
        double rotation;
        double speed;

        public DistanceVector(double x, double z, double rotation, double speed) {
            this.x = x;
            this.z = z;
            this.rotation = rotation;
            this.speed = speed;
        }
    }

    public static class PowerVector {
        double x;
        double z;
        double rotation;

        public PowerVector(double x, double z, double rotation) {
            this.x = x;
            this.z = z;
            this.rotation = rotation;
        }
    }

    enum Mode {
        AUTONOMOUS,
        DYNAMIC, TELEOP
    }
}
