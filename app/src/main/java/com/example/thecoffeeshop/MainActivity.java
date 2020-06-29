package com.example.thecoffeeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    String[] provinceList = {"Ontario","Alberta","Nova Scotia","Nunavut","Quebec","Saskatchewan","Newfoundland and Labrador","Northwest Territories","British Columbia",
            "Manitoba","New Brunswick","Prince Edward Island","Yukon"};
    String[] sizeList = {"Small","Medium","Large"};

    String beverageType, extraItemName = "" , beverageName;
    double extraItemCharge = 0.00, finalRoundedAmount = 0.00, sizeCharge = 0.00, flavourCharge = 0.00, tax, finalAmount,beverageAmount;


    EditText etName,etDate;
    Spinner spSize;
    RadioButton coffee,tea,none,op1,op2,radioButton;
    RadioGroup flavouringGroup;
    CheckBox milk,sugar;
    AutoCompleteTextView selectProvince;
    //Button btnGenerateBill;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        spSize = findViewById(R.id.spSize);
        coffee = (RadioButton) findViewById(R.id.coffee);
        tea = (RadioButton) findViewById(R.id.tea);
        milk = (CheckBox) findViewById(R.id.milk);
        sugar = (CheckBox) findViewById(R.id.sugar);

        none = (RadioButton) findViewById(R.id.none);
        op1 = (RadioButton) findViewById(R.id.op1);
        op2 = (RadioButton) findViewById(R.id.op2);

        flavouringGroup = (RadioGroup) findViewById(R.id.flavouringGroup);
        int selectedId = flavouringGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
       // btnGenerateBill = (Button) findViewById(R.id.btnGenerateBill);


        //AutoComplteTextView

        AutoCompleteTextView selectProvince = (AutoCompleteTextView) findViewById(R.id.selectProvince);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, provinceList);
        selectProvince.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        final long today = MaterialDatePicker.todayInUtcMilliseconds();

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");
        builder.setSelection(today);
        final MaterialDatePicker materialDatePicker = builder.build();

        //For the DatePicker
        etDate.setFocusable(false);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                etDate.setText(materialDatePicker.getHeaderText());
            }
        });

        //Select Size
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sizeList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSize.setAdapter(adapter1);



        }

        public void OnBeverageSelected(View view)
        {
            //change flavour accoding to selection of tea or coffee


            if (coffee.isChecked()) {
                beverageName = coffee.getText().toString();
                flavouringGroup.setVisibility(View.VISIBLE);
                none.setText("None");
                op1.setText("Chocolate");
                op2.setText("Vanilla");
            } else if (tea.isChecked()) {
                beverageName = tea.getText().toString();
                flavouringGroup.setVisibility(View.VISIBLE);
                none.setText("None");
                op1.setText("Lemon");
                op2.setText("Mint");
            }
        }




    public void GenerateBill(View view){

        //get the name of customer
        String name = etName.getText().toString();

        //get the provience name
        String provienceName = selectProvince.getText().toString();

        if(coffee.isChecked())
        {
            if (milk.isChecked() && sugar.isChecked())
            {
                extraItemName = milk.getText().toString() + "and" + sugar.getText().toString();
                extraItemCharge = 2.25;
            }
            else if(milk.isChecked())
            {
                extraItemCharge = 1.25;
                extraItemName = milk.getText().toString();
            }
            else if(sugar.isChecked())
            {
                extraItemCharge = 1.00;
                extraItemName = sugar.getText().toString();
            }
            else {
                extraItemName = "";
                extraItemCharge = 0.00;
            }

            //Getting value from spinner
            String sizeList = spSize.getSelectedItem().toString();


            //Setting sizeCharge as per size selection
            if(spSize.equals(("Small")))
            {
                sizeCharge = 1.50;
            }
            else if(spSize.equals(("Medium")))
            {
                sizeCharge = 2.50;
            }
            else {
                sizeCharge = 3.25;
            }

            int selectedId = flavouringGroup.getCheckedRadioButtonId();

            //Get flavour name
            String flavourName = (String) radioButton.getText();

            //setting charge according to flavour name
            if(flavourName.equals("None"))
            {
                flavourCharge = 0.00;
            }
            else if(flavourName.equals("Chocolate"))
            {
                flavourCharge = 0.75;
            }
            else if(flavourName.equals("Vanilla"))
            {
                flavourCharge = 0.25;
            }

            //Get selected beverage value
            beverageType = coffee.getText().toString();

            //Calculation of final amount
            //Tax calculation
            //Rounded amount

            beverageAmount = sizeCharge + extraItemCharge + flavourCharge;
            tax = beverageAmount * 1.13;
            finalAmount = tax + beverageAmount;
            finalRoundedAmount = Math.round(finalAmount * 100.0) / 100.0;

            //Toast for final bill
            Toast.makeText(getApplicationContext(),"For" + name + "from" + provienceName + "a" + spSize + " " + beverageType + "with" +
                    extraItemName + " flavouring" + flavourName + "cost: $" + finalRoundedAmount, Toast.LENGTH_LONG).show();

        }

        else if(tea.isChecked())
        {
            if (milk.isChecked() && sugar.isChecked())
            {
                extraItemName = milk.getText().toString() + "and" + sugar.getText().toString();
                extraItemCharge = 2.25;
            }
            else if(milk.isChecked())
            {
                extraItemCharge = 1.25;
                extraItemName = milk.getText().toString();
            }
            else if(sugar.isChecked())
            {
                extraItemCharge = 1.00;
                extraItemName = sugar.getText().toString();
            }
            else {
                extraItemName = "";
                extraItemCharge = 0.00;
            }

            //Getting value from spinner
            String sizeList = spSize.getSelectedItem().toString();


            //Setting sizeCharge as per size selection
            if(spSize.equals(("Small")))
            {
                sizeCharge = 1.50;
            }
            else if(spSize.equals(("Medium")))
            {
                sizeCharge = 2.50;
            }
            else {
                sizeCharge = 3.25;
            }

            int selectedId = flavouringGroup.getCheckedRadioButtonId();

            //Get flavour name
            String flavourName = (String) radioButton.getText();

            //setting charge according to flavour name
            if(flavourName.equals("None"))
            {
                flavourCharge = 0.00;
            }
            else if(flavourName.equals("Lemon"))
            {
                flavourCharge = 0.25;
            }
            else if(flavourName.equals("Mint"))
            {
                flavourCharge = 0.50;
            }

            //Get selected beverage value
            beverageType = coffee.getText().toString();

            //Calculation of final amount
            //Tax calculation
            //Rounded amount

            beverageAmount = sizeCharge + extraItemCharge + flavourCharge;
            tax = beverageAmount * 1.13;
            finalAmount = tax + beverageAmount;
            finalRoundedAmount = Math.round(finalAmount * 100.0) / 100.0;

            //Toast for final bill
            Toast.makeText(getApplicationContext(),"For" + name + "from" + provienceName + "a" + spSize + " " + beverageType + "with" +
                    extraItemName + " flavouring" + flavourName + "cost: $" + finalRoundedAmount, Toast.LENGTH_LONG).show();

        }



        }



}

