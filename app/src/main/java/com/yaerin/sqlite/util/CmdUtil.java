package com.yaerin.sqlite.util;

import android.text.TextUtils;

import java.io.DataOutputStream;

/**
 * Created by wuchundu on 2019/12/31.
 */
public class CmdUtil {

    /**
     * 应用程序运行命令获取Root权限,拷贝文件，设备必须已破解(获得ROOT权限)
     * @return 应用程序是/否获取Root权限
     */
    public static boolean upgradeRootPermission(String path, String tager) {

        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(tager))
            return false;

        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + path;//修改文件读写权限
            String copy = "cp -rf " + path + " " + tager;//复制文件
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes(copy + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }
}
