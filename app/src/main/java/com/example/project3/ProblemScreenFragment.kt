package com.example.project3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class ProblemScreenFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_problemscreen, container, false)

        val startButton = view.findViewById<Button>(R.id.startButton)
        val difficultyRadioGroup = view.findViewById<RadioGroup>(R.id.difficultyRGroup)
        var difficultySelectText = ""

        difficultyRadioGroup.setOnCheckedChangeListener{
            group, checkedId -> val radioButton = view.findViewById<RadioButton>(checkedId)
            difficultySelectText = radioButton.text.toString()
        }


        startButton.setOnClickListener{
            //findNavController().navigate(R.id.action_problemScreenFragment_to_problemSolvingScreen)
            val action = ProblemScreenFragmentDirections.actionProblemScreenFragmentToProblemSolvingScreen(difficultySelectText)

            view.findNavController().navigate(action)
        }

        return view
    }
}