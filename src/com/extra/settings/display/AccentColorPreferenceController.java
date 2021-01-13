/*
 * Copyright (C) 2018 PotatoProject
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ssos.shapeshifter.display;

import android.content.ContentResolver;
import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import net.margaritov.preference.colorpicker.ColorPickerPreference;

public class AccentColorPreferenceController extends AbstractPreferenceController implements
        Preference.OnPreferenceChangeListener {

    private static final String ACCENT_COLOR = "accent_color";
    static final int DEFAULT_ACCENT_COLOR = 0xff725aff;
    private static final String ACCENT_OVERLAY = "prebuilt_accents_key";

    private ColorPickerPreference mAccentColor;
    private Preference mAccentOverlay;

    public AccentColorPreferenceController(Context context) {
        super(context);
    }

    @Override
    public String getPreferenceKey() {
        return ACCENT_COLOR;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mAccentColor = (ColorPickerPreference) screen.findPreference(ACCENT_COLOR);
        mAccentOverlay = (Preference) screen.findPreference(ACCENT_OVERLAY);
        mAccentColor.setOnPreferenceChangeListener(this);
        int intColor = Settings.System.getIntForUser(mContext.getContentResolver(),
                Settings.System.ACCENT_COLOR, DEFAULT_ACCENT_COLOR, UserHandle.USER_CURRENT);
        String hexColor = String.format("#%08x", (0xff725aff & intColor));
        if (hexColor.equals("#ff725aff")) {
            mAccentColor.setSummary(R.string.default_string);
            mAccentOverlay.setEnabled(true);
        } else {
            mAccentColor.setSummary(hexColor);
            mAccentOverlay.setEnabled(false);
        }
        mAccentColor.setNewPreviewColor(intColor);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mAccentColor) {
            String hex = ColorPickerPreference.convertToARGB(
                    Integer.valueOf(String.valueOf(newValue)));
            if (hex.equals("#ff725aff")) {
                mAccentColor.setSummary(R.string.default_string);
                mAccentOverlay.setEnabled(true);
            } else {
                mAccentColor.setSummary(hex);
                mAccentOverlay.setEnabled(false);
            }
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putIntForUser(mContext.getContentResolver(),
                    Settings.System.ACCENT_COLOR, intHex, UserHandle.USER_CURRENT);
            return true;
        }
        return false;
    }
}
