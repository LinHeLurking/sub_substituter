package online.ruin_of_future.sub_substituter

import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.input.TransferMode
import javafx.util.Callback
import tornadofx.*
import java.io.File

class Layout : View() {
    private val logicalBridge: LogicalBridge by inject()

    override val root = borderpane {
        prefWidth = 800.0
        prefHeight = 600.0

        val cellCallBack = Callback<ListView<File>, ListCell<File>> {
            object : ListCell<File>() {
                override fun updateItem(item: File?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (item == null || empty) {
                        ""
                    } else {
                        item.name
                    }
                }
            }
        }

        left = vbox {
            prefWidthProperty().bind(this@borderpane.widthProperty() / 2 - 20.0)
            text {
                text = "Original video Names"
                alignment = Pos.CENTER
            }
            val origVideoView = listview(logicalBridge.originalVideoFileList) {
                cellFactory = cellCallBack
                selectionModel.selectionMode = SelectionMode.MULTIPLE
            }

            hbox {
                alignment = Pos.CENTER
                prefWidthProperty().bind(this@vbox.prefWidthProperty() - 5.0)
                vbox {
                    button("Remove Selected") {
                        setOnAction {
                            logicalBridge.originalVideoFileList.removeAll(origVideoView.selectionModel.selectedItems)
                        }
                        prefWidthProperty().bind(this@hbox.prefWidthProperty() / 4 + 20.0)
                        alignment = Pos.CENTER
                    }
                    alignment = Pos.CENTER
                    prefWidthProperty().bind(this@hbox.prefWidthProperty() / 2)
                }
                vbox {
                    button("Clear") {
                        setOnAction {
                            logicalBridge.originalVideoFileList.clear()
                        }
                        prefWidthProperty().bind(this@hbox.prefWidthProperty() / 4 + 20.0)
                        alignment = Pos.CENTER
                    }
                    alignment = Pos.CENTER
                    prefWidthProperty().bind(this@hbox.prefWidthProperty() / 2)
                }
            }

            text {
                text = "Original Subtitle Names"
                alignment = Pos.CENTER
            }
            val origSubView = listview(logicalBridge.originalSubtitleFileList) {
                cellFactory = cellCallBack
                selectionModel.selectionMode = SelectionMode.MULTIPLE
            }

            hbox {
                alignment = Pos.CENTER
                prefWidthProperty().bind(this@vbox.prefWidthProperty() - 5.0)
                vbox {
                    button("Remove Selected") {
                        setOnAction {
                            logicalBridge.originalSubtitleFileList.removeAll(origSubView.selectionModel.selectedItems)
                        }
                        alignment = Pos.CENTER
                        prefWidthProperty().bind(this@hbox.prefWidthProperty() / 4 + 20.0)
                    }
                    alignment = Pos.CENTER
                    prefWidthProperty().bind(this@hbox.prefWidthProperty() / 2)
                }
                vbox {
                    button("Clear") {
                        setOnAction {
                            logicalBridge.originalSubtitleFileList.clear()
                        }
                        alignment = Pos.CENTER
                        prefWidthProperty().bind(this@hbox.prefWidthProperty() / 4 + 20.0)
                    }
                    alignment = Pos.CENTER
                    prefWidthProperty().bind(this@hbox.prefWidthProperty() / 2)
                }
            }

            setOnDragOver {
                it.acceptTransferModes(*TransferMode.ANY)
            }
            setOnDragDropped {
                if (it.dragboard.hasFiles()) {
                    logicalBridge.autoAddFiles(it.dragboard.files)
                }
            }
        }
        right = vbox {
            prefWidthProperty().bind(this@borderpane.widthProperty() / 2 - 10.0)
            text {
                text = "After"
                alignment = Pos.CENTER
            }
            listview(logicalBridge.afterSubtitleFileList) {
                prefHeightProperty().bind(this@borderpane.heightProperty() - 20.0)
                cellFactory = cellCallBack
            }
        }
        bottom = hbox {
            val ppt = prefWidthProperty()
            ppt.bind(this@borderpane.prefWidthProperty() - 10.0)
            button("Test") {
                alignment = Pos.CENTER
                prefWidthProperty().bind(ppt / 6)
                setOnAction {
                    logicalBridge.transferTest()
                }
            }
            button("Convert") {
                alignment = Pos.CENTER
                prefWidthProperty().bind(ppt / 6)
                setOnAction {
                    logicalBridge.transfer()
                }
            }
            alignment = Pos.CENTER
        }
    }
}