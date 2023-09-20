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
import android.widget.TextView
import androidx.navigation.findNavController

class ProblemScreenFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_problemscreen, container, false)

        // Finding views of our buttons, radioGroups, TextViews
        val startButton = view.findViewById<Button>(R.id.startButton)
        val difficultyRadioGroup = view.findViewById<RadioGroup>(R.id.difficultyRGroup)
        val selectionRadioGroup = view.findViewById<RadioGroup>(R.id.selectionRGroup)
        val questionNumbers = view.findViewById<TextView>(R.id.questionNumber)
        val decrementButton = view.findViewById<Button>(R.id.decrement)
        val incrementButton = view.findViewById<Button>(R.id.increment)

        // Variable to hold User Selections
        var difficultySelection = ""
        var operandSelection = ""
        var numberOfQuestions = questionNumbers.text

        // Button will set User Selected Difficulty
        difficultyRadioGroup.setOnCheckedChangeListener{
                _, checkedId -> val radioButton = view.findViewById<RadioButton>(checkedId)
            difficultySelection = radioButton.text.toString()
        }

        // Button will set User Selected Operand
        selectionRadioGroup.setOnCheckedChangeListener{
                _, checkedId -> val radioButton = view.findViewById<RadioButton>(checkedId)
            operandSelection = radioButton.text.toString()
        }

        // Takes User Selections as a Single String argument to be passed to the next Fragment
        startButton.setOnClickListener{
            if(difficultySelection != "" && operandSelection != "" && numberOfQuestions.toString().toInt() != 0){
                val combinedSelection = "$difficultySelection|$operandSelection|$numberOfQuestions"
                val action = ProblemScreenFragmentDirections
                    .actionProblemScreenFragmentToProblemSolvingScreen(combinedSelection)
                view.findNavController().navigate(action)
            }
        }

        // Decrements Number of questions
        decrementButton.setOnClickListener {
            if(questionNumbers.text.toString().toInt() > 0){
                var number = questionNumbers.text.toString().toInt()
                number--
                questionNumbers.text = number.toString()
                numberOfQuestions = questionNumbers.text
            }
        }

        // Increments Number of questions
        incrementButton.setOnClickListener {
            var number = questionNumbers.text.toString().toInt()
            number++
            questionNumbers.text = number.toString()
            numberOfQuestions = questionNumbers.text
        }

        return view
    }
}