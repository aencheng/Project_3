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
        // Pulls the result from the key argument passed from previous Fragment
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

        // Finds TextView and Button
        val text = view.findViewById<TextView>(R.id.resultText)
        val returnHome = view.findViewById<Button>(R.id.returnHomeButton)

        // Sets result to be param1 = Our Result String
        val result = param1

        text.text = result

        // Takes us back to Start Screen Fragment
        returnHome.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_problemScreenFragment)
        }

        return view
    }
}