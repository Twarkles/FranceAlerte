package com.example.francealerte.ui.dashboard

import Alerte
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.francealerte.MainActivity
import com.example.francealerte.R
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        var text_dashboard: TextView = root.findViewById(R.id.text_dashboard)
        text_dashboard.text = resources.getString(R.string.declaration)
        var btn_pos: Button = root.findViewById(R.id.btn_position)
        var btn_dec: Button = root.findViewById(R.id.btn_declarer)
        var titre: TextView = root.findViewById(R.id.titre)
        var latitude: TextView = root.findViewById(R.id.latitude)
        var longitude: TextView = root.findViewById(R.id.longitude)

        btn_pos.setOnClickListener{
            (activity as MainActivity).getLastLocation()

        }

        btn_dec.setOnClickListener{
            val t1 : String = titre.text.toString()
            val t2 : String = latitude.text.toString()
            val t3 : String = longitude.text.toString()
            var date = LocalDateTime.now().toString()

            if (t1.trim().isEmpty() || t2.trim().isEmpty() || t3.trim().isEmpty()) {
                Toast.makeText(activity, "Veuillez remplir tous les champs avant de déclarer une alerte", Toast.LENGTH_SHORT).show()
            } else {
                val al = Alerte(t1, t2.toDouble(), t3.toDouble(), date)
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("Alertes")
                myRef.push().setValue(al)
                Toast.makeText(activity, "Alerte publiée", Toast.LENGTH_SHORT).show()

            }



        }
        return root

    }




}