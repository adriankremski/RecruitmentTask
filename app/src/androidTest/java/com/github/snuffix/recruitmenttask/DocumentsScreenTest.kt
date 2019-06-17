package com.github.snuffix.recruitmenttask

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.github.snuffix.recruitmenttask.data.model.DocumentEntity
import com.github.snuffix.recruitmenttask.presentation.model.DocumentSortOrder
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class DocumentsScreenTest {

    @get:Rule
    private val activityRule = ActivityTestRule(MainActivity::class.java)

    private val context
        get() = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setup() {
        activityRule.launchActivity(null)
    }

    @Test
    fun sortingByNameAscendingWillSortTheItems() {
        sortingBySelectedOrderWillSortTheItems(DocumentSortOrder.BY_NAME_ASC)
    }

    private fun sortingBySelectedOrderWillSortTheItems(sortOrder: DocumentSortOrder) {
        Espresso.openActionBarOverflowOrOptionsMenu(context)

        Espresso.onView(ViewMatchers.withText(sortOrder.displayName)).perform(ViewActions.click())

        val sortedDocuments = testDocuments.sortBy(sortOrder)

        Espresso.onView(withId(R.id.documentsRecycler)).perform(waitUntil(hasItemCount(Matchers.greaterThan(0))))

        sortedDocuments.forEachIndexed { index, document ->
            Espresso.onView(withId(R.id.documentsRecycler))
                .check(ViewAssertions.matches(atPosition(index, ViewMatchers.hasDescendant(ViewMatchers.withText(document.name)))))
        }
    }

    private fun List<DocumentEntity>.sortBy(sortOrder: DocumentSortOrder) = when (sortOrder) {
        DocumentSortOrder.BY_NAME_ASC -> this.sortedBy { it.name }
        DocumentSortOrder.BY_NAME_DESC -> this.sortedByDescending { it.name }
        DocumentSortOrder.BY_DATE_ASC -> this.sortedBy { it.creationDate }
        DocumentSortOrder.BY_DATE_DESC -> this.sortedByDescending { it.creationDate }
    }

    @Test
    fun sortingByNameDescendingWillSortTheItems() {
        sortingBySelectedOrderWillSortTheItems(DocumentSortOrder.BY_NAME_DESC)
    }

    @Test
    fun sortingByDateAscendingWillSortTheItems() {
        sortingBySelectedOrderWillSortTheItems(DocumentSortOrder.BY_DATE_ASC)
    }

    @Test
    fun sortingByDateDescendingWillSortTheItems() {
        sortingBySelectedOrderWillSortTheItems(DocumentSortOrder.BY_DATE_DESC)
    }
}