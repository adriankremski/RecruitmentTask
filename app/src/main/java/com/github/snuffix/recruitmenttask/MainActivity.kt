package com.github.snuffix.recruitmenttask

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ToolbarContainer {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun attachToolbar(title: String?, toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        NavigationUI.setupWithNavController(toolbar, navHostFragment.findNavController())
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.title = title
    }
}
