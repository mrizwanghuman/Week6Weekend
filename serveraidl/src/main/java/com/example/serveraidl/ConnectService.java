package com.example.serveraidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ConnectService extends Service {
    ImpAidl impAidl = new ImpAidl();
    public ConnectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  impAidl;
    }
    private class ImpAidl extends IConnectAidlInterface.Stub{

        @Override
        public int Calculator(int num1, int num2) throws RemoteException {
            return num1+num2;
        }
    }
}
