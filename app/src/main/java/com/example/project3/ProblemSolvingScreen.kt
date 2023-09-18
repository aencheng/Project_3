package com.example.project3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

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
        val text = view.findViewById<TextView>(R.id.testText)
        val list = param1!!.split("|")
        val difficulty = list[0]
        val operand = list[1]
        val numberOfQuestions = list[2]
        text.text = "$difficulty - $operand - $numberOfQuestions"
        return view
    }
}