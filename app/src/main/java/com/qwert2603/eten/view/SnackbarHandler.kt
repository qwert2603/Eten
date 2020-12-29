package com.qwert2603.eten.view

interface SnackbarHandler {
    fun show(
        message: String,
        action: String? = null,
        onClick: (() -> Unit)? = null,
    )
}