package example.homework10.permissons;

public interface PermissionCallback {
    void permissionGranted(PermissionChecker.RuntimePermissions permission);
    void permissionDenied(PermissionChecker.RuntimePermissions permission);
}
