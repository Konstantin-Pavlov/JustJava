package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    //int numberOfCoffees = 3;
    // 0 < quantity < 100
    int quantity = 1, price = 5;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {

        if (quantity+1 < 100)
            quantity += 1;
        else {

            if (toast != null) {
                toast.cancel();
            }
            // Show an error message as a toast
            Context context = getApplicationContext();
            CharSequence text = "you can't order more than one hundred cups of coffee";
            int duration = Toast.LENGTH_SHORT;
            toast = Toast.makeText(context, text, duration);
            toast.show();
            //toast.cancel();  // solves UX problem is when the user hits "-" or "+" too many times. It makes the toast show up for longer than it "should"


            // Show an error message as a toast
            //toast.makeText(this, "you can't order more than one hundred cups of coffee", Toast.LENGTH_SHORT).show();

            // Exit this method early because there's nothing left to do
            //return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity-1 > 0)
            quantity -= 1;
        else {
            if (toast != null) {
                toast.cancel();
            }

            // Show an error message as a toast
            Context context = getApplicationContext();
            CharSequence text = "you can't order less than one cup of coffee";
            int duration = Toast.LENGTH_SHORT;
            toast = Toast.makeText(context, text, duration);
            toast.show();
            //toast.cancel();  // solves UX problem is when the user hits "-" or "+" too many times. It makes the toast show up for longer than it "should"


            // Show an error message as a toast
            //Toast.makeText(this, "you can't order less than one cup of coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            //return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //display(numberOfCoffees);
        // displayPrice(quantity*5);
        //String priceMessage = createOrderSummary(price); //"Total: $" + Math.abs(price) ;
        //priceMessage += "\nThank you!";

        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString(); //return editable object, then method toString() makes it String type (chaining method calls)

        CheckBox WhippedCreamCheckBox  = (CheckBox) findViewById(R.id.Whipped_cream_checkBox);
        CheckBox Chocolate = (CheckBox) findViewById(R.id.Chocolate);

        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked(); // Figure out if the user wants whipped cream topping
        boolean hasChocolate = Chocolate.isChecked(); // Figure out if the user wants chocolate topping
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        Log.v("MainActivity", "Name: " + name); //logs
        Log.v("MainActivity", "has whipped cream " + hasWhippedCream);
        Log.v("MainActivity", "has Chocolate " + hasChocolate);
        Log.v("MainActivity", "The price is " + price);

        String OrderInfo = createOrderSummary(name, price, hasWhippedCream, hasChocolate );

        //displayMessage(createOrderSummary(name, price, hasWhippedCream, hasChocolate )); // Display the order summary on the screen
        displayMessage(OrderInfo); // Display the order summary on the screen

        //open mail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses); //addresses - a String[] holding e-mail addresses that should be delivered to.
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name); //email SUBJECT
        intent.putExtra(Intent.EXTRA_TEXT, OrderInfo); //email body
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        /*
        //open maps
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        mapIntent.setData(Uri.parse("geo:47.6,-122.3"));
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
        */
    }

    /**
     * Javadoc
     * create summary of the order
     * @param name of the customer
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        //String name = "Kaptain Kunal";
        String message = "Name: " + name +  "\nAdd Whipped Cream? " + addWhippedCream
                + "\nAdd chocolate? " + addChocolate
                + "\nQuantity: " + Math.abs(quantity) + "\nTotal: " + price + "\nThank you!";
        return message;
    }


    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int total = Math.abs(quantity * price) +
                quantity * ( (hasWhippedCream ? 1 : 0) + (hasChocolate ? 2 : 0) ); //ternary operator
        return total;
        /*
        int total = price;
        if (hasWhippedCream)
            total += 1;
        if (hasChocolate)
            total += 2;
        return total * Math.abs(quantity);
        */
    }


    /**
     * This method displays the given quantity value on the screen.
     */

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



/*
    */
/**
 * This method displays the given price on the screen.
 *//*

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance(Locale.US).format(number));
    }
*/

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}


