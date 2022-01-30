/*
 *  Copyright (C) 2021 Project Radiant
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.radiant.extras.fragments;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.Vibrator;
import androidx.preference.PreferenceCategory;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import com.radiant.support.preferences.SwitchPreference;
import androidx.preference.Preference;
import android.content.om.IOverlayManager;
import com.android.settings.dashboard.DashboardFragment;

import android.content.om.IOverlayManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.settings.development.OverlayCategoryPreferenceController;
import java.util.List;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

import com.android.settings.R;

import com.android.settings.SettingsPreferenceFragment;

import com.android.internal.logging.nano.MetricsProto;

public class MonetSettings extends DashboardFragment implements
        Preference.OnPreferenceChangeListener{

    private SwitchPreference mPitchPreference;
    IOverlayManager mOverlayManager;
    private static final String TAG = "MonetSettings";

    @Override
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, getSettingsLifecycle(), this);
    }

    private static List<AbstractPreferenceController> buildPreferenceControllers(
            Context context, Lifecycle lifecycle, Fragment fragment) {
        final List<AbstractPreferenceController> controllers = new ArrayList<>();
        controllers.add(new OverlayCategoryPreferenceController(context,
                "android.theme.customization.font"));
        return controllers;
    }

	@Override
    protected int getPreferenceScreenResId() {
        return R.xml.radiant_extras_monet;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        mPitchPreference = findPreference("pitch_theme");
        try {
            mPitchPreference.setChecked(mOverlayManager.getOverlayInfo("com.radiant.pitchsystem", UserHandle.USER_CURRENT).isEnabled());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mPitchPreference.setOnPreferenceChangeListener(this);
    }

    private void setOverlay(String overlay, boolean status) {
        try {
            mOverlayManager.setEnabled(overlay, status, UserHandle.USER_CURRENT);
        } catch (RemoteException | IllegalStateException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mPitchPreference) {
            setOverlay("com.radiant.pitchsystem", (Boolean) newValue);
            setOverlay("com.radiant.pitchsettings", (Boolean) newValue);
            setOverlay("com.radiant.pitchsystemui", (Boolean) newValue);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.Radiant;
    }

}
