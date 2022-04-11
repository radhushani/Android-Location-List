package com.myproject.maplocationlist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myproject.maplocationlist.adapter.LocationAdapter
import com.myproject.maplocationlist.database.AppDatabase
import com.myproject.maplocationlist.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = AppDatabase.getDatabase(view.context)
        binding.recyclerview.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerview.adapter = LocationAdapter(db.LocationDao().getAll())

        val recyclerView: RecyclerView = binding.recyclerview
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        var location = db.LocationDao().getAll()[position]

                        saveData(location.name, location.latitude, location.longitude)
                        findNavController().navigate(R.id.action_FirstFragment_to_MapsFragment)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {

                    }
                })
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun saveData(name: String, latitude: Double, longitude: Double) {
        val insertedLocationName = name
        val insertedLatitude = latitude.toFloat()
        val insertedLongitude = longitude.toFloat()
        var sharedPref : SharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.apply {
            putString("nameKey", insertedLocationName)
            putFloat("latitudeKey", insertedLatitude)
            putFloat("longitudeKey", insertedLongitude)
        }.apply()
    }
}