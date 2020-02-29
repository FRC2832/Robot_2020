
package frc.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;

/**
* GripPipeline class.
*
* <p>An OpenCV pipeline generated by GRIP.
*
* @author GRIP
*/
public class Grip_Camera_settings {

	//Outputs
	private final Mat hsvThresholdOutput = new Mat();
    private final MatOfKeyPoint findBlobsOutput = new MatOfKeyPoint();

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * This is the primary method that runs the entire pipeline and updates the
     * outputs.
     */
    public void process(final Mat source0) {
        // Step HSV_Threshold0:
        final Mat hsvThresholdInput = source0;
        final double[] hsvThresholdHue = { 51.798561151079134, 180.0 };
        final double[] hsvThresholdSaturation = { 34.39748201438849, 132.65151515151513 };
        final double[] hsvThresholdValue = { 233.90287769784172, 255.0 };
        hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);

        // Step Find_Blobs0:
        final Mat findBlobsInput = hsvThresholdOutput;
        final double findBlobsMinArea = 20.0;
        final double[] findBlobsCircularity = { 0.0, 1.0 };
        final boolean findBlobsDarkBlobs = false;
        findBlobs(findBlobsInput, findBlobsMinArea, findBlobsCircularity, findBlobsDarkBlobs, findBlobsOutput);

    }

    private void findBlobs(final Mat findBlobsInput, final double findBlobsMinArea, final double[] findBlobsCircularity,
            final boolean findBlobsDarkBlobs, final MatOfKeyPoint findBlobsOutput2) {
    }

    private void hsvThreshold(final Mat hsvThresholdInput, final double[] hsvThresholdHue,
            final double[] hsvThresholdSaturation, final double[] hsvThresholdValue, final Mat hsvThresholdOutput2) {
    }
}
/** */