package com.satya.utilites.extentions

import android.net.Uri
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.UriUtils
import com.satya.utilites.utilities.Toolkit
import java.io.File

fun String.file(): File = FileUtils.getFileByPath(this)

fun File.isFileExists() = FileUtils.isFileExists(this)
fun String.isFileExists() = FileUtils.isFileExists(this)

fun Uri.realPath() = com.satya.utilites.utilities.FileUtils.realPathFromURI(Toolkit.getTopActivityOrApp(), this)

fun Uri.toFile() = UriUtils.uri2File(this)
fun File.toUri() = UriUtils.file2Uri(this)