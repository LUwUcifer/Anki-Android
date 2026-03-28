/*
 * Copyright (c) 2026 LUwUcifer <luwucifwer@proton.me>
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.ichi2.anki.ui.windows.reviewer.whiteboard

import android.content.Context
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.view.View.MeasureSpec
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WhiteboardViewTest {
    private lateinit var context: Context
    private lateinit var view: WhiteboardView

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        view = WhiteboardView(context)
    }

    private fun sizeView(
        width: Int,
        height: Int,
    ) {
        view.measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY),
        )
        view.layout(0, 0, width, height)
    }

    /**
     * Creates a view of 1080x1920
     * Creates a Path in the shape of a rectangle along the boundaries of the view
     * Rotates the view to 1920x1080
     * If Path Matrix isn't transformed, the bottom or right edges will go out of bounds, failing the test
     */
    @Test
    fun `full-rect path bounds do not exceed screen bounds after resize`() {
        val initialWidth = 1080
        val initialHeight = 1920

        sizeView(initialWidth, initialHeight)

        val path =
            Path().apply {
                addRect(0f, 0f, initialWidth.toFloat(), initialHeight.toFloat(), Path.Direction.CW)
            }
        view.setHistory(
            listOf(DrawingAction(path, Color.BLACK, 4f, isEraser = false)),
        )

        val newWidth = 1920
        val newHeight = 1080

        sizeView(newWidth, newHeight)

        val bounds = RectF()
        path.computeBounds(bounds, true)

        assertTrue("right bound must not exceed new width ($newWidth)", bounds.right <= newWidth.toFloat())
        assertTrue("bottom bound must not exceed new height ($newHeight)", bounds.bottom <= newHeight.toFloat())
    }
}
