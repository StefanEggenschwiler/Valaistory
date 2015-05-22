package hevs.ch.session;


import android.app.Application;

import com.google.android.gms.maps.model.CameraPosition;

public class Session extends Application {

    // ---------------------------------------------------
    // Session values - updated as the app runs
    // ---------------------------------------------------

    private static CameraPosition currentCameraPosition;

    // ---------------------------------------------------

    /**
     * Determines whether a valid cameraposition is available
     */
    public static boolean hasValidCameraPosition() {
        return (getCurrentCameraPosition() != null);
    }

    /**
     * @param currentCameraPosition the latest CameraPosition class
     */
    public static void setCurrentCameraPosition(CameraPosition currentCameraPosition) {
        Session.currentCameraPosition = currentCameraPosition;
    }

    /**
     * @return the CameraPosition class containing latest lat-long information
     */
    public static CameraPosition getCurrentCameraPosition() {
        return currentCameraPosition;
    }
}