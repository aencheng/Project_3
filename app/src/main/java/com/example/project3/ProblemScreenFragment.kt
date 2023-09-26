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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import java.math.BigDecimal
import java.math.RoundingMode

class ProblemScreenFragment : Fragment() {
    private var param1: String? = null

    // Pulls argument passed from Second Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("resultString")?.let {
            param1 = it
            Log.e("CLASS", param1!!)
        }
    }

    @SuppressLint("MissingInflatedId", "ResourceAsColor", "SetTextI18n")
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
        val resultText = view.findViewById<TextView>(R.id.resultText)

        if(param1 != null){
            // Takes result argument passed from the Second Fragment and make them usable.
            val list = param1!!.split("|")
            val correctCount = list[0]
            val totalQuestions = list[1]
            val operation = list[2]

            // Based on result, the home screen will display a specific message, as well as adjust the size of
            // other items within the view in order to properly make space for all items.
            if(BigDecimal(correctCount).divide(BigDecimal(totalQuestions), 2, RoundingMode.HALF_UP).toDouble() < 0.8){
                val buttonParams = startButton.layoutParams as ConstraintLayout.LayoutParams
                buttonParams.matchConstraintPercentHeight = 0.17f
                startButton.layoutParams = buttonParams

                resultText.text = "You got $correctCount out of $totalQuestions " +
                        "correct in $operation. You need to practice more!"
                resultText.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
            else{
                val buttonParams = startButton.layoutParams as ConstraintLayout.LayoutParams
                buttonParams.matchConstraintPercentHeight = 0.21f
                startButton.layoutParams = buttonParams

                resultText.text = "You got $correctCount out of $totalQuestions " +
                        "correct in $operation. Good Work!"
                resultText.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            }
        }

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