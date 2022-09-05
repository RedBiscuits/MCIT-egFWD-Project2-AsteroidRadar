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
import com.udacity.asteroid.ui.details.DetailFragmentArgs

class MainFragment : Fragment() {

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

        binding.executePendingBindings()
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupAsteroidRecycler(binding: FragmentMainBinding) {
        val adapter = AsteroidAdapter(AsteroidAdapter.AsteroidListener {
            val action = MainFragmentDirections.actionShowDetail(it)
            findNavController().navigate(action)
            Log.d("is it true ?? " , "Hell fucking yes!!!!")
        })
        val manager = LinearLayoutManager(requireContext())

        binding.asteroidRecycler.adapter = adapter
        binding.asteroidRecycler.layoutManager = manager

        viewModel.asteroids.observe(requireActivity()) {
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
        return true
    }
}
