package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "Will The Robot Run B2", group = "Concept")
// @Disabled
public class AutonomousCodeB2 extends LinearOpMode {

    double INITIAL_STRAFE_ADJUSTMENT = 4;

    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    private static final String VUFORIA_KEY =
            "AYP7nvf/////AAABmT2VPXjDjEwxoKtorMB0orSJ2AtS0BDknPbRiZdeO24J/vAtmya+AugH2KfqjZzTCeyjH3iTrFaWURVY3mEXp0eHYVGijCbwjN/nr0yekZYvaNWrnz8+QDu42BLqhHaMKPt6dxsSJMJSxKcn+mbu7cPXiUWuXvpv7nLP6KpPdqqThd5Sg7IGe7L4Prjt1NbaKxhjZ25tzkW7gXY5lUu0Rrq5k49dE2grVIsVNqi+zOcj0ikcZqkARlxxD8fixoMnfiwGqjbnlWTKHb/5ypPOQG5AGPKLoQMEHO4s8xzB0NVNIqewYelCTW659fSa8YSOcBa1WdImwBhDlaY8BwXd7p74ccH+2oUft5akGCoz88Li";
    private TFObjectDetector tfod = null;
    private VuforiaLocalizer vuforia;

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        DcMotor ferrisMotor = hardwareMap.get(DcMotor.class, "intakeFerrisWheel");
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        DcMotor rearRight = hardwareMap.get(DcMotor.class, "rearRight");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rearRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.AUTONOMOUS);
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();
            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1, 16.0/9.0);
        }


        int position;
        final double findRot = 1.550;
        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        DcMotor duckMotor = hardwareMap.get(DcMotor.class, "duckMotor");

        waitForStart();

        robot.moveDistance(new HardwareRobot.DistanceVector(0.2, 0, 0, 1));
        TimeUnit.SECONDS.sleep(1);

        // moves right
        robot.moveDistance(new HardwareRobot.DistanceVector(0, -1.5 + (-INITIAL_STRAFE_ADJUSTMENT), 0, 1));
        TimeUnit.SECONDS.sleep(2);

        // spins duck wheel
        duckMotor.setPower(-0.3);
        TimeUnit.SECONDS.sleep(3);

        duckMotor.setPower(0);

        // moves to in front of team shipping unit
        robot.moveDistance(new HardwareRobot.DistanceVector(0, 3.6, 0, 1));
        TimeUnit.SECONDS.sleep(3);

        robot.moveDistance(new HardwareRobot.DistanceVector(0.5, 0, 0, 1));
        TimeUnit.SECONDS.sleep(1);

        // robot turns
        robot.moveDistance(new HardwareRobot.DistanceVector(0, 0, 2*findRot, 1));

        TimeUnit.SECONDS.sleep(2);

        position = 2;
        // depending on position the arm deposits box in desired level
        if (position == 0) {
            armMotor.setTargetPosition(50);
            ferrisMotor.setTargetPosition(-100);
        }else if (position == 1) {
            armMotor.setTargetPosition(410);
            ferrisMotor.setTargetPosition(-70);
        }else {
            armMotor.setTargetPosition(1050);
            ferrisMotor.setTargetPosition(90);
        }
        armMotor.setPower(1);
        ferrisMotor.setPower(0.5);
        TimeUnit.SECONDS.sleep(1);

        robot.moveDistance(new HardwareRobot.DistanceVector(-1.2, 0, 0, 1));
        TimeUnit.SECONDS.sleep(1);

        // push cargo
        intakeMotor.setPower(0.5);

        TimeUnit.SECONDS.sleep(2);
        intakeMotor.setPower(0);

        robot.moveDistance(new HardwareRobot.DistanceVector(0.5, 0, 0, 1));

        TimeUnit.SECONDS.sleep(1);

        robot.moveDistance(new HardwareRobot.DistanceVector(0, 0, -1*findRot, 1));

        TimeUnit.SECONDS.sleep(2);

        robot.moveDistance(new HardwareRobot.DistanceVector(-5, 0, 0, 1));

        TimeUnit.SECONDS.sleep(4);
        armMotor.setPower(0);
        ferrisMotor.setPower(0);

    }

}