package example.homework10.permissons;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class PermissionCheckerUtils {

    private boolean isPermissionGranted(Context context, final RuntimePermissions permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ActivityCompat.checkSelfPermission(context, permission.toStringValue()) == PackageManager.PERMISSION_GRANTED;
    }

    public void checkForPermissions(Activity activity, final RuntimePermissions permission, final PermissionCallback callback) {
        if (!isPermissionGranted(activity.getApplicationContext(), permission)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission.toStringValue())) {
                // Здесь показываем обоснование, почему необходимо разрешение
                callback.permissionDenied(permission);
            } else {
                // Запрос диалога пермишна
                ActivityCompat.requestPermissions(activity, new String[]{permission.toStringValue()}, permission.ordinal());
            }
        } else {
            // Права даны или АПИ < 23
            callback.permissionGranted(permission);
        }
    }

    public enum RuntimePermissions {

        PERMISSION_REQUEST_FINE_LOCATION {
            @Override
            public String toStringValue() {
                return android.Manifest.permission.ACCESS_FINE_LOCATION;
            }
            @Override
            public String showInformationMessage() {
                return null;
            }
        };

        public final int VALUE;

        public abstract String toStringValue();
        public abstract String showInformationMessage();

        RuntimePermissions() {
            VALUE = this.ordinal();
        }
    }
}
