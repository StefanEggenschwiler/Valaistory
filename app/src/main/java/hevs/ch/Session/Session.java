package hevs.ch.Session;


import android.app.Application;
import android.location.Location;

public class Session extends Application {

    // ---------------------------------------------------
    // Session values - updated as the app runs
    // ---------------------------------------------------

    private static Location currentLocationInfo;

    // ---------------------------------------------------


    /**
     * @return the currentLatitude
     */
    public static double getCurrentLatitude() {
        if (getCurrentLocationInfo() != null) {
            return getCurrentLocationInfo().getLatitude();
        } else {
            return 0;
        }
    }

    /**
     * Determines whether a valid location is available
     */
    public static boolean hasValidLocation() {
        return (getCurrentLocationInfo() != null && getCurrentLatitude() != 0 && getCurrentLongitude() != 0);
    }

    /**
     * @return the currentLongitude
     */
    public static double getCurrentLongitude() {
        if (getCurrentLocationInfo() != null) {
            return getCurrentLocationInfo().getLongitude();
        } else {
            return 0;
        }
    }

    /**
     * @param currentLocationInfo the latest Location class
     */
    public static void setCurrentLocationInfo(Location currentLocationInfo) {
        Session.currentLocationInfo = currentLocationInfo;
    }

    /**
     * @return the Location class containing latest lat-long information
     */
    private static Location getCurrentLocationInfo() {
        return currentLocationInfo;
    }
}