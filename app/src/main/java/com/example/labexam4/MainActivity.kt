package com.example.labexam4

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import RecyclerItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labexam4.Adapter.ToDoAdapter
import com.example.labexam4.Model.ToDoModel
import com.example.labexam4.Utils.DatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton



class MainActivity : AppCompatActivity(),DialogCloseListener{

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter
    private var taskList: MutableList<ToDoModel> = mutableListOf()
    private lateinit var db: DatabaseHandler
    private lateinit var fab:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        db = DatabaseHandler(this)
        db.openDatabase()

        taskRecyclerView = findViewById(R.id.taskRecycleView)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = ToDoAdapter(this, taskList,db)
        taskRecyclerView.adapter = tasksAdapter

        fab = findViewById(R.id.fab)
        var itemTouchHelper:ItemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(taskRecyclerView)

        taskList = db.getAllTasks()
        taskList.reverse()
        tasksAdapter.setTasks(taskList)

        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                AddNewTask.newInstance().show(supportFragmentManager,AddNewTask.TAG)
            }
        })
    }



    @SuppressLint("NotifyDataSetChanged")
    override fun handleDialogClose(dialog: DialogInterface) {
        taskList = db.getAllTasks()
        taskList.reverse()
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()


    }

}
