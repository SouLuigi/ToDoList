package com.ex.todolist

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale

class AddNewTask : DialogFragment() {

    var listener: AddTaskListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_add_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonSaveNewTask: MaterialButton = view.findViewById(R.id.buttonSaveNewTask)
        val editTextData: EditText = view.findViewById(R.id.editTextData)
        val spinner: Spinner = view.findViewById(R.id.spinnerCategory)
        val options = listOf("Trabalho", "Estudo", "Pessoal")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = options[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        editTextData.setOnClickListener {
            val calendario = Calendar.getInstance()
            val ano = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, anoSelecionado, mesSelecionado, diaSelecionado ->
                    val dataFormatada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(Calendar.getInstance().apply {
                            set(anoSelecionado, mesSelecionado, diaSelecionado)
                        }.time)
                    editTextData.setText(dataFormatada)
                }, ano, mes, dia
            )
            datePicker.show()
        }

        buttonSaveNewTask.setOnClickListener {
            val title = view.findViewById<EditText>(R.id.editTextTitleTask).text.toString()
            val category = view.findViewById<Spinner>(R.id.spinnerCategory).selectedItem.toString()
            val date = view.findViewById<EditText>(R.id.editTextData).text.toString()

            if (title.isNotBlank() && date.isNotBlank()) {
                val newTask = Task(
                    id = System.currentTimeMillis().toInt(),
                    title = title,
                    date = date,
                    category = category
                )
                listener?.onTaskAdded(newTask)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun setAddTaskListener(listener: MainActivity) {
        this.listener = listener
    }

    interface AddTaskListener {
        fun onTaskAdded(task: Task)
    }
}