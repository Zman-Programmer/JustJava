package com.example.zman.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.order;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    double quantity =1;
    boolean isWhipChecked;
    boolean isChocolate;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public String createOrderSummary(double totalprice){
        String ordertext = "Name: " + name;
        ordertext += "\nAdd Whipped Cream: " + isWhipChecked;
        ordertext += "\nAdd Chocolate: " + isChocolate;
        ordertext += "\nQuantity: " + quantity;
        ordertext += "\nTotol: $" + totalprice;
        ordertext += "\nThank you!";
        return ordertext;
    }




    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view){
        EditText customerName = (EditText) findViewById(R.id.customername);
        name = customerName.getText().toString();


        CheckBox WhippedCreamCheck = (CheckBox) findViewById(R.id.whippedcream);
        isWhipChecked = WhippedCreamCheck.isChecked();
        //Log.v("MainActivity", "Has whipped cream: "+ isWhipChecked);

        CheckBox checkChocolateBox = (CheckBox) findViewById(R.id.chocolate);
        isChocolate = checkChocolateBox.isChecked();

        double price = calculatePrice(quantity);
        String ordersummary = createOrderSummary(price);


        Intent emailintent = new Intent(Intent.ACTION_SENDTO);
        emailintent.setType("text/plain");
        String subject = "JustJava order for " + name;
        emailintent.setData(Uri.parse("mailto:"));
        emailintent.putExtra(Intent.EXTRA_EMAIL, "zemann.schps@gmail.com");
        emailintent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailintent.putExtra(Intent.EXTRA_TEXT, ordersummary);

        if (emailintent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailintent);
        }
        //displayPrice(ordersummary);
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private double calculatePrice(double quantity) {
        double coffee =5;
        if(isWhipChecked == true){
            coffee = coffee + 1;
        }
        if (isChocolate == true){
            coffee = coffee + 2;
        }
        double price = quantity * coffee;
        return price;
    }

    //this is the + method
    public void increment(View view){
        if(quantity >= 100){
            Toast.makeText(MainActivity.this, "You can not order more than 100 coffee.", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            quantity++;
            displaymethod (quantity);
            //displayPrice(quantity * 5);
        }
    }

    //this is the - method
    public void decrement(View view){
        if(quantity<= 1){
            Toast.makeText(MainActivity.this, "You can not order less than 1 coffee.", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            quantity--;
            displaymethod (quantity);
            //displayPrice(quantity * 5);
        }
    }
    /**
     * This method displays the given ssquantity value on the screen.
     */
    private void displaymethod(double a) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + a);
    }

//    private void displayPrice(String number) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText("" + number);
//    }
//    /**
//     * This method displays the given price on the screen.
//     */
//    private void displayPrice(double number) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }
}