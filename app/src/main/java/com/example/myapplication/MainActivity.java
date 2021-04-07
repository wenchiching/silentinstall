package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void uninstall_chrome(View view) {
        Toast.makeText(view.getContext(), "uninstall chrome", Toast.LENGTH_LONG).show();
    }

    public void install_chrome(View view) throws IOException {
        String ACTION_INSTALL_COMPLETE = "com.android.intent.action.INSTALL_COMPLETE";
        try
        {
            PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(
                    PackageInstaller.SessionParams.MODE_FULL_INSTALL);
            PackageInstaller packageInstaller = view.getContext().getPackageManager().getPackageInstaller();
            int sessionId = packageInstaller.createSession(params);
            PackageInstaller.Session session = packageInstaller.openSession(sessionId);

            long sizeBytes = 0;
            final File file = new File("/data/local/tmp/com.cpuid.cpu_z.apk");
            if (file.isFile()) {
                sizeBytes = file.length();
            }

            InputStream in = null;
            OutputStream out = null;

            in = new FileInputStream(file);
            out = session.openWrite("package", 0, sizeBytes);

            int total = 0;
            byte[] buffer = new byte[65536];
            int c;
            while ((c = in.read(buffer)) != -1)
            {
                total += c;
                out.write(buffer, 0, c);
            }
            session.fsync(out);
            in.close();
            out.close();

            System.out.println("(Owen 1) InstallApkViaPackageInstaller streamed apk " + total + " bytes");

            session.commit(PendingIntent.getBroadcast(this, 0, new Intent(ACTION_INSTALL_COMPLETE), 0).getIntentSender());
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
