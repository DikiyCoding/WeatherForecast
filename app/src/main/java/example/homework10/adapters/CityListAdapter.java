package example.homework10.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.homework10.R;
import example.homework10.activities.DetailActivity;
import example.homework10.apis.open_weather_map.pojos.CitiesParcelable;
import example.homework10.handlers.UnixHandler;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private final Intent intent;
    private final Context context;
    private final LayoutInflater inflater;
    private final List<CitiesParcelable> cities;

    private final String TAG = "Logs";

    public CityListAdapter(Context context, List<CitiesParcelable> cities) {
        this.context = context;
        this.cities = cities;
        this.inflater = LayoutInflater.from(context);
        intent = new Intent(context, DetailActivity.class);
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CitiesParcelable city = cities.get(position);
        holder.itemCityName.setText(city.getName());
        holder.itemCountryName.setText(city.getCountry());
        holder.itemTemperature.setText(city.getTemperature() + " °С");
        holder.itemDayTime.setText(UnixHandler.fromUNIXtoReadable(city.getDatetime()));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public long getItemId(int position) {
        return cities.get(position).hashCode();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView itemCityName, itemCountryName, itemTemperature, itemDayTime;
        private int position;

        ViewHolder(View itemView) {
            super(itemView);
            itemCityName = itemView.findViewById(R.id.itemCityName);
            itemCountryName = itemView.findViewById(R.id.itemCountryName);
            itemTemperature = itemView.findViewById(R.id.itemTemperature);
            itemDayTime = itemView.findViewById(R.id.itemDayTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Item is clicked");
            position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                intent.putExtra("values", cities.get(position));
                context.startActivity(intent);
            }
        }
    }
}
