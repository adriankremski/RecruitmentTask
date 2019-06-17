package com.github.snuffix.recruitmenttask.extensions

import androidx.viewpager.widget.ViewPager

inline fun ViewPager.onPageChange(crossinline block: (Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            //Ignored
        }

        override fun onPageSelected(position: Int) {
            block.invoke(position)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            //Ignored
        }
    })
}