package com.jrt.copyfilelocation

import com.intellij.openapi.options.Configurable
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class CopyFileLocationConfigurable : Configurable {

    private val settings = CopyFileLocationSettings.getInstance()
    private var useRelativePathsCheckbox: com.intellij.ui.dsl.builder.Cell<javax.swing.JCheckBox>? = null

    override fun getDisplayName(): String = "Claude Code CP"

    override fun createComponent(): JComponent {
        val panel = panel {
            group("Path Options") {
                row {
                    useRelativePathsCheckbox = checkBox("Use relative paths")
                        .comment("When enabled, copies relative paths ")
                }
            }
        }
        return panel
    }

    override fun isModified(): Boolean {
        return useRelativePathsCheckbox?.component?.isSelected != settings.useRelativePaths
    }

    override fun apply() {
        useRelativePathsCheckbox?.component?.let {
            settings.useRelativePaths = it.isSelected
        }
    }

    override fun reset() {
        useRelativePathsCheckbox?.component?.isSelected = settings.useRelativePaths
    }
}