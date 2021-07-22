package com.example.myapplication;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yuechou.zhang
 * @since 2021/7/13
 */
public class BleActivity extends AppCompatActivity implements View.OnClickListener {

    private AudioRecord audioRecord;
    private int bufferSize;
    private final List<Map<String, ScanResult>> scanResults = new ArrayList<>();

    /**
     * 需要申请的运行时权限
     */
    private String[] permissions = new String[]{
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private ListView bleListView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble_activity);
        bleListView = findViewById(R.id.lv_ble);
        ActivityCompat.requestPermissions(this, permissions, 1001);
        initAr();
        initBle();
        startRecord();
    }

    private void initAdapter() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initBle() {
        BluetoothManager bleManager = getSystemService(BluetoothManager.class);
        BluetoothAdapter adapter = bleManager.getAdapter();
        Log.i("zyc", "initBle: 当前的蓝牙状态:" + adapter.getState());
        if (adapter.getState() == BluetoothAdapter.STATE_OFF) {
            adapter.enable();
        }
        Log.i("zyc", "initBle: 开始搜索.");
        BluetoothLeScanner bluetoothLeScanner = adapter.getBluetoothLeScanner();
        bluetoothLeScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                Log.i("zyc", "onScanResult" + callbackType);
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {

            }

            @Override
            public void onScanFailed(int errorCode) {
                Log.i("zyc", "onScanFailed: 搜索失败:" + errorCode);
                super.onScanFailed(errorCode);
            }
        });
    }

    private void initAr() {
        bufferSize = AudioRecord.getMinBufferSize(16000,
            AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, 16000, AudioFormat.CHANNEL_IN_STEREO,
            AudioFormat.ENCODING_PCM_16BIT, bufferSize);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startRecord() {
        byte[] buffer = new byte[bufferSize];
        audioRecord.startRecording();

        new Thread(() -> {
            File recordFile = new File(getDataDir(), "record.pcm");
            try {
                FileOutputStream fos = new FileOutputStream(recordFile);
                //创建文档
                if (!recordFile.exists()) {
                    boolean b = recordFile.createNewFile();
                    if (!b) Log.i("zyc", "startRecord: create Fail.");
                }
                //重复写入
                while (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                    int read = audioRecord.read(buffer, 0, bufferSize);
                    if (read >= 0) {
                        fos.write(buffer);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }

    private void stopRecord() {
        audioRecord.stop();
        audioRecord.release();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_start_recordd) {
            startRecord();
        } else if (id == R.id.btn_stop_record) {
            stopRecord();
        }
    }
}
