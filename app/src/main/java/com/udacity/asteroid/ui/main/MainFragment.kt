package com.udacity.asteroid.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroid.R
import com.udacity.asteroid.data.database.AsteroidDatabase
import com.udacity.asteroid.data.repositories.AsteroidRepository
import com.udacity.asteroid.databinding.FragmentMainBinding
import com.udacity.asteroid.ui.adapters.AsteroidAdapter

class MainFragment : Fragment() {

    private val adapter = AsteroidAdapter(AsteroidAdapter.AsteroidListener { asteroid ->
        viewModel.onAsteroidClicked(asteroid)
    })
    private val repository by lazy {
        AsteroidRepository(AsteroidDatabase.getDatabase(requireContext()).asteroidDao)
    }
    private val viewModel: AsteroidViewModel by lazy {
        ViewModelProvider(requireActivity() , AsteroidViewModelFactory(repository))[AsteroidViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupAsteroidRecycler(binding)


        viewModel.loading.observe(viewLifecycleOwner){ isLoading ->
            Log.d("meow" , isLoading.toString())
            if (isLoading){
                binding.loadingDataSpinner.visibility = View.VISIBLE
            }else{
                binding.loadingDataSpinner.visibility = View.GONE
            }
        }
        viewModel.navigateToAsteroidDetail!!.observe(viewLifecycleOwner){ asteroid ->
            asteroid?.let {
                val action = MainFragmentDirections.actionShowDetail(asteroid)
                findNavController().navigate(action)
                viewModel.onAsteroidDetailNavigated()
            }
        }

        binding.executePendingBindings()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupAsteroidRecycler(binding: FragmentMainBinding) {

        val manager = LinearLayoutManager(requireContext())

        binding.asteroidRecycler.adapter = adapter
        binding.asteroidRecycler.layoutManager = manager

        viewModel.asteroids.observe(viewLifecycleOwner) {
            it?.let {
            adapter.submitList(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_today -> {
                adapter.submitList(adapter.currentList.filter {
                    it.closeApproachDate == viewModel.getToday()
                })
            }
            R.id.show_next_week -> {
                adapter.submitList(viewModel.asteroids.value)
            }
            R.id.show_all->{
                adapter.submitList(viewModel.allAsteroids.value)
            }
            else -> return true
        }
        return true
    }

}
