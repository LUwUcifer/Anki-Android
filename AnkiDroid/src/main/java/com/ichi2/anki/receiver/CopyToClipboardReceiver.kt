/*
 *  Copyright (c) 2026 LUwUcifer <luwucifwer@proton.me>
 *
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 3 of the License, or (at your option) any later
 *  version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 *  PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with
 *  this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ichi2.anki.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.ichi2.anki.notifications.NotificationId
import com.ichi2.utils.copyToClipboard
import timber.log.Timber

class CopyToClipboardReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val text =
            intent.getStringExtra(SYNC_ERROR_LOG) ?: run {
                Timber.w("CopyToClipboardReceiver: no error log found")
                return
            }
        NotificationManagerCompat.from(context).cancel(NotificationId.SYNC_MEDIA)
        context.copyToClipboard(text)
    }

    companion object {
        const val SYNC_ERROR_LOG = "COPY ERROR"
    }
}
