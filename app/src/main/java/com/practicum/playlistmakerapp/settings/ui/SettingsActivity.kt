package com.practicum.playlistmakerapp.settings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.practicum.playlistmakerapp.R
import com.practicum.playlistmakerapp.settings.view_model.SettingsViewModel
import com.practicum.playlistmakerapp.util.router.SettingRouter
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsActivity : AppCompatActivity() {

    val viewModel by viewModel<SettingsViewModel>()
    val settingRouter by lazy {
        SettingRouter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.settings_toolbar)
        val shareButton = findViewById<LinearLayout>(R.id.shareButton)
        val supportButton = findViewById<LinearLayout>(R.id.supportButton)
        val termsButton = findViewById<LinearLayout>(R.id.termsButton)
        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)

        viewModel.getSwitcherStatusLiveData().observe(this) {
            themeSwitcher.isChecked = it
        }


        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.onSwitcherClicked(checked)
        }


        shareButton.setOnClickListener {
            settingRouter.openSharingActivity()
        }

        supportButton.setOnClickListener {
            settingRouter.openSupportActivity()
        }

        termsButton.setOnClickListener {
            settingRouter.openOfferActivity()
        }

        toolbar.setNavigationOnClickListener { settingRouter.onBackPressed() }

    }
}