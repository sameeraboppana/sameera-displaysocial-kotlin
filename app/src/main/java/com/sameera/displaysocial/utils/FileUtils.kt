package com.sameera.displaysocial.utils

import android.content.Context
import java.io.File

class FileUtils {
    companion object{
        fun getImagePath(context : Context):File {
            return File(context.externalMediaDirs.first(), "image.png")
        }

        fun getVideoPath(context: Context): File? {
            return File(context.externalMediaDirs.first(), "video.mp4")
        }
    }
}