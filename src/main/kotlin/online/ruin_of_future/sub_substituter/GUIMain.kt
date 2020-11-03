package online.ruin_of_future.sub_substituter

import tornadofx.App

class GUIMain : App(Layout::class) {
    companion object {
        var INSTANCE: GUIMain? = null
    }

    fun start(args: Array<String>) {
        if (INSTANCE == null) {
            INSTANCE = this
            tornadofx.launch<GUIMain>(args)
        } else {
            println("You already have an instance running")
        }
    }
}