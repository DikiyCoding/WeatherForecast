package example.homework10.permissons;

public interface PermissionCallback {
    void permissionGranted(PermissionCheckerUtils.RuntimePermissions permission);
    void permissionDenied(PermissionCheckerUtils.RuntimePermissions permission);
}
