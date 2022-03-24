package com.example.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.map.ServicesData.Categories;
import com.example.map.ServicesData.Images;
import com.example.map.ServicesData.Prices;
import com.example.map.ServicesData.ServicesDataClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowItemsOnClickOnList extends AppCompatActivity {
    TextView title, createdDate, updatedDate, description, contact, email, amount, prices_title;
    ImageView imageView;
    static int position;
    ServicesDataClass servicesDataClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_on_click_on_list);

        intialize();
        position = getIntent().getIntExtra("position", 0);
        servicesDataClass = MainActivity.list_service.get(position);
        showDataOnLayout();
    }

    private void showDataOnLayout() {
        List<Categories> categories = servicesDataClass.getCategories();
        Images[] images = servicesDataClass.getImages();
        if (!servicesDataClass.getTitle().isEmpty()) {
            title.setText(servicesDataClass.getTitle());
        }

        if (!servicesDataClass.getCreatedAt().isEmpty()) {
            createdDate.append(servicesDataClass.getCreatedAt());
        }
        if (!servicesDataClass.getUpdatedAt().isEmpty()) {
            updatedDate.append(servicesDataClass.getUpdatedAt());
        }

        if (images.length > 0 && images[0].getUrl() != null) {
            Picasso.get().load(servicesDataClass.getImages()[0].getUrl()).placeholder(R.drawable.default_image).into(imageView);
        }

        if (categories != null && categories.get(0).getPrices() != null && categories.get(0).getPrices().get(0).getAmount() != 0) {
            amount.append(categories.get(0).getPrices().get(0).getAmount() + "");
        }

        if (categories != null && categories.get(0).getPrices() != null && categories.get(0).getPrices().get(0).getTitle() != null) {
            prices_title.setText(categories.get(0).getPrices().get(0).getTitle());
        }

        if (!servicesDataClass.getDescription().isEmpty()) {
            description.setText(servicesDataClass.getDescription());
        }

        if (!servicesDataClass.getEmailId().isEmpty()) {
            contact.append(servicesDataClass.getContactInfo());
        }
        if (!servicesDataClass.getContactInfo().isEmpty()) {
            email.append(servicesDataClass.getEmailId());
        }

    }

    private void intialize() {
        title = findViewById(R.id.click_title);
        createdDate = findViewById(R.id.click_created_date);
        updatedDate = findViewById(R.id.click_updated_date);
        description = findViewById(R.id.click_description);
        contact = findViewById(R.id.click_contact_info);
        email = findViewById(R.id.click_emailId);
        imageView = findViewById(R.id.click_imageview);
        amount = findViewById(R.id.click_prices_amount);
        prices_title = findViewById(R.id.click_prices_title);
    }
}