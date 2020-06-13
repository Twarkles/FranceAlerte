package com.example.francealerte

import Alerte
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter (val mCtx: Context, val layoutResId: Int, val al: List<Alerte>) : ArrayAdapter<Alerte>(mCtx, layoutResId, al) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(layoutResId, null);
        val textViewName = view.findViewById<TextView>(R.id.textViewName)


        val alert = al[position]

        textViewName.text = alert.titre

        return view;



    }
}