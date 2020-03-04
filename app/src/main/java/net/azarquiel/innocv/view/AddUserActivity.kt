package net.azarquiel.innocv.view

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.row.*
import net.azarquiel.innocv.R
import net.azarquiel.innocv.model.User
import java.text.SimpleDateFormat
import java.util.*

class AddUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val fecha = sdf.format(Date())
        tvfecha.text = fecha
        btnpf.setOnClickListener { clickDatePicker() }
        btnaceptar.setOnClickListener { onClickbtnaceptar() }
    }

    private fun onClickbtnaceptar() {
        val user = User(0,edUser.text.toString(),tvfecha.text.toString())
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", user)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    private fun clickDatePicker() {
        val c = Date()
        var sdf = SimpleDateFormat("dd")
        val dia = sdf.format(c).toInt()
        sdf = SimpleDateFormat("MM")
        val mes = sdf.format(c).toInt()
        sdf = SimpleDateFormat("yyyy")
        val anio = sdf.format(c).toInt()
        val tpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, y, m, d ->
            tvfecha.text = "$d-${m+1}-$y"
        },anio,mes-1,dia)

        tpd.show()
    }
}
