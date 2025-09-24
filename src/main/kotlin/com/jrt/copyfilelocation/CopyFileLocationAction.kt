package com.jrt.copyfilelocation

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import java.awt.datatransfer.StringSelection

class CopyFileLocationAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(e: AnActionEvent) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabled = virtualFile != null
    }

    override fun actionPerformed(e: AnActionEvent) {
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val editor = e.getData(CommonDataKeys.EDITOR)
        val project = e.project

        val filePath = getFilePath(virtualFile, project)
        val locationText = buildLocationString(filePath, editor)

        // Copy to clipboard
        CopyPasteManager.getInstance().setContents(StringSelection(locationText))

        // Show notification
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Copy File Location")
            .createNotification("File location copied to clipboard", locationText, NotificationType.INFORMATION)
            .notify(project)
    }

    private fun getFilePath(virtualFile: VirtualFile, project: Project?): String {
        val settings = CopyFileLocationSettings.getInstance()

        return if (settings.useRelativePaths && project != null) {
            getRelativePath(virtualFile, project) ?: virtualFile.path
        } else {
            virtualFile.path
        }
    }

    private fun getRelativePath(virtualFile: VirtualFile, project: Project): String? {
        val projectRootManager = ProjectRootManager.getInstance(project)
        val contentRoots = projectRootManager.contentRoots

        for (root in contentRoots) {
            if (virtualFile.path.startsWith(root.path)) {
                return virtualFile.path.substring(root.path.length + 1)
            }
        }

        // If not under content roots, try project base dir
        val projectBaseDir = project.basePath
        if (projectBaseDir != null && virtualFile.path.startsWith(projectBaseDir)) {
            return virtualFile.path.substring(projectBaseDir.length + 1)
        }

        return null
    }

    private fun buildLocationString(filePath: String, editor: Editor?): String {
        val baseLocation = "@$filePath"

        if (editor == null) {
            return baseLocation
        }

        val selectionModel = editor.selectionModel
        if (!selectionModel.hasSelection()) {
            return baseLocation
        }

        val document = editor.document
        val startOffset = selectionModel.selectionStart
        val endOffset = selectionModel.selectionEnd

        val startLine = document.getLineNumber(startOffset) + 1 // Convert to 1-based line numbers
        val endLine = document.getLineNumber(endOffset) + 1

        return if (startLine == endLine) {
            "$baseLocation#L$startLine"
        } else {
            "$baseLocation#L$startLine-$endLine"
        }
    }
}