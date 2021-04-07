package android.content.pm;

import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PackageInstaller {

    public int createSession(SessionParams params) throws IOException {
        return 0;
    }

    public Session openSession(int sessionId) throws IOException {
        return null;
    }

    public void uninstall(String packageName,IntentSender statusReceiver) {
    }

    public List<SessionInfo> getStagedSessions() {
        return null;
    }

    public static class SessionParams implements Parcelable {

        public static final int MODE_FULL_INSTALL = 1;

        public SessionParams(int mode) {
        }

        protected SessionParams(Parcel in) {
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public void setAppPackageName(String appPackageName) {
        }

        public void setInstallAsApex() {
        }

        public void setStaged() {
        }

        public static final Creator<SessionParams> CREATOR = new Creator<SessionParams>() {


            @Override
            public SessionParams createFromParcel(Parcel in) {
                return new SessionParams(in);
            }

            @Override
            public SessionParams[] newArray(int size) {
                return new SessionParams[size];
            }
        };
    }

    public static class Session implements Closeable {
        @Override
        public void close() throws IOException {

        }

        public OutputStream openWrite(String name, long offsetBytes, long lengthBytes) throws IOException {
            return null;
        }

        public void fsync(OutputStream out) throws IOException {
        }

        public void commit(IntentSender statusReceiver) {

        }
    }

    public static class SessionInfo implements Parcelable {

        protected SessionInfo(Parcel in) {
        }

        public static final Creator<SessionInfo> CREATOR = new Creator<SessionInfo>() {
            @Override
            public SessionInfo createFromParcel(Parcel in) {
                return new SessionInfo(in);
            }

            @Override
            public SessionInfo[] newArray(int size) {
                return new SessionInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }
}
