package com.tit.oxigenapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ObtenerDatos paciente = new ObtenerDatos();
        paciente.conectar();
        System.out.println(paciente.conectar());
    }
}
