package io.github.coroutineusecases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.coroutineusecases.base.BaseActivity
import io.github.coroutineusecases.base.UseCaseActivity
import io.github.coroutineusecases.base.UseCaseCategory
import io.github.coroutineusecases.base.UseCaseCategoryAdapter
import io.github.coroutineusecases.base.useCaseCategories
import io.github.coroutineusecases.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideUpButton()
        initRecyclerView()
    }

    private val onUseCaseCategoryClickListener: (UseCaseCategory) -> Unit =
        { clickedUseCaseCategory ->
            val intent = UseCaseActivity.newIntent(applicationContext, clickedUseCaseCategory)
            startActivity(intent)
        }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter =
                UseCaseCategoryAdapter(
                    useCaseCategories,
                    onUseCaseCategoryClickListener
                )
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(initItemDecoration())
        }
    }

    private fun initItemDecoration(): DividerItemDecoration {
        val itemDecorator =
            DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.recyclerview_divider
            )!!
        )
        return itemDecorator
    }

    override fun getToolbarTitle() = "Coroutine Usecases on Android"
}
