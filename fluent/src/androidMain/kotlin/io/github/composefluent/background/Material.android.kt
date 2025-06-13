package io.github.composefluent.background

import android.os.Build

internal actual fun supportMaterial(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}