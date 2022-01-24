package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
        } else if (mode == Mode.TELEOP){
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

        frontLeft.setTargetPosition((int) (rawFrontLeft * MULTIPLIER) + frontLeft.getCurrentPosition());
        frontRight.setTargetPosition((int) (rawFrontRight * -MULTIPLIER) + frontRight.getCurrentPosition());
        rearLeft.setTargetPosition((int) (rawRearLeft * MULTIPLIER) + rearLeft.getCurrentPosition());
        rearRight.setTargetPosition((int) (rawRearRight * -MULTIPLIER) + rearRight.getCurrentPosition());

        frontLeft.setPower(vector.speed);
        frontRight.setPower(vector.speed);
        rearLeft.setPower(vector.speed);
        rearRight.setPower(vector.speed);
    }

    

    public void movePower(double y1, double y2, double x1, double x2) {

        //these lines are probably not necessary

      /*  if (y1<=0.5 && y1>=-0.5){
            y1 = 0;
        }
        if (y2<=0.5 && y2>=-0.5){
            y2 = 0;
        }

        if (x1<=0.5 && x1>=-0.5){
            x1 =0;
        }

        if (x2<=0.5 && x2>=-0.5){
            x2 =0;
        }

       */



        double maxThreshold = 0.5;
        double adj = 1.4;
        double bound= 0.2;

        if ((x1<-bound && y1<-bound)||(x2<-bound && y2<-bound)||(x1>bound && y1>bound)||(x2>bound && y2>bound)){
            //10:30 & 4:30
            frontRight.setPower(-y1*adj);
            rearLeft.setPower(y1*adj);
            frontLeft.setPower(0);
            rearRight.setPower(0);

        }else if ((x1>bound && y1<-bound)||(x2>bound && y2<-bound)||(x1<-bound && y1>bound)||(x2<-bound && y2>bound)){
            //1:30 & 7:30
            frontLeft.setPower(y1*adj);
            rearRight.setPower(-y1*adj);
            frontRight.setPower(0);
            rearLeft.setPower(0);
        }else if (y1>=maxThreshold || y1<=-maxThreshold || y2>=maxThreshold || y2<=-maxThreshold ) {
            //tank movement
            frontLeft.setPower(y1);
            rearLeft.setPower(y1);
            frontRight.setPower(-y2);
            rearRight.setPower(-y2);
        }else if (x1>=maxThreshold || x1<=-maxThreshold || x2>=maxThreshold || x2<=-maxThreshold) {
            //strafe
            frontLeft.setPower(-x1);
            rearLeft.setPower(x1);
            frontRight.setPower(-x2);
            rearRight.setPower(x2);
        }else{
            frontLeft.setPower(0);
            rearLeft.setPower(0);
            frontRight.setPower(0);
            rearRight.setPower(0);
        }



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
        TELEOP
    }
}
