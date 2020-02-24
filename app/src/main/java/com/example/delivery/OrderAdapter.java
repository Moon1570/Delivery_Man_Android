package com.example.delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter {

    ArrayList list = new ArrayList();
    Context context;

    public OrderAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public void remove() {
        list.clear();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row1;
        row1 = convertView;
        OrderHolder orderHolder;

        if (row1 == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row1 = layoutInflater.inflate(R.layout.order_row_layout, parent, false);

            orderHolder = new OrderHolder();

            orderHolder.id = row1.findViewById(R.id.order_id);
            orderHolder.name = row1.findViewById(R.id.order_customer_name);
            orderHolder.date = row1.findViewById(R.id.order_expected_date);


            row1.setTag(orderHolder);
        }
        else {
            orderHolder = ( OrderHolder) row1.getTag();
        }


        OrderModel sellerProduct = (OrderModel) this.getItem(position);
        orderHolder.id.setText(sellerProduct.getOrder_id());
        orderHolder.name.setText(sellerProduct.getOrdername());
        orderHolder.date.setText(sellerProduct.getExpectedDeliveryDate());


       // Picasso.get().load("http://10.10.22.242:9090/ecommerce/images/sellerproducts/"+ sellerProduct.getImage()).into(sellerProductHolder.imageView);

    /*    Picasso.get()
                .load("https://i.ytimg.com/vi/28uUsJ72a1A/hqdefault.jpg")
                .placeholder(null)
                .resize(80, 120)
                .into(productHolder.imageView); */

        return row1;

    }

    static class OrderHolder
    {
        TextView id,name, date;
    }
}
