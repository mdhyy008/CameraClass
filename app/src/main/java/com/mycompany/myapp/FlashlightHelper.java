package com.mycompany.myapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

public class FlashlightHelper {
    private boolean flashlightAvailable;
    private Camera camera;
    private CameraManager cameraManager;
    private String cameraId;

    public FlashlightHelper(Context context) {
        flashlightAvailable = context.getApplicationContext().getPackageManager()
			.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager = (CameraManager) context.getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
            try {
                if (cameraManager != null) {
                    cameraId = cameraManager.getCameraIdList()[0];
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启闪光灯
     *
     * @return 是否开启成功
     */
    public boolean open() {
        if (!flashlightAvailable) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager.setTorchMode(cameraId, true);
                return true;
            } catch (CameraAccessException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                camera = Camera.open();
                camera.startPreview();
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 关闭闪光灯
     *
     * @return 是否关闭成功
     */
    public boolean close() {
        if (!flashlightAvailable) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                cameraManager.setTorchMode(cameraId, false);
                return true;
            } catch (CameraAccessException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                if (camera != null) {
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                } else {
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
