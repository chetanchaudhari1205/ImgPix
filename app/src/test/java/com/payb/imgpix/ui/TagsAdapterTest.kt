package com.payb.imgpix.ui

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.payb.imgpix.databinding.ItemTagBinding
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever

/**
 * TagsAdapterTest test class
 */
@RunWith(MockitoJUnitRunner::class)
class TagsAdapterTest {

    private lateinit var tagsAdapter: TagsAdapter

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var listTags: List<String>
    @Mock
    lateinit var viewGroup: ViewGroup
    @Mock
    lateinit var itemTagBinding: ItemTagBinding
    @Mock
    lateinit var rootLayout: LinearLayout
    @Mock
    lateinit var viewHolder: TagsAdapter.ViewHolder

    @Before
    fun setup() {
        tagsAdapter = spy(TagsAdapter(listTags))
        tagsAdapter.binding = itemTagBinding
    }

    @Test
    fun testOnCreateViewHolder() {
        doReturn(itemTagBinding).whenever(tagsAdapter).getItemTagBinding(viewGroup)
        doReturn(rootLayout).whenever(itemTagBinding).root
        Assert.assertNotNull(tagsAdapter.onCreateViewHolder(viewGroup, 0))
    }

    @Test
    fun testGetItemCount() {
        Assert.assertNotNull(tagsAdapter.itemCount)
    }

    @Test
    fun testOnBindViewHolder() {
        doNothing().whenever(tagsAdapter).setBindingToUIComponents(viewHolder, "fruits, yellow")
        doReturn("fruits, yellow").whenever(listTags)[0]
        tagsAdapter.onBindViewHolder(viewHolder, 0)
    }

}