package com.myproject.maplocationlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.myproject.maplocationlist.database.AppDatabase
import com.myproject.maplocationlist.databinding.FragmentSecondBinding
import com.myproject.maplocationlist.model.Location

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.editTextLocationName.setOnClickListener {
            binding.editTextLocationName.text.clear()
        }
        binding.editTextLatitude.setOnClickListener {
            binding.editTextLatitude.text.clear()
        }
        binding.editTextLongitude.setOnClickListener {
            binding.editTextLongitude.text.clear()
        }

        binding.buttonSave.setOnClickListener {
            val locationName = binding.editTextLocationName.text.toString()
            val latitude = binding.editTextLatitude.text.toString().toDouble()
            val longitude = binding.editTextLongitude.text.toString().toDouble()
            val location = Location(locationName, latitude, longitude)

            val db = AppDatabase.getDatabase(view.context)
            db.LocationDao().insert(location)

            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}