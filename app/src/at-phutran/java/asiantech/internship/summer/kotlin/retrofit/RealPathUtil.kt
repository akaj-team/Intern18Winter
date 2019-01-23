package asiantech.internship.summer.kotlin.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.content.CursorLoader
import android.net.Uri
import android.provider.MediaStore

class RealPathUtil {
    @SuppressLint("NewApi")
    fun getRealPathFromUriAPI11to18(context: Context, contentUri: Uri): String? {
        val array = arrayOf(MediaStore.Images.Media.DATA)
        var result: String? = null
        val cursorLoader = CursorLoader(context, contentUri, array, null, null, null)
        val cursor = cursorLoader.loadInBackground()
        if (cursor != null) {
            val indexColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(indexColumn)
        }
        return result
    }
}