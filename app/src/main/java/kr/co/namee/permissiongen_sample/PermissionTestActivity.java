package kr.co.namee.permissiongen_sample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 创建时间：2017/6/17
 * 编写者：黄伟才
 * 功能描述：
 */

public class PermissionTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_test_activity);
    }

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    public void doClick2(View view) {
        if (PermissionUtil.checkPermission(this, MY_PERMISSIONS_REQUEST_CAMERA, Manifest.permission.CAMERA, Manifest.permission.WRITE_CONTACTS)) {
            //Toast.makeText(this, "可以执行任务", Toast.LENGTH_LONG).show();
            callPhone();
        }
    }

    //执行测试方法
    public void doClick1(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            //用户已经允许弹出权限请求
            callPhone();
        }
    }

    //拨打电话
    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);
                if (isTip) {
                    //用户取消权限请求，并没有彻底禁止弹出权限请求
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PermissionTestActivity.this);
                    dialog.setCancelable(false);
                    dialog.setMessage("权限使用解释");
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //再次请求权限
                            ActivityCompat.requestPermissions(PermissionTestActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    });
                    dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                        }
                    });
                    dialog.show();

                } else {
                    //用户彻底禁止弹出权限请求

                    //进入权限设置界面
                    Uri packageURI = Uri.parse("package:" + "kr.co.namee.permissiongen_sample");
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                    startActivity(intent);
                }

            } else {
                //用户允许弹出权限请求
                callPhone();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
