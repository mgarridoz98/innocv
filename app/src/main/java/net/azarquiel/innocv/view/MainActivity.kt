package net.azarquiel.innocv.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import net.azarquiel.innocv.R
import net.azarquiel.innocv.adapter.CustomAdapter
import net.azarquiel.innocv.model.User
import net.azarquiel.innocv.util.formatDate
import net.azarquiel.innocv.viewmodel.MainViewModel
import org.jetbrains.anko.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, CustomAdapter.OnLongClickListenerUser {

    companion object {
        const val REQUESTADD = 1
    }


    private lateinit var users: List<User>
    private lateinit var searchView: SearchView
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initRV()
        getUsers()
        fab.setOnClickListener {
            addUser()
        }
    }

    private fun dialogoUser(user: User) {
        alert {
            title ="Modify User"
            customView {
                verticalLayout {
                    lparams(width = wrapContent, height = wrapContent)
                    val etNombre = editText {
                        hint = "Name"
                        padding = dip(16)
                        this.setText(user.name)
                    }
                    val etBd = editText {
                        hint = "Birthdate"
                        padding = dip(16)
                        this.setText(user.birthdate.formatDate())
                    }
                    positiveButton("Aceptar") {
                        if (etNombre.text.toString().isEmpty() || etBd.text.toString().isEmpty() )
                            toast("Campos Obligatorios")
                        else{ //modify user
                            val userModify = User(user.id,etNombre.text.toString(),etBd.text.toString())
                            modifyUser(userModify)
                        }
               }
                    negativeButton("Cancelar"){}
                }
            }
        }.show()
    }

    private fun addUser() {
        val intent = Intent(this, AddUserActivity::class.java)
        startActivityForResult(intent, REQUESTADD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUESTADD) {
                val user = data!!.getSerializableExtra("user") as User
                viewModel.saveUser(user).observe(this, Observer {})
                this.getUsers()
            }

        }

    }

    private fun modifyUser(userM: User) {
        viewModel.modifyUser(userM).observe(this, Observer {
            this.getUsers()
        })
    }

    private fun getUsers() {
        viewModel.getUsers().observe(this, Observer { it ->
            it?.let{
                users=it
                adapter.setUsers(users)
            }
        })

    }

    private fun initRV() {
        adapter = CustomAdapter(this, R.layout.row,this)
        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // ************* <Filtro> ************
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("Search...")
        searchView.setOnQueryTextListener(this)
        // ************* </Filtro> ************

        return true
    }

    // ************* <Filtro> ************
    override fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<User>(users)
        adapter.setUsers(original.filter{ user -> user.name!=null && user.name.toUpperCase().contains(query.toUpperCase()) })
        return false
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        return false
    }
    // ************* </Filtro> ************
    fun onClickUser(v:View){
        val userM = v.tag as User
        dialogoUser(userM)
    }

    override fun OnLongClickBorrar(user: User):Boolean{
        alert("¿Estás seguro eliminar a ${user.name}?", "Confirm") {
            yesButton {
                viewModel.deleteUser(user.id).observe(this@MainActivity, Observer {})
                this@MainActivity.getUsers()
            }
            noButton {}
        }.show()
        return true
    }
}
