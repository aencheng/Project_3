package com.example.project3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
class ResultFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("combinedResult")?.let {
            param1 = it
            Log.e("CLASS", param1!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        val text = view.findViewById<TextView>(R.id.resultText)
        val returnHome = view.findViewById<Button>(R.id.returnHomeButton)

        val result = param1

        text.text = result

        returnHome.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_problemScreenFragment)
        }

        return view
    }
}