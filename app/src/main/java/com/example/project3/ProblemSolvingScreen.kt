package com.example.project3

import android.annotation.SuppressLint
import android.media.MediaPlayer
import java.math.BigDecimal
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import java.math.RoundingMode
import kotlin.random.Random

class ProblemSolvingScreen : Fragment() {
    private var param1: String? = null

    // Pulls argument passed from Previous Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("combinedSelection")?.let {
            param1 = it
            Log.e("CLASS", param1!!)
        }
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_problem_solving_screen, container, false)

        // Find Views of our TextView, Button, and EditText
        val text = view.findViewById<TextView>(R.id.problemText)
        val doneButton = view.findViewById<Button>(R.id.doneButton)
        val inputText = view.findViewById<EditText>(R.id.answerEdit)


        //Helper Function to help evaluate the values being generated based on the chosen Operand
        fun evalHelper(first: Int, operator: String, second: Int): Double {
            val result = when (operator) {
                "Addition" -> BigDecimal(first).add(BigDecimal(second)).toDouble()
                "Subtraction" -> BigDecimal(first).subtract(BigDecimal(second)).toDouble()
                "Multiplication" -> BigDecimal(first).multiply(BigDecimal(second)).toDouble()
                "Division" -> BigDecimal(first.toString()).divide(BigDecimal(second.toString()), 2, RoundingMode.HALF_UP).toDouble()

                else -> throw IllegalArgumentException("Invalid operator")
            }
            Log.e("RESULT", "$first|$second|$result")
            return result
        }

        // Takes Param1 and Splits it by | to get each argument needed to check what values to show to User
        val list = param1!!.split("|")
        val difficulty = list[0]
        val operand = list[1]

        // Saves originalNumber to be used in result Screen
        val originalNumber = list[2].toInt()

        // NumberOfQuestions starts slightly lower due to us generating the first question immediately
        var numberOfQuestions = list[2].toInt() - 1

        // Counting correct Answers
        var correctCount = 0

        // Decides first value based on Difficulty
        var n1 = when(difficulty){
            "Easy" -> Random.nextInt(0, 9)
            "Medium" -> Random.nextInt(10, 25)
            "Hard" -> Random.nextInt(26, 50)
            else -> throw IllegalArgumentException("Invalid Difficulty")
        }

        // Decides second value based on Difficulty and always starts at at least 1 in Easy mode
        var n2 = when(difficulty){
            "Easy" -> Random.nextInt(1, 9)
            "Medium" -> Random.nextInt(10, 25)
            "Hard" -> Random.nextInt(26, 50)
            else -> throw IllegalArgumentException("Invalid Difficulty")
        }

        // Set text based on given n1 and n2 and operand
        text.text = when(operand){
            "Addition" -> "$n1      +      $n2"
            "Subtraction" -> "$n1      -      $n2"
            "Multiplication" -> "$n1      X      $n2"
            "Division" -> "$n1      /      $n2"
            else -> throw IllegalArgumentException("Invalid Operator")
        }


        doneButton.setOnClickListener {
            if(inputText.text.isNotEmpty()){
                // when counter reaches 0, it'll check answer and then pass result to next fragment as argument
                when (numberOfQuestions) {
                    0 -> {
                        val evaluate = evalHelper(n1, operand, n2)
                        if(inputText.text.toString().toDouble() == evaluate){
                            correctCount++
                        }
                        val resultString = "$correctCount|$originalNumber|$operand"
                        //Log.e("Check Variable", combinedSelection)
                        val action = ProblemSolvingScreenDirections.actionProblemSolvingScreenToProblemScreenFragment(resultString)
                        view.findNavController().navigate(action)
                    }

                    // otherwise, it needs to check answer and create new question while decrementing the count.
                    else -> {
                        val evaluate = evalHelper(n1, operand, n2)
                        if(inputText.text.toString().toDouble() == evaluate){
                            Toast.makeText(requireContext(), "Correct. Good work!", Toast.LENGTH_SHORT).show()
                            val mediaPlayer = MediaPlayer.create(context, R.raw.correct)
                            mediaPlayer.start()
                            correctCount++
                        }
                        else{
                            Toast.makeText(requireContext(), "Wrong", Toast.LENGTH_SHORT).show()
                            val mediaPlayer = MediaPlayer.create(context, R.raw.wrong)
                            mediaPlayer.start()
                        }
                        n1 = when(difficulty){
                            "Easy" -> Random.nextInt(0, 9)
                            "Medium" -> Random.nextInt(10, 25)
                            "Hard" -> Random.nextInt(26, 50)
                            else -> throw IllegalArgumentException("Invalid Difficulty")
                        }

                        n2 = when(difficulty){
                            "Easy" -> Random.nextInt(1, 9)
                            "Medium" -> Random.nextInt(10, 25)
                            "Hard" -> Random.nextInt(26, 50)
                            else -> throw IllegalArgumentException("Invalid Difficulty")
                        }
                        text.text = when(operand) {
                            "Addition" -> "$n1      +      $n2"
                            "Subtraction" -> "$n1      -      $n2"
                            "Multiplication" -> "$n1      X      $n2"
                            "Division" -> "$n1      /      $n2"
                            else -> throw IllegalArgumentException("Invalid Operator")
                        }
                        val emptyEditable = SpannableStringBuilder()
                        inputText.text = emptyEditable // empties EditText
                        inputText.clearFocus() // removes Text Cursor
                        numberOfQuestions--
                    }
                }
            }
        }

        return view
    }
}