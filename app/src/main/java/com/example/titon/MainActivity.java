package com.example.titon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.UartDevice;
import com.google.android.things.pio.UartDeviceCallback;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner devicesSpinner;
    TextView baudRateTextView;
    TextView dataSizeTextView;
    TextView parityTextView;
    TextView stopBitTextView;
    TextView currentView;
    GridLayout gridLayout;

    List<String> inputDemo = new ArrayList<String>();
    String[] splittedValues;

    // Elerheto eszkozok listaja
    List<String> deviceList = new ArrayList<String>();

    /*
    // PLACEHOLDER! csatlakozashoz hasznalt eszkoz neve
    private final String UART_DEVICE_NAME = deviceList.get(0);

    // Csatlakozashoz hasznalt eszkoz peldanya
    private UartDevice mDevice;

   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baudRateTextView = findViewById(R.id.baudRateInput);
        dataSizeTextView = findViewById(R.id.dataSizeInput);
        parityTextView = findViewById(R.id.parityInput);
        stopBitTextView = findViewById(R.id.stopBitInput);

        gridLayout = findViewById(R.id.gridLayout);

        // Fill the inputDemo List
        fillDemoList();

        for (int i=0; i<inputDemo.size(); i++){
            splitInput(inputDemo.get(i));
            for (int j=0; j<7; j++){
                currentView = gridLayout.findViewWithTag(Integer.toString(j));
                currentView.setText(splittedValues[j]);
            }
        }

        //findDevice();
        setDropDownList();

        // Attempt to access the UART device
        /*
        try {
            PeripheralManager manager = PeripheralManager.getInstance();
            mDevice = manager.openUartDevice(UART_DEVICE_NAME);
        } catch (IOException e) {
            Log.w("Exception log", "Unable to access UART device", e);
        }
        */
    }

    public void connectToDevice (View view) {
        Toast.makeText(getApplicationContext(),
                "BaudRate: " + baudRateTextView.getText().toString() +
                " DataSize: " + dataSizeTextView.getText().toString() +
                " Parity: " + parityTextView.getText().toString() +
                " StopBit: " + stopBitTextView.getText().toString()
                , Toast.LENGTH_LONG).show();
    }

    public void setDropDownList() {
        deviceList.add("Device 1");
        deviceList.add("Device 2");

        devicesSpinner = findViewById(R.id.devicesSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, deviceList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        devicesSpinner.setAdapter(adapter);
    }

    public void fillDemoList() {
        inputDemo.add("0C0AD7 050A 1983 70594C 700788_FFFFF6 704739");
        inputDemo.add("0C0ACB 050A 1982 705946 7007BD 702744_FFFFE3");
        inputDemo.add("0C0AE2 050A 1982_FFFFEA 7007E1 702782 70477B");
        inputDemo.add("0C0AD7 050A 1983 70596A_FFFFF2 702783 704754");
        inputDemo.add("0C0AD4 0509 1984 705982 700839_FFFFE8 70478D");
    }

    public String[] splitInput(String input) {
        splittedValues = input.split(" |\\_");
        return splittedValues;
    }

    /*
    // Elerheto eszkozok keresese ------------------------------------------------------

    public void findDevice() {
        PeripheralManager manager = PeripheralManager.getInstance();
        deviceList = manager.getUartDeviceList();
        if (deviceList.isEmpty()){
            deviceList.add("Empty");
            Log.i("Log msg", "No UART port available on this device.");
        } else {
            Log.i("Log msg", "List of available devices: " + deviceList);
        }
    }

    // Eszkoz figyelese -------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        // Begin listening for interrupt events
        try {
            mDevice.registerUartDeviceCallback(uartCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Eszkoz figyelesenek leallitasa -----------------------------------------------

    @Override
    protected void onStop() {
        super.onStop();
        // Interrupt events no longer necessary
        mDevice.unregisterUartDeviceCallback(uartCallback);
    }

    private UartDeviceCallback uartCallback = new UartDeviceCallback() {
        @Override
        public boolean onUartDeviceDataAvailable(UartDevice uart) {
            // Read available data from UART device
            try {
                readUartBuffer(uart);
            } catch (IOException e) {
                Log.w("Exception log", "Unable to access UART device", e);
            }

            // Continue listening for more interrupts
            return true;
        }

        @Override
        public void onUartDeviceError(UartDevice uart, int error) {
            Log.w("Exception log", uart + ": Error event" + error);
        }
    };
    */

    /*

    // Csatlakozas beallitasa ----------------------------------------------------------

    public void configureUartFrame(UartDevice uart) throws IOException {
        // Configure the UART port
        uart.setBaudrate(115200);
        uart.setDataSize(8);
        uart.setParity(UartDevice.PARITY_NONE);
        uart.setStopBits(1);
    }

    // Eszkozre iras -------------------------------------------------------------------

    public void writeUartData(UartDevice uart) throws IOException {
        byte[] buffer = {1,0,1};
        int count = uart.write(buffer, buffer.length);
        Log.d("Write log", "Wrote" + count + " bytes to peripheral");
    }

    // Eszkozrol olvasas ---------------------------------------------------------------

    public void readUartBuffer(UartDevice uart) throws IOException {
        //Maximum amount of data to read at one time
        final int maxCount = 8;
        byte[] buffer = new byte[maxCount];

        int count;
        while ((count = uart.read(buffer, buffer.length)) > 0) {
            Log.d("Read log", "Read " + count + " bytes from peripheral");
        }
    }

    // Kapcsolat bontasa ---------------------------------------------------------------

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if (mDevice != null) {
            try {
                mDevice.close();
                mDevice = null;
            } catch (IOException e) {
                Log.w("Exception log", "Unable to close UART device", e);
            }
        }
    }
    */
}