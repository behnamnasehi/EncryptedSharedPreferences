package com.behnamnasehi.encryptedsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.RequiresApi;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class EncryptedDataHolder {

    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_STORE_ALIAS = "MyEncryptedSharedPreferences";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @RequiresApi(api = Build.VERSION_CODES.M)
    private KeyGenParameterSpec createKeyGenParameterSpec() {
        return new KeyGenParameterSpec.Builder(
                KEY_STORE_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private MasterKey getMasterKey(Context context, KeyGenParameterSpec keyGenParameterSpec) throws GeneralSecurityException, IOException {
        return new MasterKey.Builder(context, KEY_STORE_ALIAS)
                .setKeyGenParameterSpec(keyGenParameterSpec)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public EncryptedDataHolder(Context context) {
        try {
            pref = androidx.security.crypto.EncryptedSharedPreferences.create(
                    context,
                    KEY_STORE_ALIAS,
                    getMasterKey(context, createKeyGenParameterSpec()),
                    androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            editor = pref.edit();
            editor.apply();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

    }

    public String getApiKey() {
        return pref.getString(KEY_API_KEY, "");
    }

    public void setApiKey(String apiKey) {
        editor.putString(KEY_API_KEY, apiKey);
        editor.apply();
        editor.commit();
    }


}
