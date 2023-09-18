package com.example.project3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import kotlin.random.Random

class ProblemSolvingScreen : Fragment() {
    private var param1: String? = null

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
        val text = view.findViewById<TextView>(R.id.problemText)
        val doneButton = view.findViewById<Button>(R.id.doneButton)

        val list = param1!!.split("|")
        val difficulty = list[0]
        val operand = list[1]
        val originalNumber = list[2].toInt()
        var numberOfQuestions = list[2].toInt() - 1
        var correctCount = 0

        var n1 = when(difficulty){
            "Easy" -> Random.nextInt(0, 9)
            "Medium" -> Random.nextInt(10, 25)
            "Hard" -> Random.nextInt(26, 50)
            else -> throw IllegalArgumentException("Invalid Difficulty")
        }

        var n2 = when(difficulty){
            "Easy" -> Random.nextInt(0, 9)
            "Medium" -> Random.nextInt(10, 25)
            "Hard" -> Random.nextInt(26, 50)
            else -> throw IllegalArgumentException("Invalid Difficulty")
        }

        text.text = when(operand){
            "Addition" -> "$n1      +      $n2"
            "Subtraction" -> "$n1      -      $n2"
            "Multiplication" -> "$n1      X      $n2"
            "Division" -> "$n1      /      $n2"
            else -> throw IllegalArgumentException("Invalid Operator")
        }

        doneButton.setOnClickListener {
            if(numberOfQuestions == 0){
                val combinedResult = "Your score: $correctCount out of $originalNumber"
                //Log.e("Check Variable", combinedSelection)
                val action = ProblemSolvingScreenDirections.actionProblemSolvingScreenToResultFragment(combinedResult)
                view.findNavController().navigate(action)
            }
            else{
                n1 = when(difficulty){
                    "Easy" -> Random.nextInt(0, 9)
                    "Medium" -> Random.nextInt(10, 25)
                    "Hard" -> Random.nextInt(26, 50)
                    else -> throw IllegalArgumentException("Invalid Difficulty")
                }

                n2 = when(difficulty){
                    "Easy" -> Random.nextInt(0, 9)
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
                numberOfQuestions--
            }
        }

        return view
    }
}