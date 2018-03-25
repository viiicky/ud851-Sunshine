package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by vikasprasad on 25/03/18.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count  = preferenceScreen.getPreferenceCount();
        for (int i=0; i<count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, preferenceScreen.getSharedPreferences().getString(preference.getKey(), ""));
            }
        }

    }

    private void setPreferenceSummary(Preference preference, Object value) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            preference.setSummary(listPreference.getEntries()[listPreference.findIndexOfValue((String) value)]);
            return;
        }
        preference.setSummary((String) value);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (!(preference instanceof CheckBoxPreference)) {
            setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
