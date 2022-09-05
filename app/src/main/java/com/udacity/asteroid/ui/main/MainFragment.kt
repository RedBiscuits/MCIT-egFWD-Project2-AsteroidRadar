package com.udacity.asteroid.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroid.R
import com.udacity.asteroid.data.database.AsteroidDatabase
import com.udacity.asteroid.data.repositories.AsteroidRepository
import com.udacity.asteroid.databinding.FragmentMainBinding

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
        viewModel.picture.observe(requireActivity()){
            Log.d("today pic" , it.url)
        }
        binding.executePendingBindings()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
