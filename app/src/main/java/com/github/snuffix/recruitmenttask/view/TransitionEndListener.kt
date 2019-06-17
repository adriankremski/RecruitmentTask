package com.github.snuffix.recruitmenttask.view

import androidx.transition.Transition


class TransitionEndListener(private val onTransitionEndCallback : (Transition) -> Unit) : Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition) {
        onTransitionEndCallback(transition)
    }

    override fun onTransitionResume(transition: Transition) {
    }

    override fun onTransitionPause(transition: Transition) {
    }

    override fun onTransitionCancel(transition: Transition) {
    }

    override fun onTransitionStart(transition: Transition) {
    }
}