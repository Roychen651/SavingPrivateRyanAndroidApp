package com.example.savingprivateryanapp

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var adultTicketCount = 0
    private var childTicketCount = 0
    private var totalPrice = 0
    private var adultTicketPrice = 10
    private var childTicketPrice = 7
    private lateinit var adultTicketPriceTextView: TextView
    private lateinit var childTicketPriceTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var adultPlus: Button
    private lateinit var adultMinus: Button
    private lateinit var childPlus: Button
    private lateinit var childMinus: Button
    private lateinit var rishon: CheckBox
    private lateinit var glilot: CheckBox
    private lateinit var beerSheva: CheckBox
    private var theaterChoice: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val summaryButton = findViewById<Button>(R.id.summary_btn)
        summaryButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.summary_layout_dialog, null)

            builder.setView(dialogView)
            val dialog = builder.create()

            dialogView.findViewById<Button>(R.id.summary_close_btn).setOnClickListener {
                Toast.makeText(this, "Closing Summary", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            dialog.show()
        }

        val img = findViewById<ImageButton>(R.id.my_image)
        val animDrawable: AnimationDrawable = img.drawable as AnimationDrawable
        animDrawable.start()

        val titleBtn = findViewById<Button>(R.id.headline_btn)
        titleBtn.setOnClickListener {
            val red = (0..255).random()
            val green = (0..255).random()
            val blue = (0..255).random()

            val color: Int = Color.rgb(red, green, blue)
            it.setBackgroundColor(color)
        }




        val dateButton = findViewById<Button>(R.id.date_dialog_button)
        val alphaAnim = AnimationUtils.loadAnimation(applicationContext,R.anim.fade)
        dateButton.startAnimation(alphaAnim)
        val textDate = findViewById<TextView>(R.id.text_date)
        dateButton.setOnClickListener {
            val c = Calendar.getInstance()
            val listener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = "${year}-${month + 1}-${dayOfMonth}" // Format the date as desired
                textDate.text = selectedDate

            }
            val dpd = DatePickerDialog(this,listener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH))

            dpd.show()
        }


        adultTicketPriceTextView = findViewById(R.id.ticketCountTextView)
        childTicketPriceTextView = findViewById(R.id.ticketCountTextViewChild)
        totalPriceTextView = findViewById(R.id.totalPrice)
        adultPlus = findViewById(R.id.plusButton)
        adultMinus = findViewById(R.id.minusButton)
        childMinus = findViewById(R.id.minusButtonChild)
        childPlus = findViewById(R.id.plusButtonChild)
        rishon = findViewById(R.id.Rishon)
        glilot = findViewById(R.id.Glilot)
        beerSheva = findViewById(R.id.BeerSheva)
        val orderButton = findViewById<Button>(R.id.order)

        val rotateAnim = AnimationUtils.loadAnimation(applicationContext,R.anim.rotate)
        summaryButton.startAnimation(rotateAnim)
        orderButton.startAnimation(rotateAnim)


        rishon.setOnClickListener {
            if (rishon.isChecked) {
                theaterChoice = getString(R.string.rishon_le_zion)
                glilot.isChecked = false
                beerSheva.isChecked = false
            } else {
                theaterChoice = ""
            }
        }

        glilot.setOnClickListener {
            if (glilot.isChecked) {
                theaterChoice = getString(R.string.glilot)
                rishon.isChecked = false
                beerSheva.isChecked = false
            } else {
                theaterChoice = ""
            }
        }

        beerSheva.setOnClickListener {
            if (beerSheva.isChecked) {
                theaterChoice = getString(R.string.beer_sheva)
                rishon.isChecked = false
                glilot.isChecked = false
            } else {
                theaterChoice = ""
            }
        }

        adultPlus.setOnClickListener {
            adultTicketCount++
            adultTicketPriceTextView.text = adultTicketCount.toString()
            calculateTotalPrice()
        }

        adultMinus.setOnClickListener {
            if (adultTicketCount > 0) {
                adultTicketCount--
                adultTicketPriceTextView.text = adultTicketCount.toString()
                calculateTotalPrice()
            }
        }

        childPlus.setOnClickListener {
            childTicketCount++
            childTicketPriceTextView.text = childTicketCount.toString()
            calculateTotalPrice()
        }

        childMinus.setOnClickListener {
            if (childTicketCount > 0) {
                childTicketCount--
                childTicketPriceTextView.text = childTicketCount.toString()
                calculateTotalPrice()
            }
        }

        orderButton.setOnClickListener {
            showOrderSummary()
        }
    }

    private fun calculateTotalPrice() {
        val adultTotalPrice = adultTicketCount * adultTicketPrice
        val childTotalPrice = childTicketCount * childTicketPrice

        totalPrice = adultTotalPrice + childTotalPrice
        totalPriceTextView.text = "$totalPrice $"
    }

    private fun getSelectedHour(): String {
        return (findViewById<Spinner>(R.id.hour_dropdown).selectedItem as String)
    }

    private fun showOrderSummary() {
        val orderSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.order_layout, null)

        val nameOrderTextView = view.findViewById<TextView>(R.id.name_order_dialog)
        val dateOrderTextView = view.findViewById<TextView>(R.id.date_order_dialog)
        val adultOrderTextView = view.findViewById<TextView>(R.id.adult_order_dialog)
        val childOrderTextView = view.findViewById<TextView>(R.id.child_order_dialog)
        val theaterOrderTextView = view.findViewById<TextView>(R.id.theater_order_dialog)
        val totalPriceTextView = view.findViewById<TextView>(R.id.TotalPrice)
        val hourOrderTextView = view.findViewById<TextView>(R.id.hour_order_dialog) // TextView for hour

        nameOrderTextView.text = getString(R.string.headline)
        dateOrderTextView.text = findViewById<TextView>(R.id.text_date).text
        adultOrderTextView.text = adultTicketCount.toString()
        childOrderTextView.text = childTicketCount.toString()
        theaterOrderTextView.text = theaterChoice
        totalPriceTextView.text = "$totalPrice $"
        hourOrderTextView.text = getSelectedHour()

        view.findViewById<Button>(R.id.accept).setOnClickListener {
            orderSheetDialog.dismiss()
            Toast.makeText(this@MainActivity, "Order Complete", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.cancel).setOnClickListener {
            orderSheetDialog.dismiss()
            Toast.makeText(this@MainActivity, "Order Canceled", Toast.LENGTH_SHORT).show()
        }

        orderSheetDialog.setContentView(view)
        orderSheetDialog.show()
    }



}
