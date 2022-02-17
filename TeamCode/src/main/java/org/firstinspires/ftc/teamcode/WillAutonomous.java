package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "Will Autonomous", group = "Noble")
public class WillAutonomous extends LinearOpMode {

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
        HardwareRobot robot = new HardwareRobot(hardwareMap, HardwareRobot.Mode.AUTONOMOUS);
     /*   DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        DcMotor duckMotor = hardwareMap.get(DcMotor.class, "duckMotor");
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "armMotor");


      */
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

        int position = -1;
        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intakeServo");
        DcMotor duckMotor = hardwareMap.get(DcMotor.class, "duckMotor");
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "armMotor");

        boolean foundDuckPosition = false;

        while (!foundDuckPosition) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());

                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        i++;

                        if (recognition.getLeft()>=1 && recognition.getRight()<=3){
                            position = 0;
                        }else if (recognition.getLeft()>=3 && recognition.getRight()<=5){
                            position = 1;
                        }else if (recognition.getLeft()>=5 && recognition.getRight()<=7){
                            position = 2;
                        }

                    }

                    if (position !=-1){
                        foundDuckPosition = true;
                        telemetry.addData("Which Position", "%.03f , %.03f", position);
                    }
                    telemetry.update();
                }
            }
        }

        //moves left
        robot.moveDistance(new HardwareRobot.DistanceVector(0, -4, 0, 1));
        TimeUnit.SECONDS.sleep(4);

        //spins duck wheel
        duckMotor.setPower(1);
        TimeUnit.SECONDS.sleep(3);
        duckMotor.setPower(0);


        //moves to in front of team shipping unit
        robot.moveDistance(new HardwareRobot.DistanceVector(0, 6, 0, 1));
        TimeUnit.SECONDS.sleep(6);

        robot.moveDistance(new HardwareRobot.DistanceVector(2, 0, 0, 1));
        TimeUnit.SECONDS.sleep(4);

        position = 1;
        //depending on position the arm deposits box in desired level
        if (position ==0){
            armMotor.setTargetPosition(720);
        }else if (position==1){
            armMotor.setTargetPosition(990);
        }else{
            armMotor.setTargetPosition(1260);
        }
        TimeUnit.SECONDS.sleep(3);


        //robot turns
        robot.moveDistance(new HardwareRobot.DistanceVector(0, 0, 3, 1));
        TimeUnit.SECONDS.sleep(5);

        //robot goes into cargo area

        robot.moveDistance(new HardwareRobot.DistanceVector(4, 0, 0, 1));
        TimeUnit.SECONDS.sleep(4);
    }

}