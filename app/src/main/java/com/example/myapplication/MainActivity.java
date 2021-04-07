

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
        //File file = new File("/data/local/tmp/cpuz.apk");
        //FileInputStream fileInputStream = new FileInputStream(file);
        //installPackage(getApplicationContext(), fileInputStream, "com.cpuid.cpu_z");
        //new DownloadFilesTask().execute();
        String ACTION_INSTALL_COMPLETE = "com.android.intent.action.INSTALL_COMPLETE";
        try
        {
            PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(
                    PackageInstaller.SessionParams.MODE_FULL_INSTALL);
            PackageInstaller packageInstaller = view.getContext().getPackageManager().getPackageInstaller();
            int sessionId = packageInstaller.createSession(params);
            PackageInstaller.Session session = packageInstaller.openSession(sessionId);
            // .. write updated APK file to out

            long sizeBytes = 0;
            final File file = new File("/data/local/tmp/com.cpuid.cpu_z.apk");
            if (file.isFile())
            {
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

            Intent intent = new Intent(ACTION_INSTALL_COMPLETE);
            intent.putExtra("package", "package.apk");
            intent.putExtra("path", "/data/local/tmp/com.cpuid.cpu_z.apk");

            PendingIntent broadCastTest = PendingIntent.getBroadcast(
                view.getContext(),
                0,
                intent,
                0
            );

            session.commit(PendingIntent.getBroadcast(this, 0, new Intent(ACTION_INSTALL_COMPLETE), 0).getIntentSender());
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class DownloadFilesTask extends AsyncTask<Void , Void, Void> {
        protected Void doInBackground(Void ...args) {
            try
            {
                PackageInstaller pi = getPackageManager().getPackageInstaller();
                int sessId          = pi.createSession(new PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL));

                PackageInstaller.Session session = pi.openSession(sessId);

                // .. write updated APK file to out


                long sizeBytes = 0;
                final File file = new File("/data/local/tmp/chrome.apk");
                if (file.isFile())
                {
                    sizeBytes = file.length();
                }

                InputStream in = null;
                OutputStream out = null;

                in = new FileInputStream("/data/local/tmp/chrome.apk");
                out = session.openWrite("package.apk", 0, sizeBytes);

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

                System.out.println("(Owen 2) InstallApkViaPackageInstaller streamed apk " + total + " bytes");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {

        }
    }

    public static boolean installPackage(Context context, InputStream in, String packageName)
            throws IOException {
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        params.setAppPackageName(packageName);
        // set params
        int sessionId = packageInstaller.createSession(params);
        PackageInstaller.Session session = packageInstaller.openSession(sessionId);
        OutputStream out = session.openWrite("COSU", 0, -1);
        byte[] buffer = new byte[65536];
        int c;
        while ((c = in.read(buffer)) != -1) {
            out.write(buffer, 0, c);
        }
        session.fsync(out);
        in.close();
        out.close();

        //Intent coucou = new Intent(this, MainActivity.class);
        //coucou.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //PendingIntent pCoucou = PendingIntent.getActivity(this, 0, coucou,0);
        //IntentSender mIntentSender = pCoucou.getIntentSender();
        //session.commit(createIntentSender(context, sessionId));
        return true;
    }
}
