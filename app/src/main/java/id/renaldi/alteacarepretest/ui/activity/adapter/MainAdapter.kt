package id.renaldi.alteacarepretest.ui.activity.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import id.renaldi.alteacarepretest.R
import id.renaldi.alteacarepretest.data.repository.pojo.Doctor
import id.renaldi.alteacarepretest.databinding.ListItemDoctorBinding
import id.renaldi.alteacarepretest.utility.loadHTML
import id.renaldi.alteacarepretest.utility.loadImage

class MainAdapter(
    context: Context, resource: Int, var items: MutableList<Doctor>
): ArrayAdapter<Doctor>(context, resource, items) {

    fun setupList(items: MutableList<Doctor>) {
        this.items.apply {
            clear()
            addAll(items)
        }

        notifyDataSetChanged()
    }

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(
        position: Int, convertView: View?, parent: ViewGroup
    ): View = DataBindingUtil.inflate<ListItemDoctorBinding>(
        LayoutInflater.from(context),
        R.layout.list_item_doctor,
        null,
        false
    ).let{
        it.tvName.text = items[position].name
        it.ivDoctor.loadImage(url = items[position].photo.url)
        it.tvAbout.loadHTML(items[position].aboutPreview)
        it.tvHospitalSpecialis.text = "${items[position].hospital[0].name} - ${items[position].specialization.name}"
        it.tvPrice.text = items[position].price.formatted

        return@let it.root
    }
}