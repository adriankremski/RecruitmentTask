package com.github.snuffix.recruitmenttask

import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import com.github.snuffix.recruitmenttask.data.repository.DocumentsLocalSource
import org.koin.dsl.module
import java.util.*


@Suppress("unused") // Used from build.gradle.
class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(loader: ClassLoader, name: String, context: Context) = super.newApplication(loader, TestApp::class.java.name, context)!!
}

val testDocuments = List(6) {
    val calendar = Calendar.getInstance()
    Calendar.getInstance().set(Calendar.DAY_OF_YEAR, it)

    DocumentEntity(
        id = UUID.randomUUID().toString(),
        name = UUID.randomUUID().toString(),
        url = UUID.randomUUID().toString(),
        type = "pdf",
        description = UUID.randomUUID().toString(),
        creationDate = calendar.time
    )
}

class TestApp : RecruitmentTaskApp() {
    override val testModules = listOf(module {
        factory<DocumentsLocalSource>(override = true) {
            object : DocumentsLocalSource {
                override suspend fun hasCachedDocuments() = true

                override suspend fun saveDocuments(documents: List<DocumentEntity>) {
                }

                override suspend fun getDocument(documentId: String): DocumentEntity = testDocuments.first { it.id == documentId }

                override suspend fun getDocuments(): List<DocumentEntity> = testDocuments
            }
        }
    })
}
