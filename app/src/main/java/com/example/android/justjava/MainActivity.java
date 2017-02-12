package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.icu.text.NumberFormat;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private final int defaultPrice = 5;
    private int count = 0;
    private int price = defaultPrice; //in dollars
    private TextView orderTextView;
    private String orderText = "";
    private String[] emailAddresses = new String[]{"jbaker@student.sjcny.edu", "scottmcq1@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // orderTextView = (TextView) findViewById(R.id.order_summary_text_view);
        // orderTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        count++;
        update();
    }

    public void decrement(View view) {
        if (count > 0) {
            count--;
            update();
        }
    }

    public void submitOrder(View view) {
        priceModifier();
        //  displayOrderSummary(count * price);
        setBody(count, price);
        composeEmail(emailAddresses, "Just Java Order", orderText);
        price = defaultPrice;
    }

    private void update() {
        displayQuantity(count);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayOrderSummary(int number) {
        orderTextView.setText(
                "Name: " + getName() + "\n"
                        + "Add whipped cream? " + hasWhippedCream() + "\n"
                        + "Add chocolate? " + hasChocolate() + "\n"
                        + "Quantity: " + count + "\n"
                        + "Total: " + NumberFormat.getCurrencyInstance().format(number) + "\n"
                        + "Thank you!"
        );
    }

    private boolean hasWhippedCream() {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return whippedCream.isChecked();
    }

    private boolean hasChocolate() {
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return chocolate.isChecked();
    }

    private String getName() {
        EditText name = (EditText) findViewById(R.id.name_text);
        return name.getText().toString();
    }

    private void priceModifier() {
        if (hasWhippedCream())
            price++;
        if (hasChocolate())
            price += 2;
    }

    public void composeEmail(String[] addresses, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    private void setBody(int quantity, int price) {
        orderText = "Name: " + getName() + "\n"
                + "Add whipped cream? " + hasWhippedCream() + "\n"
                + "Add chocolate? " + hasChocolate() + "\n"
                + "Quantity: " + quantity + "\n"
                + "Total: " + NumberFormat.getCurrencyInstance().format(price * quantity) + "\n"
                + "Thank you!";
    }
}