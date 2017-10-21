package kr.co.namee.permissiongen_sample;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2017/6/17
 * 编写者：黄伟才
 * 功能描述：
 */

public class PermissionUtil {

    /**
     * 检查申请权限
     *
     * @param context
     * @param requestCode
     * @param permissions
     * @return
     */
    public static boolean checkPermission(Context context, int requestCode, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context,
                    permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }

        if (deniedPermissions.size() > 0) {
            //注意：多次重复执行权限数据请求会报错
            ActivityCompat.requestPermissions((Activity) context,
                    deniedPermissions.toArray(new String[deniedPermissions.size()]),
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

}
