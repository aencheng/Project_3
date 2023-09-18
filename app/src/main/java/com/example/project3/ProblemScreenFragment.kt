package com.example.project3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.navigation.findNavController

class ProblemScreenFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_problemscreen, container, false)

        val startButton = view.findViewById<Button>(R.id.startButton)
        val difficultyRadioGroup = view.findViewById<RadioGroup>(R.id.difficultyRGroup)
        val selectionRadioGroup = view.findViewById<RadioGroup>(R.id.selectionRGroup)
        val questionNumbers = view.findViewById<TextView>(R.id.questionNumber)

        var difficultySelection = ""
        var operandSelection = ""
        var numberOfQuestions = questionNumbers.text

        difficultyRadioGroup.setOnCheckedChangeListener{
                _, checkedId -> val radioButton = view.findViewById<RadioButton>(checkedId)
            difficultySelection = radioButton.text.toString()
        }

        selectionRadioGroup.setOnCheckedChangeListener{
                _, checkedId -> val radioButton = view.findViewById<RadioButton>(checkedId)
            operandSelection = radioButton.text.toString()
        }

        startButton.setOnClickListener{
            if(difficultySelection != "" && operandSelection != "" && numberOfQuestions.toString().toInt() != 0){
//                val combinedSelection = "$difficultySelection|$operandSelection"
//                val action = ProblemScreenFragmentDirections.actionProblemScreenFragmentToProblemSolvingScreen(combinedSelection)
//                Log.e("Check Variable", combinedSelection)
//                view.findNavController().navigate(action)

                val combinedSelection = "$difficultySelection|$operandSelection|$numberOfQuestions"
                Log.e("Check Variable", combinedSelection)
                val action = ProblemScreenFragmentDirections
                    .actionProblemScreenFragmentToProblemSolvingScreen(combinedSelection)
                view.findNavController().navigate(action)
            }
        }

        return view
    }
}