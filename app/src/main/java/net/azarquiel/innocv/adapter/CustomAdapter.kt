package net.azarquiel.innocv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row.view.*
import net.azarquiel.innocv.model.User

class CustomAdapter(val context: Context,
                    val layout: Int,
                    val listener: OnLongClickListenerUser
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setUsers(Users: List<User>) {
        this.dataList = Users
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: User, listener: OnLongClickListenerUser){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            itemView.tvuser.text = dataItem.name
            itemView.setOnLongClickListener {
                listener.OnLongClickBorrar(dataItem)
            }
            itemView.tag = dataItem
        }

    }

    interface OnLongClickListenerUser{
        fun OnLongClickBorrar(user : User):Boolean{
            return true
        }
    }
}