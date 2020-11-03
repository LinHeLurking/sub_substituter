package online.ruin_of_future.sub_substituter

import tornadofx.Controller
import tornadofx.observableListOf
import tornadofx.sort
import java.io.File

class LogicalBridge : Controller() {
//    companion object {
//        private val alignmentFinder = AlignmentFinder()
//    }
//
//    fun findAlign(s: String, t: String): Pair<List<Int>, List<Int>> {
//        return alignmentFinder.align(s, t)
//    }

    private val acceptedSubtitleExtension = listOf<String>(
        "ass",
        "sub",
        "srt"
    )

    private val acceptedVideoExtension = listOf<String>(
        "mp4",
        "mkv"
    )

    val originalSubtitleFileList = observableListOf<File>()
    val originalVideoFileList = observableListOf<File>()
    val afterSubtitleFileList = observableListOf<File>()
    private var tested = false

    fun autoAddFiles(files: List<File>) {
        tested = false
        for (item in files) {
            if (item.extension in acceptedSubtitleExtension) {
                originalSubtitleFileList.add(item)
            } else if (item.extension in acceptedVideoExtension) {
                originalVideoFileList.add(item)
            }
        }
        originalSubtitleFileList.sort()
        originalVideoFileList.sort()
    }

    fun transferTest() {
        if (
            !tested &&
            originalSubtitleFileList.isNotEmpty() &&
            originalSubtitleFileList.size == originalVideoFileList.size
        ) {
            originalSubtitleFileList.sort()
            originalVideoFileList.sort()
            val path = originalSubtitleFileList[0]
                .canonicalPath
                .removeSuffix(originalSubtitleFileList[0].name)
            for (i in originalVideoFileList.indices) {
                val name = path +
                        originalVideoFileList[i].nameWithoutExtension +
                        ".${originalSubtitleFileList[i].extension}"
                println(name)
                afterSubtitleFileList.add(File(name))
            }
            tested = true
        } else {
            println("RUA!")
            afterSubtitleFileList.clear()
            tested = false
        }
    }

    fun transfer() {
        if (!tested) {
            transferTest()
        }
        for (i in afterSubtitleFileList.indices) {
            val fromFile = originalSubtitleFileList[i]!!
            val toFile = afterSubtitleFileList[i]!!
            fromFile.copyTo(toFile, true)
        }
    }
}