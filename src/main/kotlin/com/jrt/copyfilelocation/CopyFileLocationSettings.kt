package com.jrt.copyfilelocation

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*

@State(
    name = "CopyFileLocationSettings",
    storages = [Storage("copyFileLocationSettings.xml")]
)
@Service(Service.Level.APP)
class CopyFileLocationSettings : PersistentStateComponent<CopyFileLocationSettings.State> {

    data class State(
        var useRelativePaths: Boolean = true
    )

    private var myState = State()

    override fun getState(): State {
        return myState
    }

    override fun loadState(state: State) {
        myState = state
    }

    var useRelativePaths: Boolean
        get() = myState.useRelativePaths
        set(value) {
            myState.useRelativePaths = value
        }

    companion object {
        fun getInstance(): CopyFileLocationSettings {
            return ApplicationManager.getApplication().getService(CopyFileLocationSettings::class.java)
        }
    }
}