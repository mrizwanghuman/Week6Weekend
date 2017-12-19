package com.example.clientaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ClientMainActivity extends AppCompatActivity {
    private IConnectAidlInterface iConnectAidlInterface;
ServiceConnection serviceCon= new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        iConnectAidlInterface = IConnectAidlInterface.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
    }

    public void connectAidl(View view) {
        switch (view.getId()){
            case R.id.btnBindService:

                Intent intent= new Intent("com.example.serveraidl.AIDL");
               //intent.setPackage("com.example.serveraidl");
                bindService(convertImpLicitIntentToExplicitIntent(intent),serviceCon, BIND_AUTO_CREATE);
                break;

            case R.id.btnCalculation:
                try {
                    Log.d("MainActivity", "connectAidl: "+iConnectAidlInterface.Calculator(2,4));
                }catch (RemoteException e){
                    e.getStackTrace();
                    Log.d("error", "connectAidl: "+e.getMessage());
                }


                break;

        }
    }
public Intent convertImpLicitIntentToExplicitIntent(Intent implicitIntent){
    PackageManager pm = getPackageManager();
    List<ResolveInfo> resolveInfos = pm.queryIntentServices(implicitIntent,0);
    if (resolveInfos == null|| resolveInfos.size()!=1){
        return null;
    }
    ResolveInfo serviceInfo = resolveInfos.get(0);
    ComponentName componentName = new ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name);
    Intent intent = new Intent(implicitIntent);
    intent.setComponent(componentName);
    return intent;
}
}

