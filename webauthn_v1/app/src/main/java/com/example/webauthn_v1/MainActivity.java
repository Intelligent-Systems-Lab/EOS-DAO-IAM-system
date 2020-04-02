package com.example.webauthn_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import duo.labs.webauthn.Authenticator;
import duo.labs.webauthn.exceptions.VirgilException;
import duo.labs.webauthn.exceptions.WebAuthnException;
import duo.labs.webauthn.models.AttestationObject;
import duo.labs.webauthn.models.AuthenticatorGetAssertionOptions;
import duo.labs.webauthn.models.AuthenticatorMakeCredentialOptions;
import duo.labs.webauthn.models.PublicKeyCredentialSource;
import moe.feng.support.biometricprompt.BiometricPromptCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "Demo";
    Context context;

    Authenticator authenticator;
    AuthenticatorMakeCredentialOptions makeCredentialOptions;
    AttestationObject attestationObject;
    //CancellationSignal cancellationSignal;

    AuthenticatorGetAssertionOptions getAssertionOptions;
    BiometricPromptCompat mBiometricPrompt;
    CancellationSignal mCancellationSignal;
    BiometricPrompt.AuthenticationCallback mAuthenticationCallback;

    JSONObject obj;
    byte[] attestationObjectBytes;

    Button reg_bt;
    Button log_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);

        reg_bt = (Button) findViewById(R.id.reg);
        log_bt = (Button) findViewById(R.id.log);

        try {
            authenticator = new Authenticator(context, true, true);
        } catch (VirgilException e) {
            e.printStackTrace();
        }

        mBiometricPrompt = new BiometricPromptCompat.Builder(context)
        .setTitle("HI")
        .setSubtitle("NO")
        .setDescription("hahahahaha……")
        .setNegativeButton("Use password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(
                        context,
                        "Please。",
                        Toast.LENGTH_LONG).show();
            }
        })
        .build();

        reg_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.ACTIVITY_TAG, ">>>>REG<<<<");
                //mBiometricPrompt.authenticate(mCancellationSignal, myCallback);
                reg();
            }
        });

        log_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.ACTIVITY_TAG, ">>>>LOG<<<<");
                login();
            }
        });


    }

    public void reg(){
        try {
            obj = new JSONObject(loadJSONFromAsset());
            Log.d(MainActivity.ACTIVITY_TAG, obj.getString("Registration"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Log.d(MainActivity.ACTIVITY_TAG, ">>>OPEN.");

        try {
            //og.d(MainActivity.ACTIVITY_TAG, ">>>TRY.");
            makeCredentialOptions = AuthenticatorMakeCredentialOptions.fromJSON(obj.getString("Registration"));
            //AuthenticatorMakeCredentialOptions makeCredentialOptions = AuthenticatorMakeCredentialOptions.fromJSON();

            attestationObject = authenticator.makeCredential(makeCredentialOptions);
            // or if you want to require user verification and need the biometric dialog:
            //attestationObject = authenticator.makeCredential(makeCredentialOptions, context, cancellationSignal);
            attestationObjectBytes = attestationObject.asCBOR();
        } catch (VirgilException | JSONException | WebAuthnException e) {
            e.printStackTrace();
        }

    }

    public void login() {
        try {
            obj = new JSONObject(loadJSONFromAsset());
            Log.d(MainActivity.ACTIVITY_TAG, obj.getString("Login"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
        getAssertionOptions = AuthenticatorGetAssertionOptions.fromJSON(obj.getString("Login"));// AuthenticatorGetAssertionOptions
        //attestationObject = authenticator.getInternalAssertion(getAssertionOptions,);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //https://abhiandroid.com/programming/json
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("option.json");
            int size = ((InputStream) is).available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
