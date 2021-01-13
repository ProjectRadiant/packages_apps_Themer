/*
 * Copyright (C) 2020 ShapeShiftOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ssos.shapeshifter.fragments.ui;

import static android.os.UserHandle.USER_SYSTEM;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.UiModeManager;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.om.IOverlayManager;
import android.content.pm.ActivityInfo;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable;
import com.android.settingslib.search.SearchIndexable;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.ssos.shapeshifter.utils.AccentUtils;

import com.android.internal.logging.nano.MetricsProto;

import java.util.ArrayList;
import java.util.List;

public class PrebuiltAccents extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener, View.OnClickListener {

    private Context mContext;
    private IOverlayManager mOverlayManager;

    ImageView mcolorCheeseButton;
    ImageView mcolorRedButton;
    ImageView mcolorMagentaButton;
    ImageView mcolorTorchRedButton;
    ImageView mcolorDodgerBlueButton;
    ImageView mcolorSpaceButton;
    ImageView mcolorGreenButton;
    ImageView mcolorBlackButton;
    ImageView mcolorPurpleButton;
    ImageView mcolorOrchidButton;
    ImageView mcolorAquamarineButton;
    ImageView mcolorTangerineButton;
    ImageView mcolorPixelBlueButton;
    ImageView mcolorSuperNovaButton;
    ImageView mcolorTealButton;
    ImageView mcolorMIUIButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate accent preview layout
        return inflater.inflate(R.layout.accentPreviewThemed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set actionbar title
        getActivity().setTitle("Accent colours");

        mContext = getActivity();
        mOverlayManager = IOverlayManager.Stub.asInterface(
                ServiceManager.getService(Context.OVERLAY_SERVICE));

        lockCurrentOrientation(getActivity());

        // Capture our ImageViews from layout
        mcolorCheeseButton = (ImageView) view.findViewById(R.id.colorCheese);
        mcolorRedButton =  (ImageView) view.findViewById(R.id.colorRed);
        mcolorMagentaButton =  (ImageView) view.findViewById(R.id.colorMagenta);
        mcolorTorchRedButton =  (ImageView) view.findViewById(R.id.colorTorchRed);
        mcolorDodgerBlueButton =  (ImageView) view.findViewById(R.id.colorDodgerBlue);
        mcolorSpaceButton =  (ImageView) view.findViewById(R.id.colorSpace);
        mcolorGreenButton =  (ImageView) view.findViewById(R.id.colorGreen);
        mcolorBlackButton =  (ImageView) view.findViewById(R.id.colorBlack);
        mcolorPurpleButton =  (ImageView) view.findViewById(R.id.colorPurple);
        mcolorOrchidButton =  (ImageView) view.findViewById(R.id.colorOrchid);
        mcolorAquamarineButton =  (ImageView) view.findViewById(R.id.colorAquamarine);
        mcolorTangerineButton =  (ImageView) view.findViewById(R.id.colorTangerine);
        mcolorPixelBlueButton =  (ImageView) view.findViewById(R.id.colorPixelBlue);
        mcolorSuperNovaButton =  (ImageView) view.findViewById(R.id.colorSuperNova);
        mcolorTealButton =  (ImageView) view.findViewById(R.id.colorTeal);
        mcolorMIUIButton =  (ImageView) view.findViewById(R.id.colorMIUI);

        TextView previewText = (TextView) view.findViewById(R.id.previewText);
        if (com.android.internal.util.ssos.Utils.isThemeEnabled("co.shapeshiftos.theme.color.cheese")) {
            previewText.setText("Cheese");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("co.aospa.theme.color.red")) {
            previewText.setText("Red");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("co.aospa.theme.color.magenta")) {
            previewText.setText("Magenta");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("co.aospa.theme.color.torchred")) {
            previewText.setText("Torch Red");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("co.aospa.theme.color.dodgerblue")) {
            previewText.setText("Dodger Blue");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("com.android.theme.color.space")) {
            previewText.setText("Space");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("com.android.theme.color.green")) {
            previewText.setText("Green");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("com.android.theme.color.black")) {
            previewText.setText("Black");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("com.android.theme.color.orchid")) {
            previewText.setText("Orchid");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("com.android.theme.color.aquamarine")) {
            previewText.setText("Aquamarine");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("com.android.theme.color.tangerine")) {
            previewText.setText("Tangerine");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("co.aospa.theme.color.pixelblue")) {
            previewText.setText("Pixel Blue");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("co.aospa.theme.color.supernova")) {
            previewText.setText("Super Nova");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("co.aospa.theme.color.teal")) {
            previewText.setText("Teal");
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("com.android.theme.color.miui")) {
            previewText.setText("MIUI");
        } else {
            previewText.setText("Default");
        }

        // Register the onClick listener with the implementation above
        mcolorCheeseButton.setOnClickListener(this);
        mcolorRedButton.setOnClickListener(this);
        mcolorMagentaButton.setOnClickListener(this);
        mcolorTorchRedButton.setOnClickListener(this);
        mcolorDodgerBlueButton.setOnClickListener(this);
        mcolorSpaceButton.setOnClickListener(this);
        mcolorGreenButton.setOnClickListener(this);
        mcolorBlackButton.setOnClickListener(this);
        mcolorPurpleButton.setOnClickListener(this);
        mcolorOrchidButton.setOnClickListener(this);
        mcolorAquamarineButton.setOnClickListener(this);
        mcolorTangerineButton.setOnClickListener(this);
        mcolorPixelBlueButton.setOnClickListener(this);
        mcolorSuperNovaButton.setOnClickListener(this);
        mcolorTealButton.setOnClickListener(this);
        mcolorMIUIButton.setOnClickListener(this);

        // Bring all ImageViews to foreground to avoid obscuring
        mcolorCheeseButton.bringToFront();
        mcolorRedButton.bringToFront();
        mcolorMagentaButton.bringToFront();
        mcolorTorchRedButton.bringToFront();
        mcolorDodgerBlueButton.bringToFront();
        mcolorSpaceButton.bringToFront();
        mcolorGreenButton.bringToFront();
        mcolorBlackButton.bringToFront();
        mcolorPurpleButton.bringToFront();
        mcolorOrchidButton.bringToFront();
        mcolorAquamarineButton.bringToFront();
        mcolorTangerineButton.bringToFront();
        mcolorPixelBlueButton.bringToFront();
        mcolorSuperNovaButton.bringToFront();
        mcolorTealButton.bringToFront();
        mcolorMIUIButton.bringToFront();

    }

    @Override
    public void onClick(View view) {
           // do something when the ImageView is clicked
           int id = view.getId(); /*to get clicked view id**/
               if (id == R.id.colorCheese) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       // Sets the accent colour
                       enableAccentColor(mOverlayManager, "co.shapeshiftos.theme.color.cheese");
                       // Capture our accent name preview from layout
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       // Set accent name in text preview
                       previewText.setText("Cheese");
               // Repeated for each accent
               } else if (id == R.id.colorRed) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "co.aospa.theme.color.red");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Red");
               } else if (id == R.id.colorMagenta) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "co.aospa.theme.color.magenta");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Magenta");
               } else if (id == R.id.colorTorchRed) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "co.aospa.theme.color.torchred");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Torch Red");
               } else if (id == R.id.colorDodgerBlue) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "co.aospa.theme.color.dodgerblue");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Dodger Blue");
               } else if (id == R.id.colorSpace) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "com.android.theme.color.space");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Space");
               } else if (id == R.id.colorGreen) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "com.android.theme.color.green");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Green");
               } else if (id == R.id.colorBlack) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "com.android.theme.color.black");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Black");
               } else if (id == R.id.colorPurple) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       setDefaultAccentColor(mOverlayManager);
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Default");
               } else if (id == R.id.colorOrchid) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "com.android.theme.color.orchid");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Orchid");
               } else if (id == R.id.colorAquamarine) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "com.android.theme.color.aquamarine");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Aquamarine");
               } else if (id == R.id.colorTangerine) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "com.android.theme.color.tangerine");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Tangerine");
               } else if (id == R.id.colorPixelBlue) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "co.aospa.theme.color.pixelblue");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Pixel Blue");
               } else if (id == R.id.colorSuperNova) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "co.aospa.theme.color.supernova");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Super Nova");
               } else if (id == R.id.colorTeal) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "co.aospa.theme.color.teal");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("Teal");
               } else if (id == R.id.colorMIUI) {
                       View inflatedView = getLayoutInflater().inflate(R.layout.accentPreviewThemed, null);
                       enableAccentColor(mOverlayManager, "com.android.theme.color.miui");
                       TextView previewText = (TextView) inflatedView.findViewById(R.id.previewText);
                       previewText.setText("MIUI");
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    public static void setDefaultAccentColor(IOverlayManager overlayManager) {
        for (int i = 0; i < AccentUtils.ACCENTS.length; i++) {
            String accent = AccentUtils.ACCENTS[i];
            try {
                overlayManager.setEnabled(accent, false, USER_SYSTEM);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static void enableAccentColor(IOverlayManager overlayManager, String accentPicker) {
        try {
            for (int i = 0; i < AccentUtils.ACCENTS.length; i++) {
                String accent = AccentUtils.ACCENTS[i];
                try {
                    overlayManager.setEnabled(accent, false, USER_SYSTEM);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            overlayManager.setEnabled(accentPicker, true, USER_SYSTEM);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void handleOverlays(String packagename, Boolean state, IOverlayManager mOverlayManager) {
        try {
            mOverlayManager.setEnabled(packagename,
                    state, USER_SYSTEM);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.CUSTOM_SETTINGS;
    }

    public static void lockCurrentOrientation(Activity activity) {
        int currentRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int orientation = activity.getResources().getConfiguration().orientation;
        int frozenRotation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        switch (currentRotation) {
            case Surface.ROTATION_0:
                frozenRotation = orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                break;
            case Surface.ROTATION_90:
                frozenRotation = orientation == Configuration.ORIENTATION_PORTRAIT
                        ? ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                        : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                break;
            case Surface.ROTATION_180:
                frozenRotation = orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                        : ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                break;
            case Surface.ROTATION_270:
                frozenRotation = orientation == Configuration.ORIENTATION_PORTRAIT
                        ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        : ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                break;
        }
        activity.setRequestedOrientation(frozenRotation);
    }
}
